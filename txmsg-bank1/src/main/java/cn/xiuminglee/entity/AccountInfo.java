package cn.xiuminglee.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Xiuming Lee
 * @Description
 */
@Data
public class AccountInfo implements Serializable {
    private Long id;
    private String accountName;
    private String accountNo;
    private String accountPassword;
    private Double accountBalance;
}
