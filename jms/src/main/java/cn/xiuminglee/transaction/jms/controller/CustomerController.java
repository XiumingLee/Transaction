package cn.xiuminglee.transaction.jms.controller;

import cn.xiuminglee.transaction.jms.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Xiuming Lee
 * @Description
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private CustomerService customerService;

    /**
     * 通过触发JmsListener来实现消息的转发
     * @param msg
     */
    @PostMapping("/message1/listen")
    public void createMsgWithListener(String msg) {
        jmsTemplate.convertAndSend("customer:msg1:new", msg);
    }

    /**
     * 直接调用CustomerService的handle()方法来实现转发
     * @param msg
     */
    @PostMapping("/message1/direct")
    public void createMsgDirect(String msg) {
        customerService.handle(msg);
    }

    /**
     * 查看转发后的消息
     * @return
     */
    @GetMapping("/message")
    public String getMsg() {
        Object reply = jmsTemplate.receiveAndConvert("customer:msg:reply");
        return String.valueOf(reply);
    }
}
