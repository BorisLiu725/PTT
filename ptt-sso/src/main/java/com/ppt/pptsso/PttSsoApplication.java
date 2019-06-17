package com.ppt.pptsso;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.ppt.pptsso.mapper")
@SpringBootApplication
@EnableTransactionManagement
@EnableRabbit
@EnableCaching //可以添加缓存
public class PttSsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PttSsoApplication.class,args);
    }

}

