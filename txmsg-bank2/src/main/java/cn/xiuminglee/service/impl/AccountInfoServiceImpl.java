package cn.xiuminglee.service.impl;

import cn.xiuminglee.dao.AccountInfoDao;
import cn.xiuminglee.entity.AccountChangeEvent;
import cn.xiuminglee.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author Xiuming Lee
 * @Description
 */
@Service
@Slf4j
public class AccountInfoServiceImpl implements AccountInfoService {

    @Autowired
    private AccountInfoDao accountInfoDao;

    /** 更新账户，增加金额 */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addAccountInfoBalance(AccountChangeEvent accountChangeEvent) {
        log.info("bank2更新本地账号，账号：{},金额：{}",accountChangeEvent.getToAccountNo(),accountChangeEvent.getAmount());

        // 幂等性判断
        if(accountInfoDao.isExistTx(accountChangeEvent.getTxNo())>0){
            return ;
        }
        //增加金额
        accountInfoDao.updateAccountBalance(accountChangeEvent.getToAccountNo(),accountChangeEvent.getAmount());
        //添加事务记录，用于幂等
        accountInfoDao.addTx(accountChangeEvent.getTxNo());

        //if(accountChangeEvent.getAmount() == 4){
        //    throw new RuntimeException("人为制造异常");
        //}
    }
}
