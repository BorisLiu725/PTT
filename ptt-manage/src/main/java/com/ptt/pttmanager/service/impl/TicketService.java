package com.ptt.pttmanager.service.impl;

import com.ptt.pttmanager.bean.Ticket;
import com.ptt.pttmanager.dao.TicketDao;
import com.ptt.pttmanager.service.BaseService;
import com.sun.xml.internal.bind.v2.TODO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TicketService extends BaseService<Ticket> {

    @Autowired
    private TicketDao ticketDao;

    private Logger logger = LoggerFactory.getLogger(TicketService.class);

    /**
     * 通过scheduleId查询演出票
     * */
    public List<Ticket> queryByScheduleId(Long scheduleId){
        logger.info("开始查询scheduleId-->"+scheduleId);
        List<Ticket> tickets = ticketDao.queryByScheduleId(scheduleId);
        logger.info("查询结束"+tickets);
        return tickets;
    }
    /**
     * 检查票的状态
     * 批量修改票的状态为已售！
     * */
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public Boolean checkTicketState(List<String> ids) throws Exception {
        List<Ticket> tickets = this.ticketDao.queryTicketStateByIds(ids);
        int count = 0;
        for (int i = 0; i < tickets.size(); i++) {
            if (tickets.get(i).getStatus()!=1){
                return false;
            }
            count+=this.ticketDao.updateTicketStatusByTicketIdWithStatus3(tickets.get(i).getTicketId(),tickets.get(i).getVersion());
        }

        logger.info("共有"+tickets.size()+"更新了"+count+"条数据...");
        if (count!=tickets.size()){
            logger.info("请重新刷新票数据...锁票失败！");
            throw new Exception("请重新刷新票数据...锁票失败！");

        }
        return count == tickets.size();
    }
    /**
     * 批量修改票的状态为未售！
     * */
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public Boolean updateTicketsStatusToNotSale(List<String> tickets) throws Exception {
        int sum = 0;
        for (int i = 0; i < tickets.size(); i++) {
            Integer count = this.ticketDao.updateTicketStatusByTicketId(tickets.get(i),1);
            //超时退票不存在并发
            //通过票的id查询票，继而获得票的version
//            Ticket ticket = this.ticketDao.queryByTicketId(tickets.get(i));
            //更改票的状态
//            Integer count = this.ticketDao.updateTicketStatusByTicketIdWithStatus1(tickets.get(i),ticket.getVersion());
            sum+=count;
        }
        if (sum != tickets.size()){
            logger.info("解锁票失败...");
            throw new Exception("解锁票失败...");
        }
        return true;
    }

    public Ticket queryByTicketId(String ticketId) {
        return this.ticketDao.queryByTicketId(ticketId);
    }

//
//    public Integer updateTicketsStatus(List<String> tickets, Integer status) {
////        this.ticketDao.updateTicketsStatus(tickets,status);
//    }

    public Boolean updateTicketStatusByTicketId(String ticket,Integer status){
        Integer count = this.ticketDao.updateTicketStatusByTicketId(ticket, status);
        return count == 1;
    }






}
