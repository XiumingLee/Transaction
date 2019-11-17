package cn.xiuminglee.transaction.jpa.controller;

import cn.xiuminglee.transaction.jpa.dao.CustomerRepository;
import cn.xiuminglee.transaction.jpa.domain.Customer;
import cn.xiuminglee.transaction.jpa.service.CustomerServiceTxInAnnotation;
import cn.xiuminglee.transaction.jpa.service.CustomerServiceTxInCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Xiuming Lee
 * @Description
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerServiceTxInAnnotation customerService;
    @Autowired
    private CustomerServiceTxInCode customerServiceInCode;
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/annotation")
    public Customer createInAnnotation(@RequestBody Customer customer) {
        log.info("CustomerController create in annotation create customer:{}", customer.getUsername());
        return customerService.create(customer);
    }

    @PostMapping("/code")
    public Customer createInCode(@RequestBody Customer customer) {
        log.info("CustomerController create in code create customer:{}", customer.getUsername());
        return customerServiceInCode.create(customer);
    }

    @GetMapping("")
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

}
