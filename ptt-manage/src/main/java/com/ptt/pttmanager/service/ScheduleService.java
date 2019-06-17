package com.ptt.pttmanager.service;


import com.ptt.pttmanager.bean.*;
import com.ptt.pttmanager.dao.ScheduleDao;
import com.ptt.pttmanager.dao.SeatDao;
import com.ptt.pttmanager.dao.StudioDao;
import com.ptt.pttmanager.dao.TicketDao;
import com.ptt.pttmanager.mapper.ScheduleMapper;
import com.ptt.pttmanager.mapper.StudioMapper;
import com.ptt.pttmanager.utils.ScheduleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ScheduleService extends BaseService<Schedule>{

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleService.class);

    @Autowired
    private ScheduleMapper scheduleMapper;
    @Autowired
    private ScheduleDao scheduleDao;
//    @Autowired
//    private StudioMapper studioMapper;

    @Autowired
    private StudioDao studioDao;
    @Autowired
    private TicketDao ticketDao;

    /**
     * 添加演出计划，并确定
     * TODO 生成票
     * 异步生成票
     * */
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public Long saveSchedule(Schedule schedule){
        //保存演出计划
        LOGGER.info("开始插入演出计划..."+schedule);
        scheduleMapper.insert(schedule);
       // scheduleDao.save(schedule);
        LOGGER.info("插入演出计划完成..."+schedule);
        LOGGER.info("开始生成票...");
        // 创建演出厅手动保存座位
        generateTicket(schedule);
        LOGGER.info("票保存完成");
        return schedule.getScheduleId();

    }
    private void generateTicket(Schedule schedule){
        LOGGER.info("schedule为："+schedule);
        //设置座位
        List<Ticket> tickets = new ArrayList<Ticket>();
        //根据演出计划id查询演出厅
        //查询演出厅
        LOGGER.info("根据演出厅id查询演出厅...StudioId="+schedule.getStudioId());
        Studio studio = studioDao.queryById(schedule.getStudioId());
        LOGGER.info("演出厅为:"+studio);

        for (int i = 0; i < studio.getSeats().size(); i++) {
            // 座位已经损坏则不能生成对应的票
            if (studio.getSeats().get(i).getStatus() == 4){
                continue;
            }
            Ticket ticket = new Ticket();
            String ticketId = UUID.randomUUID().toString().replace("-","");
            ticket.setTicketId(ticketId);
            ticket.setScheduleId(schedule.getScheduleId());
            ticket.setPrice(schedule.getNewPrice());
            ticket.setQrCode(null);
            ticket.setSeatId(studio.getSeats().get(i).getSeatId());
            tickets.add(ticket);
            //默认票的状态为未售1
            ticket.setStatus(1);
            //票的二维码

            ticket.setQrCode("");
        }
        //批量保存票
        ticketDao.saveTickets(tickets);
    }
    /**
     * 得到票的二维码
     * */
//    public String getTicketQrCode(Ticket ticket){
//        String prfix = "http://qr.liantu.com/api.php?";
//
//        StringBuilder sb = new StringBuilder();
//        sb.append()
//
//
//
//    }




    public List<ResultSchedule> queryAllScheduleByDate(Integer type) {
        Date startTime = getDate(type);
        Date endTime = getDate(type+1);
        Example example = new Example(Schedule.class);
        System.out.println(startTime + "-->" + endTime);
        example.createCriteria().andGreaterThanOrEqualTo("startTime",startTime);
        example.createCriteria().andLessThan("endTime",endTime);

//        List<Schedule> schedules = scheduleMapper.selectByExample(example);
//        List<Schedule> schedules = scheduleDao.queryAllScheduleByDate(startTime, endTime);
        List<ResultSchedule> resultSchedules = scheduleDao.queryAllScheduleByDate(startTime, endTime);
        for (ResultSchedule resultSchedule: resultSchedules) {
            System.out.println(resultSchedule);
        }
        return resultSchedules;
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

    /**
     *
     * */
    public Schedule queryScheduleId(Long scheduleId) {
        return this.scheduleDao.queryScheduleId(scheduleId);
    }

    public List<ResultSchedule> queryAllSchedule() {
        return this.scheduleDao.queryAll();
    }

    public List<ResultSchedule> queryAllScheduleByDateAndMovieId(Long movieId, Integer type) {
        Date startTime = getDate(type);
        Date endTime = getDate(type+1);
        Example example = new Example(Schedule.class);
        System.out.println(startTime + "-->" + endTime);
//        List<Schedule> schedules = scheduleMapper.selectByExample(example);
//        List<Schedule> schedules = scheduleDao.queryAllScheduleByDate(startTime, endTime);
        List<ResultSchedule> resultSchedules = scheduleDao.queryAllScheduleByDateAndMovieId(movieId,startTime, endTime);
        for (ResultSchedule resultSchedule: resultSchedules) {
            System.out.println(resultSchedule);
        }
        return resultSchedules;
    }
}
