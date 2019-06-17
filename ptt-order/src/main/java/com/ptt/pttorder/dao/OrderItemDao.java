package com.ptt.pttorder.dao;

import com.ptt.pttorder.bean.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderItemDao {

    List<OrderItem> queryOrderItemsByOrderId(String orderId);
}
