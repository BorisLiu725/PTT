package com.ptt.pttorder.controller;

import com.ptt.pttorder.bean.Order;
import com.ptt.pttorder.service.ManageClientService;
import com.ptt.pttorder.service.OrderService;
import com.ptt.pttorder.utils.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ManageClientService manageClientService;

//    @Autowired
//    private RestTemplate restTemplate;

//    private static final String REST_URL_PREFIX = "http://PTT-MANAGER-PROVIDER";


    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    /**
     * 保存订单，返回订单编号
     * */
    @PostMapping("/save")
    public ResponseEntity<String> saveOrder(@RequestBody Order order){
        try{
           LOGGER.info("前端传递过来的订单为："+order);
            String orderId = this.orderService.saveOrder(order);
            LOGGER.info("订单编号为："+orderId);
            return ResponseEntity.ok(orderId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 更改订单状态
     * 订单状态1、未付款，2、已付款，3交易成功、4.交易关闭
     * */
    @GetMapping("/update/{orderId}/{status}")
    public ResponseEntity<Map<String,String>> updateOrder(@PathVariable("orderId")String orderId, @PathVariable("status")Integer status){
        Map<String,String> msg = new HashMap<>();
        try{
            this.orderService.updateOrder(orderId,status);
            msg.put("status","200");

            return ResponseEntity.ok(msg);
        }catch (Exception e){

            e.printStackTrace();
        }
        msg.put("status","500");
        return ResponseEntity.ok(msg);
    }
    /**
     * 通过顶订单号和用户id修改订单状态
     * 如果超时的话，将票的状态恢复为未售！4-交易关闭
     * */
    @PutMapping("/update/status/{userId}/{orderId}/{status}")
    public ResponseEntity<Map<String,String>> updateOrderByUserId(@PathVariable ("userId")Long userId,@PathVariable ("orderId")String orderId, @PathVariable("status")Integer status){
        Map<String,String> msg = new HashMap<>();
        try{
//            this.orderService.updateOrder(orderId,status);
            Boolean bool = this.orderService.updateOrderByIdAndUserId(orderId, status, userId);
            msg.put("status","200");
            LOGGER.info("orderId"+orderId+"-->"+"userId"+userId+"-->status"+status +"成功吗？"+bool);
            return ResponseEntity.ok(msg);
        }catch (Exception e){
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        msg.put("status","500");
        LOGGER.info("orderId"+orderId+"-->"+"userId"+userId+"-->status"+status +"500错误");
        return ResponseEntity.ok(msg);
    }


    @GetMapping("/query/{userId}")
    public ResponseEntity<PageResult> queryOrderByUserId(@PathVariable("userId")Long userId) {
        try {
            PageResult resultOrder =  this.orderService.queryOrderByUserId(userId);
           return ResponseEntity.ok(resultOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

//    @GetMapping("/order/get")
//    @ResponseBody
//    public List<String> get(){
//        return this.manageClientService.get();
//    }

    /**
     * 测试微服务接口是否接通
     * */
    @GetMapping(value = "/consumer/ticket/get")
    @ResponseBody
    public List<String> get(){
//        return restTemplate.getForObject(REST_URL_PREFIX+"/ticket/get",List.class);
        return this.manageClientService.get();
    }

//    @DeleteMapping("/delete/{orderId}")
//    public ResponseEntity<Map<String,String>> deleteOrder(@PathVariable("orderId")String orderId){
//        Map<String,String> msg = new HashMap<>();
//        try{
//            this.orderService.deleteOrder(orderId);
//            msg.put("status","200");
//            return ResponseEntity.ok(msg);
//        }catch (Exception e){
//
//            e.printStackTrace();
//        }
//        msg.put("status","500");
//        return ResponseEntity.ok(msg);
//    }

   @DeleteMapping("/delete/{userId}/{orderId}")
    public ResponseEntity<Map<String,String>> deleteOrderByUserIdAndOrderId(@PathVariable("userId")Long userId,
                                                                            @PathVariable("orderId")String orderId) {
       System.out.println(userId+"---->"+orderId);
       Map<String,String> msg = new HashMap<>();
       try{
           this.orderService.deleteOrderByUserIdAndOrderId(userId,orderId);
           msg.put("status","200");
           return ResponseEntity.ok(msg);
       }catch (Exception e){

           e.printStackTrace();
       }
       msg.put("status","500");
       return ResponseEntity.ok(msg);
    }




}
