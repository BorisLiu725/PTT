package com.ptt.pttmanager.dao;

import com.ptt.pttmanager.bean.Ticket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TicketDao {

    //批量保存票的信息
    void saveTickets(List<Ticket> list);

    //根据演出厅id查询票
    List<Ticket> queryByScheduleId(Long scheduleId);
    //根据票的id返回票
    List<Ticket> queryTicketStateByIds(List<String> ids);
    //更新票的状态
//    Integer updateTicketStatus(@Param("ids") Map<String,Long> ids);

    Integer updateTicketStatusByTicketIdWithStatus3(@Param("id") String id,@Param("version")Long version);

    Integer updateTicketStatusByTicketIdWithStatus1(@Param("id") String id,@Param("version")Long version);

    Integer updateTicketStatusByTicketId(@Param("id") String id,@Param("status")Integer status);

    Ticket queryByTicketId(@Param("id")String id);
}
