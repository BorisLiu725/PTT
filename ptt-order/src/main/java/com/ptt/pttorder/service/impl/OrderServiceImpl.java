package com.ptt.pttorder.service.impl;

import com.ptt.pttorder.bean.Order;
import com.ptt.pttorder.bean.OrderItem;
import com.ptt.pttorder.dao.OrderDao;
import com.ptt.pttorder.service.OrderService;
import com.ptt.pttorder.utils.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service

public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderDao orderDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);



    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public String saveOrder(Order order) {

        String uuidOrderId = UUID.randomUUID().toString().replace("-","");
        order.setOrderId(uuidOrderId);
        order.setUpdateTime(new Date());
        order.setStatus(1);
        //TODO 先暂时设置一个时间
        order.setStartTime(new Date());
        orderDao.saveOrder(order);
        return uuidOrderId;
    }





    @Override
    public void updateOrder(String orderId,Integer status) {
        orderDao.updateOrderByOrderId(orderId,status);
        if (status == 2){
            //如果订单变为已付款
            //需要调用票的接口，将票设置为已售
            //TODO
        }
    }




    @Override
    public PageResult queryOrderByUserId(Long userId) {
        List<Order> orders = orderDao.queryOrderByUserId(userId);
        PageResult pageResult = new PageResult();
        pageResult.setRows(1);
        pageResult.setPage(orders.size());
        pageResult.setLists(orders);
        return pageResult;
    }

    @Override
    public Boolean deleteOrderByUserIdAndOrderId(Long userId, String orderId) {
        Integer count = this.orderDao.deleteOrderByUserIdAndOrderId(userId, orderId);
        return count == 1;
    }

//    @Override
//    public Boolean deleteOrder(String orderId) {
//        Integer count = this.orderDao.deleteOrder(orderId);
//        return count == 1;
//    }


}
