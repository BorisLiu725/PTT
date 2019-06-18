package com.ptt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * Feign内部继承了Ribbon，所以可以进行负载均衡，
 *
 * 利用Ribbon维护了STUDY-SPRINGCLOUD-DEPT的服务列表，并且通过轮询实现了客户端的负载均衡。
 * 而与Ribbon不同的是，通过Feign只需要定义服务绑定接口且以声明式的方法，优雅的而简单的实现了服务调用。
 * */

@EnableTransactionManagement
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.ptt.pttorder.service"})
@ComponentScan("com.ptt.pttorder")
@EnableAsync  //开启异步注解
@EnableScheduling //开启基于注解的定时任务
public class PttOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(PttOrderApplication.class,args);
    }

}
