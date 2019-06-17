package com.ptt.pttmanager.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

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


//    @Bean
//    @LoadBalanced
//    public RestTemplate getRestTemplate(){
//        return new RestTemplate();
//    }


}
