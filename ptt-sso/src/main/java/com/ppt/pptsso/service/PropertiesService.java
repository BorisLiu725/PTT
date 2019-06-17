package com.ppt.pptsso.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:sso-dev.properties")
public class PropertiesService {

//    @Value("${ACTIVE_MAIL_URL}")
//    public static final String ACTIVE_MAIL_URL = "http://localhost:8002/user/active";
//
//    @Value("${PTT_CACHE_TOKEN}")
//    public static final String PTT_CACHE_TOKEN = "PTT_TOKEN";


//    @Value("${ACTIVE_MAIL_URL}")
//    public  String ACTIVE_MAIL_URL ;
//
//    @Value("${PTT_CACHE_TOKEN}")
//    public  String PTT_CACHE_TOKEN;
}
