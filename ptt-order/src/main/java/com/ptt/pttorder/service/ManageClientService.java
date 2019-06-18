package com.ptt.pttorder.service;

import com.ptt.pttorder.utils.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 消费者负载均衡
 * 提供服务降级（调用方提供）
 * */
@FeignClient(value = "PTT-MANAGER-PROVIDER",fallbackFactory = ManageClientServiceFallBackFactory.class)
public interface ManageClientService {
    /**
     * 测试
     * */
    @GetMapping("/ticket/get")
    public List<String> get();

    /**
     * 解锁票
     * @Param tickets 票的id集合
     * */
    @PostMapping("/ticket/update/unlock")
    public ResultMessage updateTicketsStatus(@RequestBody List<String> tickets);




}
