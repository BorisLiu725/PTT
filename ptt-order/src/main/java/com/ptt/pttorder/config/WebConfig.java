package com.ptt.pttorder.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

@Configuration
public class WebConfig {
//
    @Bean
    public FilterRegistrationBean crossDomainFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();

        CrossDomainFilter crossDomainFilter = new CrossDomainFilter();
        registrationBean.setFilter(crossDomainFilter);
        List<String> urls = new ArrayList<>();
        urls.add("/*");
        registrationBean.setUrlPatterns(urls);
        return registrationBean;
    }


    @Bean
    @LoadBalanced //默认采用轮循的方式
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    public Executor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(200);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("自定义线程");
        executor.initialize();
        return executor;
    }


}
