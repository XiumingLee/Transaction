package cn.xiuminglee.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Xiuming Lee
 * @Description 账户改变发消息需要的实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountChangeEvent implements Serializable {

    /** 消费方账号 */
    private String fromAccountNo;
    /** 收款方账号 */
    private String toAccountNo;
    /**
     * 变动金额
     */
    private double amount;
    /**
     * 事务号
     */
    private String txNo;

}

