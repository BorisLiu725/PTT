package com.ptt.pttmanager.controller;

import com.ptt.pttmanager.bean.Ticket;
import com.ptt.pttmanager.service.impl.TicketService;
import com.ptt.pttmanager.utils.PageResult;
import com.ptt.pttmanager.utils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private DiscoveryClient discoveryClient;


    @GetMapping("/list/{scheduleId}")
    public ResponseEntity<PageResult> queryByScheduleId(@PathVariable("scheduleId")Long scheduleId){
       try{
           PageResult pageResult = new PageResult();
           pageResult.setPage(1);
           List<Ticket> tickets = ticketService.queryByScheduleId(scheduleId);
           pageResult.setRows(tickets.size());
           pageResult.setLists(tickets);
           return ResponseEntity.ok(pageResult);
       }catch (Exception e){
           e.printStackTrace();
       }
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    /**
     * 锁票
     * */
    @PostMapping("/check")
    @ResponseBody
    public ResultMessage checkTicketState(@RequestBody  List<String> ids){
        System.out.println(ids);

        ResultMessage resultMessage = new ResultMessage();
        try{
            Boolean bool = this.ticketService.checkTicketState(ids);
            if (bool){
                resultMessage.setMsg("锁票成功..");
                resultMessage.setCode("1");
            }else{
                resultMessage.setCode("2");
                resultMessage.setMsg("锁票失败..");
            }

        }catch (Exception e){
            e.printStackTrace();
            resultMessage.setMsg(e.getMessage());
            resultMessage.setCode("3");
            System.out.println(e.getMessage());
        }
        return resultMessage;
    }

    @GetMapping("/get")
    @ResponseBody
    public List<String> get(){
       List<String> s = new ArrayList<>();
       s.add("ddfsgdfgdth212d1g");
       s.add("dskjfoiaesj");
        return s;
   }

//   @GetMapping("/discovery")
//   public Object discovery(){
//        List<String> list = discoveryClient.getServices();
//       System.out.println("...."+list);
//       List<ServiceInstance> instances = discoveryClient.getInstances()
//
//   }


    @PostMapping("/update/unlock")
    @ResponseBody
    public ResultMessage updateTicketsStatus(@RequestBody List<String> tickets){
        System.out.println(tickets);
        ResultMessage resultMessage = new ResultMessage();
        try{
            Boolean bool = this.ticketService.updateTicketsStatusToNotSale(tickets);
            if (bool){
                resultMessage.setMsg("解锁成功..");
                resultMessage.setCode("1");
            }else{
                resultMessage.setCode("2");
                resultMessage.setMsg("解锁失败，请重新解锁...");
            }

        }catch (Exception e){
            e.printStackTrace();
            resultMessage.setMsg(e.getMessage());
            resultMessage.setCode("3");
            System.out.println(e.getMessage());
        }
        return resultMessage;
    }

    @GetMapping("/query/{ticketId}")
    @ResponseBody
    public Ticket queryByTicketId(@PathVariable("ticketId") String ticketId){
        return this.ticketService.queryByTicketId(ticketId);
    }

    @GetMapping("/update/{ticketId}/{status}")
    @ResponseBody
    public Map<String,Object> updateTicketStatusByTicketId(@PathVariable("ticketId")String ticketId,@PathVariable("status")Integer status){
        Map<String,Object> map = new HashMap<>();
        Boolean bool = false;
        try{
           bool = this.ticketService.updateTicketStatusByTicketId(ticketId, status);
            if (bool){
              map.put("msg","200");
            }else{
                map.put("msg","500");
            }
            return map;
        }catch (Exception e){
            e.printStackTrace();
        }

       return map;

    }



}
