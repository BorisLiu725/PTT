package com.ppt.pptsso.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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


}
