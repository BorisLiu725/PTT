package com.ptt.pttmanager.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 消费者负载均衡
 * */
@FeignClient(value = "PTT-MANAGER-PROVIDER")
public interface ManageClientService {

    @GetMapping("/ticket/get")
    public List<String> get();


}
