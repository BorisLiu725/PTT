package com.ptt.pttdashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class PttDashBoardApp {
    public static void main(String[] args) {
        SpringApplication.run(PttDashBoardApp.class,args);
    }
}
