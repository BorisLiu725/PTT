package com.ptt.pttorder.service;

import com.ptt.pttorder.bean.Order;
import com.ptt.pttorder.utils.PageResult;
import org.apache.ibatis.annotations.Param;


public interface OrderService {

    String saveOrder(Order order);

    void updateOrder(String orderId,Integer status);

    PageResult queryOrderByUserId(Long userId);

    Boolean deleteOrderByUserIdAndOrderId(Long userId, String orderId);

//    void deleteOrder(String orderId);
    Boolean updateOrderByIdAndUserId (@Param("orderId") String orderId, @Param("status")Integer status,@Param("userId")Long userId) throws Exception;
}
