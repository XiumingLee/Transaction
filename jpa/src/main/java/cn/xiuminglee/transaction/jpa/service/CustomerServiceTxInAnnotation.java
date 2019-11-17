package cn.xiuminglee.transaction.jpa.service;

import cn.xiuminglee.transaction.jpa.dao.CustomerRepository;
import cn.xiuminglee.transaction.jpa.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author Xiuming Lee
 * @Description 使用注解方式的事务管理
 */
@Service
public class CustomerServiceTxInAnnotation {
    private static final Logger log = LoggerFactory.getLogger(CustomerServiceTxInAnnotation.class);

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * 注意区分javax的@Transactional注解和Spring的@Transactional注解
     * 通常使用Spring的@Transactional注解
     * @param customer
     * @return
     */
    @Transactional
    public Customer create(Customer customer) {
        log.info("CustomerService In Annotation create customer:{}", customer.getUsername());
        if (customer.getId() != null) {
            throw new RuntimeException("用户已经存在");
        }
        customer.setUsername("Annotation:" + customer.getUsername());
        return customerRepository.save(customer);
    }


}
