package com.ptt.pttconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class Config7003GitHubApp {
    public static void main(String[] args) {
        SpringApplication.run(Config7003GitHubApp.class,args);
    }
}
