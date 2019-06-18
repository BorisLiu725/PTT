package com.ptt.pttmanager.controller;

import com.ptt.pttmanager.service.impl.SeatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
@RequestMapping("/seat")
public class SeatController {

    @Autowired
    private SeatService seatService;


    private static final Logger LOGGER = LoggerFactory.getLogger(SeatController.class);

    /**
     * TODO
     * 批量更新数据作为
     * @Param ids  ：待更新的座位id
     * @Param status ：要更新的状态
     * */
    @PostMapping("/update/list/{status}")
    public ResponseEntity<Void> updateSeats(@RequestBody Integer[] ids,@PathVariable("status")Integer status){
        try{
            LOGGER.info("即将吧座位"+ids+"-->修改为状态"+status);
            seatService.updateSeats(Arrays.asList(ids), status);
            return ResponseEntity.ok(null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }




}
