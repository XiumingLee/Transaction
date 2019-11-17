package cn.xiuminglee.transaction.jms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @Author Xiuming Lee
 * @Description
 */
@Service
public class CustomerService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private PlatformTransactionManager transactionManager;


    @JmsListener(destination = "customer:msg1:new", containerFactory = "msgFactory")  // 监听`customer:msg1:new`队列，有消息则消费
    @Transactional
    public void handle(String msg) {
        String reply = "Replied - " + msg;
        // 将消息转发到`customer:msg:reply`队列
        jmsTemplate.convertAndSend("customer:msg:reply", reply);
        if (msg.contains("error")) { // 如果发过来的消息好友`error`单词，则抛出错误。
            simulateError();
        }
    }

    public void handleInCode(String msg) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            String reply = "Replied-2 - " + msg;
            jmsTemplate.convertAndSend("customer:msg:reply", reply);
            if (!msg.contains("error")) {
                transactionManager.commit(status);
            } else {
                transactionManager.rollback(status);
            }
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    private void simulateError() {
        throw new RuntimeException("some Data error.");
    }
}
