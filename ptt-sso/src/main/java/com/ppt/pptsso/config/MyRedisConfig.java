package com.ppt.pptsso.config;


import com.ppt.pptsso.bean.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class MyRedisConfig {

    @Bean
    public RedisTemplate<String,User> userRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,User>  template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        //传入一个序列化器
//        Jackson2JsonRedisSerializer<User> serializer = new Jackson2JsonRedisSerializer<User>(User.class);
//        template.setDefaultSerializer(serializer);

        return template;
    }

    /**
     * 定制缓存规则
     * */
//    @Bean
//    public RedisCacheManager userRedisCacheManager(RedisTemplate<Object,User> userRedisTemplate){
//        RedisCacheManager redisCacheManager = new RedisCacheManager(userRedisTemplate);
//        //使用前缀
////        redisCacheManager.setUsePrefix(true);
////        Map<String,Long> expires = new HashMap<>();
////        expires.put("30m",30*60L);
////        redisCacheManager.setExpires(expires);
//        redisCacheManager.setDefaultExpiration(30*60L); //默认30分钟
//        return redisCacheManager;
//    }



}
