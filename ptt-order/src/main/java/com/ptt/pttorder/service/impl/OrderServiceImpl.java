package com.ptt.pttorder.service.impl;

import com.ptt.pttorder.bean.Order;
import com.ptt.pttorder.bean.OrderItem;
import com.ptt.pttorder.dao.OrderDao;
import com.ptt.pttorder.service.ManageClientService;
import com.ptt.pttorder.service.OrderService;
import com.ptt.pttorder.utils.PageResult;
import com.ptt.pttorder.utils.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import sun.security.krb5.internal.Ticket;

import java.util.*;

@Service

public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderDao orderDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

//    @Autowired
//    private RestTemplate restTemplate;
//
//    private static final String REST_URL_PREFIX = "http://PTT-MANAGER-PROVIDER";

    @Autowired
    private ManageClientService manageClientService;


    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public String saveOrder(Order order) {

        String uuidOrderId = UUID.randomUUID().toString().replace("-","");
        order.setOrderId(uuidOrderId);
        order.setUpdateTime(new Date());
        order.setStatus(1);
        order.setStartTime(new Date());
        orderDao.saveOrder(order);
        return uuidOrderId;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public void updateOrder(String orderId, Integer status) {
        this.orderDao.updateOrderByOrderId(orderId,status,new Date());
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


    /**
     *根据订单id+用户id+status修改订单
     * */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public Boolean updateOrderByIdAndUserId(String orderId, Integer status, Long userId)throws Exception {

//        Order order = orderDao.queryByIdAndUserId(userId, orderId);
//        order.setUpdateTime(new Date());
        //更新订单的状态（维护更新时间）
        Integer count = -1;

        //如果订单变为4.交易关闭
        //需要调用票的接口，将票设置为未售（解开被锁的票）
        if (status == 4){
            count = orderDao.updateOrderByOrderId(orderId, status,new Date());
            Order order = orderDao.queryByIdAndUserId(userId, orderId);
            List<String> ticketIds = new ArrayList<>();
            for (OrderItem orderItem:order.getOrderItems()){
                String ticketId = orderItem.getTicketId();
                ticketIds.add(ticketId);
            }
            LOGGER.info("开始调用manage服务。。修改票的状态..ticketIds:" + ticketIds);
            //调用服务修改票的状态为未售！
//            ResultMessage resultMessage = this.restTemplate.postForObject(REST_URL_PREFIX + "/ticket/update/unlock", ticketIds, ResultMessage.class);
            ResultMessage resultMessage = this.manageClientService.updateTicketsStatus(ticketIds);
            boolean bool = resultMessage.getCode().equals("1");
            LOGGER.info("调用manage服务完成。。状态为:"+bool +"-->"+resultMessage.getCode() + "-->" + resultMessage.getMsg());
        }else if (status == 2){
            //如果订单变为2.付款，此时查看订单是否已经过期
            Order order = orderDao.queryByIdAndUserId(userId, orderId);
            Date updateTime = order.getUpdateTime();
            updateTime.setTime(updateTime.getTime()+5*60*1000);
            Date nowTime = new Date();
            LOGGER.info("nowTime"+nowTime.getTime()+"-->update"+updateTime.getTime());

            if (nowTime.getTime() > updateTime.getTime()){
                //订单已过期
                LOGGER.info("订单过期了。。");
                throw  new Exception("订单已经过期了..");

            }else{
                // 订单没有过期
                count = orderDao.updateOrderByOrderId(orderId, status,new Date());
            }
        }else{
            count = orderDao.updateOrderByOrderId(orderId, status,new Date());
        }
        return count == 1;
    }



}
