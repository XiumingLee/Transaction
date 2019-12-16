package cn.xiuminglee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author Xiuming Lee
 * @Description
 */
@SpringBootApplication
public class NotifyMsgBankApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotifyMsgBankApplication.class,args);
    }

}
