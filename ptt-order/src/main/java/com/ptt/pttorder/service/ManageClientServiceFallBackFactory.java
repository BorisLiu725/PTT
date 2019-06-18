package com.ptt.pttorder.service;

import com.ptt.pttorder.utils.ResultMessage;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
/**
 * 服务降级
 *
 * */
@Component
public class ManageClientServiceFallBackFactory implements FallbackFactory<ManageClientService> {
    @Override
    public ManageClientService create(Throwable throwable) {
        return new ManageClientService() {
            @Override
            public List<String> get() {
                List<String> list =  new ArrayList<>();
                list.add("服务端暂时关闭了此服务，调用方提供服务降级..");
                return list;
            }

            @Override
            public ResultMessage updateTicketsStatus(List<String> tickets) {
                ResultMessage resultMessage = new ResultMessage();
                resultMessage.setCode("500");
                resultMessage.setMsg("服务端暂时关闭了此服务，调用方提供服务降级..");
                return resultMessage;
            }
        };
    }
}
