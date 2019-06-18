package com.ptt.pttconfig.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigClient {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${eureka.client.service-url.defaultZone}")
    private String eurekaServers;
//    @Value("${server.port}")
//    private String port;

    @RequestMapping("/config")
    public String getConfig(){
        System.out.println("spring.application.name:"+applicationName+"-->"+"eureka.client.service-url.defaultZone:"+eurekaServers);
        return "applicationName:"+this.applicationName
                +"eureka.client.service-url.defaultZone:"+this.eurekaServers;
//                +"server.port:"+port;
    }
}
