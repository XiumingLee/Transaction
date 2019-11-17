package cn.xiuminglee.transaction.jpa.service;

import cn.xiuminglee.transaction.jpa.dao.CustomerRepository;
import cn.xiuminglee.transaction.jpa.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @Author Xiuming Lee
 * @Description 代码形式实现事务管理
 */
@Service
public class CustomerServiceTxInCode {
    private static final Logger log = LoggerFactory.getLogger(CustomerServiceTxInCode.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;  // 注入Spring的事务管理类

    public Customer create(Customer customer) {
        log.info("CustomerService In Code create customer:{}", customer.getUsername());
        if (customer.getId() != null) {
            throw new RuntimeException("用户已经存在");
        }
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);  // 设置隔离级别
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED); // 设置传播行为
        def.setTimeout(15);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            customer.setUsername("Code:" + customer.getUsername());
            customerRepository.save(customer);
            transactionManager.commit(status); // 事务提交
            return customer;
        } catch (Exception e) {
            transactionManager.rollback(status); // 事务回滚
            throw e;
        }
    }
}
