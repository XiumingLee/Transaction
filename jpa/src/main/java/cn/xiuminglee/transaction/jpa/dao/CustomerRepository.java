package cn.xiuminglee.transaction.jpa.dao;

import cn.xiuminglee.transaction.jpa.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author Xiuming Lee
 * @Description
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findOneByUsername(String username);
}
