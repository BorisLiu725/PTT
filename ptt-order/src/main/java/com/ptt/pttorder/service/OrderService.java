package com.ptt.pttorder.service;

import com.ptt.pttorder.bean.Order;
import com.ptt.pttorder.utils.PageResult;


public interface OrderService {

    String saveOrder(Order order);

    void updateOrder(String orderId,Integer status);

    PageResult queryOrderByUserId(Long userId);

    Boolean deleteOrderByUserIdAndOrderId(Long userId, String orderId);

//    void deleteOrder(String orderId);
}
