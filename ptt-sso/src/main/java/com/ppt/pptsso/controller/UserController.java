package com.ppt.pptsso.controller;

import com.ppt.pptsso.bean.User;
import com.ppt.pptsso.service.PropertiesService;
import com.ppt.pptsso.service.UserService;
import com.ppt.pptsso.utils.CookieUtils;
import com.ppt.pptsso.utils.ResultMessage;
import com.ppt.pptsso.utils.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("user")
@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private static final String PREFIX = "user/";


    @Autowired
    private UserService userService;


    @GetMapping("/toRegist")
    public String toRegist(){
        return PREFIX+"register";
    }

    @GetMapping("/toLogin")
    public String toLogin(){
        return PREFIX+"login";
    }


    /**
     * 跟据类型检查参数是否被占用
     * 这些需要前端在注册用户之前调用
     * 1-用户名
     * 2-email
     * 3-phoneNum
     *
     * */
    @GetMapping("/check/{param}/{type}")
    public ResponseEntity<Boolean> check(@PathVariable(value = "param")String param,
                                         @PathVariable(value = "type")Integer type ){
       try{
           LOGGER.info("检查参数/check/[{}]/[{}]",param,type);
           Boolean flag = this.userService.check(param,type);
           //参数错误
           if (flag == null){
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
           }
           LOGGER.info("/check/[{}]/[{}]完成...",param,type);
           return ResponseEntity.ok(flag);
       }catch (Exception e){
            e.printStackTrace();
       }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 注册
     * */
    @PostMapping("/regist")
    @ResponseBody
    public Map<String,String> regist(@RequestBody  User user){

        LOGGER.info("/regist开始检验注册用户的信息..."+user.toString());
        Map<String,String> msg = new HashMap<>();
        try{
//            Boolean flag = true;
            Boolean flag = this.userService.regist(user);
            if (flag){
                LOGGER.info("检验完，无错误...");
                msg.put("status","200");
                msg.put("msg","ok");
//                model.addAttribute("msg","ok");
//                return "redirect:/"+PREFIX+"toLogin";
            }
            return msg;
        }catch (Exception e){
           LOGGER.error(e.getMessage());
            msg.put("status","500");
            msg.put("msg",e.getMessage());
//            return "redirect:/"+PREFIX+"error";
        }
        LOGGER.info("服务器端错误...");
//        return "redirect:/"+PREFIX+"error";
        return msg;
    }

    /**
     * 邮箱验证回调方法
     * */
    @GetMapping(value = "/active/{id}/{code}")
    public ResponseEntity<String> RegistMileCallBack(@PathVariable(value = "id")Long id ,
                                   @PathVariable(value = "code")String code){
        System.out.println(code+"**********************");
        LOGGER.info(id+"接受到传来的验证码了!..."+code);
        try{
            Boolean flag =  userService.active(id,code);
            if (flag){
                LOGGER.info("用户激活邮箱成功...");
                return ResponseEntity.ok("激活成功");
            }
            if(!flag){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("激活失败！无效的激活码..");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("系统错误，激活失败！");
    }


    /**
     * 登录
     * */
    @PostMapping(value = "/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody User user, HttpServletRequest request,
                                         HttpServletResponse response){
        Map<String,String> map = new HashMap<>();
        try {
            LOGGER.info("/login登录开始..."+user.toString());
            String token = this.userService.login(user);
            LOGGER.info("查询出来的token为"+token);
            if (token == null){
                //map.put(this.userService.PTT_CACHE_TOKEN,"400");
                map.put("status","1");
//                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
            }else{
                //登录成功，将登录信息写到cookie中
                map.put(this.userService.PTT_CACHE_TOKEN,token);
                map.put("status","0");
//                CookieUtils.setCookie(request,response,this.userService.PTT_CACHE_TOKEN,token);
                LOGGER.info("将登录信息写到cookie成功.."+this.userService.PTT_CACHE_TOKEN+""+token);

                return  ResponseEntity.ok(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("/login服务器出现错误..",e.getMessage());
           // map.put(this.userService.PTT_CACHE_TOKEN,"500");
            map.put("status","1");
        }

        return ResponseEntity.ok(map);
    }


    /**
     * 跟据TOKEN 查询用户
     * */
    @GetMapping("/token/{token}")
    @ResponseBody
    public ResponseEntity<User> queryByToken(@PathVariable("token")String token){
        LOGGER.info("根据token查询用户:"+token);
        try{
            User user = this.userService.queryByToken(token);
            if (user == null){
                LOGGER.info("查出来的用户为null");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            LOGGER.info("查出来的用户为:"+user.toString());
            return ResponseEntity.ok(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


    @PostMapping("/update/pwd")
    @ResponseBody
    public ResultMessage updatePassWorld(@RequestBody UserVo userVo){
        Boolean bool = this.userService.updatePassWorld(userVo.getUserId(),userVo.getOldPassword(),userVo.getNewPassword());
        ResultMessage resultMessage = new ResultMessage();
        if (bool){
            resultMessage.setCode("200");
            resultMessage.setMsg("ok");
        }else {
            resultMessage.setCode("400");
            resultMessage.setMsg("原密码错误!");
        }
       return resultMessage;
    }

    @PostMapping("/update")
    @ResponseBody
    public Map<String,Object> updateUser(@RequestBody User user){
        Map<String,Object> map = new HashMap<>();
        Integer count = 0;
        try{
            System.out.println("更新user..."+user+"-->");
             count = this.userService.updateSelective(user);
            User record = this.userService.queryById(user.getUserId());
            map.put("status",count>0 ? "200" : "400");
            map.put("user",record);
            return map;
        }catch (Exception e){
            e.printStackTrace();
        }
        map.put("status",count ==0 ? "400" : "500");
        map.put("user",user);
        return map;
    }


    @GetMapping("/list")
    @ResponseBody
    public List<User> queryAllUsers(){
        return this.userService.queryAll();
    }

    @PostMapping("/update/{token}")
    @ResponseBody
    public Map<String,Object> updateUser(@RequestBody User user,@PathVariable("token")String token){
        Map<String,Object> map = new HashMap<>();
//        Integer count = 0;
        try{
            System.out.println("更新user..."+user+"-->");
//            count = this.userService.updateSelective(user);
            Boolean bool = this.userService.updateUser(user, token);
            map.put("status",bool? "200" : "400");
            return map;
        }catch (Exception e){
            e.printStackTrace();
        }
        map.put("status", "500");

        return map;
    }



}
