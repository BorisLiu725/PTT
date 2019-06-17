package com.ptt.pttmanager.controller;

import com.ptt.pttmanager.bean.ResultSchedule;
import com.ptt.pttmanager.bean.Schedule;
import com.ptt.pttmanager.service.ScheduleService;
import com.ptt.pttmanager.utils.PageResult;
import com.ptt.pttmanager.utils.ScheduleResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;
    /**
     * 获取演出安排
     * type: 0当天的时间带次日凌晨
     * type：1 第二天凌晨，到次日（相对于第二天）凌晨
     * ...
     * (已测)
     * */
    @GetMapping("/query/list/{type}")
    public ResponseEntity<PageResult> queryAllScheduleByDate(@PathVariable("type") Integer type){
        try{
            List<ResultSchedule> schedules = this.scheduleService.queryAllScheduleByDate(type);
            PageResult pageResult = new PageResult();
            pageResult.setRows(schedules.size());
            pageResult.setPage(1);
            pageResult.setLists(schedules);
            return ResponseEntity.ok(pageResult);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

    @GetMapping("/query/list/{movieId}/{type}")
    public ResponseEntity<PageResult> queryAllScheduleByDate(@PathVariable("movieId")Long movieId,@PathVariable("type") Integer type){
        try{
            List<ResultSchedule> schedules = this.scheduleService.queryAllScheduleByDateAndMovieId(movieId,type);
            PageResult pageResult = new PageResult();
            pageResult.setRows(schedules.size());
            pageResult.setPage(1);
            pageResult.setLists(schedules);
            return ResponseEntity.ok(pageResult);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }




    /**
     * 获取所有的演出计划
     * */
    @GetMapping("/query/list")
    public ResponseEntity<PageResult> queryAllSchedule(){
        try{
            List<ResultSchedule> schedules = this.scheduleService.queryAllSchedule();
            PageResult pageResult = new PageResult();
            pageResult.setRows(schedules.size());
            pageResult.setPage(1);
            pageResult.setLists(schedules);
            return ResponseEntity.ok(pageResult);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }



    /**
     *
     * 根据演出计划id获取演出计划
     * (已测)
     * */
//    @GetMapping(value = "/query/{scheduleId}")
//    public ResponseEntity<Schedule> querySchedule(@PathVariable("scheduleId") Long scheduleId){
//        try{
//            Schedule schedule = scheduleService.queryById(scheduleId);
//            return ResponseEntity.ok(schedule);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//    }

    /**
     * 添加一个演出计划
     *
     * */
    @PostMapping(value = "/add")
    public ResponseEntity<Long> addSchedule(@RequestBody Schedule schedule){
        try{

            Long scheduleId = scheduleService.saveSchedule(schedule);
            return ResponseEntity.ok(scheduleId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(-1L);
    }

    /**
     * 删除一个演出计划
     * */
    @DeleteMapping(value = "/delete/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable("scheduleId") Long scheduleId){
        try{


            scheduleService.deleteById(scheduleId);
            return ResponseEntity.ok(null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 更新一个演出计划
     * 必须包含主键
     * */
    @PostMapping(value = "/update")
    public ResponseEntity<Void> updateSchedule(@RequestBody Schedule schedule){
        try{
            System.out.println(schedule+".........");
            scheduleService.update(schedule);
            return ResponseEntity.ok(null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 根据演出计划id获取演出计划
     * */
    @GetMapping(value = "/query/{scheduleId}")
    public ResponseEntity<Schedule> queryScheduleId(@PathVariable("scheduleId") Long scheduleId){
        try{
            Schedule schedule = scheduleService.queryScheduleId(scheduleId);
            return ResponseEntity.ok(schedule);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }



}
