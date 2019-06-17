package com.ptt.pttmanager.dao;

import com.ptt.pttmanager.bean.Seat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SeatDao {
    /**
     * 保存座位
     * */
    public void saveSeats(List<Seat> list);

//    List<Seat> queryAllSeatsByScheduleIdAndStudioId(@Param("scheduleId") Long scheduleId, @Param("studioId") Long studioId);

    void updateSeats(@Param("ids") List<Integer> ids,@Param("status")Integer status);

    List<Seat> queryAllSeatsByStudioId(Long studioId);

    Seat querySeatById(Long seatId);
}
