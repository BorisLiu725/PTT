package com.ptt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.ptt.pttorder"})
@ComponentScan("com.ptt.pttorder")
@EnableAsync  //开启异步注解
@EnableScheduling //开启基于注解的定时任务
public class PttOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(PttOrderApplication.class,args);
    }

}
