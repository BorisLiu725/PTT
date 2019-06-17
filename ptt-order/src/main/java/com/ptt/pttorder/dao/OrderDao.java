package com.ptt.pttorder.dao;


import com.ptt.pttorder.bean.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderDao {

    void saveOrder(Order order);

    void updateOrderByOrderId(@Param("orderId") String orderId, @Param("status") Integer status);

    List<Order> queryOrderByUserId(@Param("userId") Long userId);

    Integer deleteOrder(String orderId);

    Integer deleteOrderByUserIdAndOrderId(@Param("userId") Long userId,@Param("orderId") String orderId);
}
