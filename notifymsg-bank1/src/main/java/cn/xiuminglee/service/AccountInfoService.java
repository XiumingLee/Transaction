package cn.xiuminglee.service;

import cn.xiuminglee.entity.AccountChangeEvent;
import cn.xiuminglee.entity.AccountPay;

/**
 * @Author Xiuming Lee
 * @Description
 */
public interface AccountInfoService {

    /** 更新账户金额 */
    void updateAccountBalance(AccountChangeEvent accountChange);

    /** 查询充值结果（远程调用） */
    AccountPay queryPayResult(String tx_no);

}
