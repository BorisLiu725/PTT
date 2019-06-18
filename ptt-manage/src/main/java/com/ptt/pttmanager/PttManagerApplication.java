package com.ptt.pttmanager;


//import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;
/**
 * 注意这个@MapperScan("com.ptt.pttmanager.mapper")
 * import tk.mybatis.spring.annotation.MapperScan;
 * */
@SpringBootApplication
@MapperScan({"com.ptt.pttmanager.mapper","com.ptt.pttmanager.dao"})
@EnableTransactionManagement
@EnableCaching
@EnableEurekaClient
@EnableDiscoveryClient
@EnableAsync  //开启异步注解
@EnableScheduling //开启基于注解的定时任务
@EnableCircuitBreaker //开启hystrix熔断机制的支持
public class PttManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PttManagerApplication.class, args);
    }


}
