package cn.xiuminglee.controller;

import cn.xiuminglee.entity.AccountChangeEvent;
import cn.xiuminglee.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @Author Xiuming Lee
 * @Description
 */
@RestController
@Slf4j
public class AccountInfoController {
    @Autowired
    private AccountInfoService accountInfoService;

    /**
     * 转账请求
     * @param formAccountNo 账号
     * @param toAccountNo 账号
     * @param amount 金额
     * @return
     */
    @RequestMapping(value = "/transfer")
    public String transfer(@RequestParam("formAccountNo")String formAccountNo, @RequestParam("toAccountNo")String toAccountNo,@RequestParam("amount") Double amount){
        //创建一个事务id，作为消息内容发到mq
        String tx_no = UUID.randomUUID().toString();
        AccountChangeEvent accountChangeEvent = new AccountChangeEvent(formAccountNo,toAccountNo,amount,tx_no);
        //发送消息
        accountInfoService.sendUpdateAccountBalance(accountChangeEvent);
        return "转账成功";
    }
}
