package cn.xiuminglee.service;

import cn.xiuminglee.entity.AccountChangeEvent;

/**
 * @Author Xiuming Lee
 * @Description
 */
public interface AccountInfoService {
    /** 更新账户，增加金额 */
    void addAccountInfoBalance(AccountChangeEvent accountChangeEvent);
}
