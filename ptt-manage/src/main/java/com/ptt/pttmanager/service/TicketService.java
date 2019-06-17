package com.ptt.pttmanager.service;

import com.ptt.pttmanager.bean.Ticket;
import com.ptt.pttmanager.dao.TicketDao;
import com.sun.xml.internal.bind.v2.TODO;
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

    public List<Ticket> queryByScheduleId(Long scheduleId){
        System.out.println("开始查询scheduleId-->"+scheduleId);
        List<Ticket> tickets = ticketDao.queryByScheduleId(scheduleId);
        System.out.println("查询结束"+tickets);
        return tickets;
    }
    /**
     * 检查票的状态
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

        System.out.println("共有"+tickets.size()+"更新了"+count+"条数据...");
        if (count!=tickets.size()){
            throw new Exception("请重新刷新票数据...锁票失败！");
        }
        return count == tickets.size();
    }

    public Boolean updateTicketsStatus(List<String> tickets) throws Exception {
        int sum = 0;
        for (int i = 0; i < tickets.size(); i++) {
            Integer count = this.ticketDao.updateTicketStatusByTicketId(tickets.get(i),1);
            sum+=count;
        }
        if (sum != tickets.size()){
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
