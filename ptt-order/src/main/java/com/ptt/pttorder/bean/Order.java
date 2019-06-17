package com.ptt.pttorder.bean;

import java.util.Date;
import java.util.List;

/**
 * 订单
 *
 * */
public class Order {

    //订单号
    private String orderId;
    //用户id
    private Long userId;
    //订单状态1、未付款，2、已付款，3交易成功、4.交易关闭
    private Integer status;
    //订单总价
    private Long totalPrice;
    //下单时间
    private Date startTime;
    //更新订单时间
    private Date updateTime;
    //订单条目
    private List<OrderItem> orderItems;

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", userId=" + userId +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                ", startTime=" + startTime +
                ", updateTime=" + updateTime +
                ", orderItems=" + orderItems +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
