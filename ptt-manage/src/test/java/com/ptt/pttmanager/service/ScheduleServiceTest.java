package com.ptt.pttmanager.service;

import com.ptt.pttmanager.bean.Schedule;
import com.ptt.pttmanager.mapper.ScheduleMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ScheduleServiceTest {


    @Autowired
    private ScheduleMapper scheduleMapper;

    @Test
    public void queryAllScheduleByDate() {
        Integer type = 1;
        Date startTime = getDate(type);
        Date endTime = getDate(type+1);
        Example example = new Example(Schedule.class);
        example.createCriteria().andGreaterThanOrEqualTo("startTime",startTime);
        example.createCriteria().andLessThanOrEqualTo("endTime",endTime);

        List<Schedule> schedules = scheduleMapper.selectByExample(example);
        for (Schedule schedule: schedules) {
            System.out.println(schedule);
        }

    }

    /**
     * 得到目标日期
     * 0：就是现在的时间
     * 1 加一天
     * 2 加两天
     * 时分秒都是00:00:00
     * */
    private Date getDate(Integer type){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,type);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (type == 0){
            System.out.println(dateFormat.format(calendar.getTime())+"-->"+dateFormat.format(new Date()));
            return calendar.getTime();
        }
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        System.out.println(dateFormat.format(calendar.getTime())+"-->"+dateFormat.format(new Date()));
        return calendar.getTime();
    }

}