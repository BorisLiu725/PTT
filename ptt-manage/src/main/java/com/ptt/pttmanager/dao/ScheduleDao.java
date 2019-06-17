package com.ptt.pttmanager.dao;

import com.ptt.pttmanager.bean.ResultSchedule;
import com.ptt.pttmanager.bean.Schedule;
import com.ptt.pttmanager.utils.ScheduleResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ScheduleDao {
//    public List<Schedule> queryAllScheduleByDate(@Param("startTime")Date startTime,@Param("endTime")Date endTime);
     List<ResultSchedule> queryAllScheduleByDate(@Param("startTime")Date startTime, @Param("endTime")Date endTime);

     //插入演出安排
     void save(Schedule schedule);

     Schedule queryScheduleId(@Param("scheduleId") Long scheduleId);

     List<ResultSchedule> queryAll();

     Schedule queryScheduleId2(@Param("scheduleId") Long scheduleId);

    List<ResultSchedule> queryAllScheduleByDateAndMovieId(@Param("movieId") Long movieId, @Param("startTime")Date startTime, @Param("endTime")Date endTime);
}
