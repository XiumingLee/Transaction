package cn.xiuminglee.service;

import cn.xiuminglee.entity.AccountPay;

/**
 * @Author Xiuming Lee
 * @Description
 */
public interface AccountPayService {
    //充值
    AccountPay insertAccountPay(AccountPay accountPay);
    //查询充值结果
    AccountPay getAccountPay(String txNo);
}

