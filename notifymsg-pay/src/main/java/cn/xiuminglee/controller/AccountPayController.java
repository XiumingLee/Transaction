package cn.xiuminglee.controller;

import cn.xiuminglee.entity.AccountPay;
import cn.xiuminglee.service.AccountPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @Author Xiuming Lee
 * @Description
 */
@RestController
public class AccountPayController {

    @Autowired
    AccountPayService accountPayService;

    /** 充值 */
    @RequestMapping(value = "/paydo")
    public AccountPay pay(AccountPay accountPay){
        //生成事务编号
        String txNo = UUID.randomUUID().toString();
        accountPay.setId(txNo);
        return accountPayService.insertAccountPay(accountPay);
    }

    /** 查询充值结果 */
    @RequestMapping(value = "/payresult/{txNo}")
    public AccountPay payresult(@PathVariable("txNo") String txNo){
        return accountPayService.getAccountPay(txNo);
    }
}
