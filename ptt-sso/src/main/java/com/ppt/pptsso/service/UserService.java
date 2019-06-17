package com.ppt.pptsso.service;


//import com.fasterxml.jackson.databind.ObjectMapper;
import com.ppt.pptsso.bean.User;
import com.ppt.pptsso.mapper.UserMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class UserService  extends BaseService<User> {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RabbitMailService rabbitMailService;

    @Value("${ACTIVE_MAIL_URL}")
    public  String ACTIVE_MAIL_URL ;

    @Value("${PTT_CACHE_TOKEN}")
    public  String PTT_CACHE_TOKEN;


//    @Qualifier("userRedisCacheManager")
//    @Autowired
//    private RedisCacheManager userCacheManager;

    @Qualifier("userRedisTemplate")
    @Autowired
    RedisTemplate<String,User> redisTemplate;


    private static final Long CACHE_TIME = 60 * 30L;

//    private ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 检查用户名/邮箱/电话号码  是否重复
     * */
    public  Boolean check(String param, Integer type){
        User record = new User();
        switch (type){
            case 1:record.setUserName(param);break;
            case 2:record.setEmail(param);break;
            case 3:record.setPhoneNum(param);break;
            default:return null;
        }
        return this.userMapper.selectOne(record) == null;

    }


    /**
     * 注册需要发邮件
     * */
    public Boolean regist(User user) throws Exception {
        //保存User
        user.setUserId(null);
        user.setCreateDate(new Date());
        user.setUpdateDate(user.getCreateDate());
        //设置为未激活状态
        user.setStatus(1);

        //密码加密存储
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        // 生成验证码
        String code = DigestUtils.md5Hex(user.getUserName() + System.currentTimeMillis());
        code = code.substring(0,10);
        user.setCode(code);
        Integer res = -1;
        try {
             res = this.userMapper.insert(user);
        }catch (Exception e){
            throw  new Exception("用户信息插入失败，...请检查用户名|邮箱|手机号码可能已经被注册...");
        }

        String url = ACTIVE_MAIL_URL + "/" + user.getUserId() + "/" + code;
        //发邮件
        this.rabbitMailService.sendMsg(res==1,url,user.getEmail());

        return res == 1;
    }

    /**
     * 用户激活
     * */
    public Boolean active(Long id, String code) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user.getCode().equals(code) && user.getStatus()!=null && user.getStatus()!=2){
            user.setStatus(2);
            userMapper.updateByPrimaryKeySelective(user);
            return true;
        }
        return false;
    }
    /**
     * 用户登录
     * */
//    @Cacheable(value = "User",key = "'PTT_TOKEN_'+#user.userName")
    public String login(User user){
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        User existUser = this.userMapper.selectOne(user);
        // 没有查到该用户
        if (existUser == null){
            return null;
        }

        // 查到该用户了，将用户保存到redis中
        String token = DigestUtils.md5Hex(user.getUserName() + System.currentTimeMillis());

        try {
            this.redisTemplate.opsForValue().set(PTT_CACHE_TOKEN + "_" +token,existUser,CACHE_TIME,TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return token;

    }

    /**
     * 根据token查找用户
     * */
//    @CachePut(value = "User",key = "'PTT_TOKEN_'+#result.userName")
    public User queryByToken(String token) {
        String key = PTT_CACHE_TOKEN + "_" + token;
        System.out.println("key--->"+key);
        User existUser = this.redisTemplate.opsForValue().get(key);

        //  重新设置失效时期
        if (existUser == null){
            System.out.println("失效了》。。");
            return  null;
        }
        try {
            this.redisTemplate.opsForValue().set(PTT_CACHE_TOKEN + "_" +token,existUser,CACHE_TIME,TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return existUser;

    }

    /**
     * 通过用户id修改密码
     * */
    public Boolean updatePassWorld(Long userId, String oldPassword,String newPassword) {
        User record = new User();
        record.setUserId(userId);
        record.setPassword(DigestUtils.md5Hex(oldPassword));
        System.out.println(record+"***************"+oldPassword+"-->"+newPassword);
        User user = this.userMapper.selectOne(record);

        if (user == null || !user.getPassword().equals(DigestUtils.md5Hex(oldPassword))){
            return false;
        }
        user.setPassword(DigestUtils.md5Hex(newPassword));
        System.out.println(user+"----------------");
        return this.userMapper.updateByPrimaryKeySelective(user)==1;
    }


    public Boolean updateUser(User user,String token) {
        String key = PTT_CACHE_TOKEN + "_" + token;
        this.redisTemplate.delete(key);
        this.userMapper.updateByPrimaryKeySelective(user);
        User record = this.userMapper.selectByPrimaryKey(user.getUserId());
        try {
            this.redisTemplate.opsForValue().set(PTT_CACHE_TOKEN + "_" +token,record,CACHE_TIME,TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }



}
