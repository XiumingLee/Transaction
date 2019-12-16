package cn.xiuminglee.service.impl;

import cn.xiuminglee.dao.AccountInfoDao;
import cn.xiuminglee.entity.AccountChangeEvent;
import cn.xiuminglee.entity.AccountPay;
import cn.xiuminglee.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * @Author Xiuming Lee
 * @Description
 */
@Service
@Slf4j
public class AccountInfoServiceImpl implements AccountInfoService {

    @Autowired
    private AccountInfoDao accountInfoDao;

    /** 更新账户金额 */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAccountBalance(AccountChangeEvent accountChange) {
        //幂等校验
        if(accountInfoDao.isExistTx(accountChange.getTxNo())>0){
            return ;
        }
        int i = accountInfoDao.updateAccountBalance(accountChange.getAccountNo(), accountChange.getAmount());
        //插入事务记录，用于幂等控制
        accountInfoDao.addTx(accountChange.getTxNo());
    }

    /** 远程调用查询充值结果 */
    @Override
    public AccountPay queryPayResult(String tx_no) {
        RestTemplate restTemplate = new RestTemplate();
        //远程调用
        AccountPay payresult =restTemplate.getForObject("http://127.0.0.1:8001/payresult/" + tx_no,AccountPay.class);
        if("success".equals(payresult.getResult())){
            //更新账户金额
            AccountChangeEvent accountChangeEvent = new AccountChangeEvent();
            accountChangeEvent.setAccountNo(payresult.getAccountNo()); //账号
            accountChangeEvent.setAmount(payresult.getPayAmount()); //金额
            accountChangeEvent.setTxNo(payresult.getId()); //充值事务号
            updateAccountBalance(accountChangeEvent);
        }
        return payresult;
    }
}
