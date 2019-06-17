package com.ptt.pttmanager.service;


import com.ptt.pttmanager.bean.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 秒（0~59）
 *
 * 分钟（0~59）
 *
 * 小时（0~23）
 *
 * 天（月）（0~31，但是你需要考虑你月的天数）
 *
 * 月（0~11）
 *
 * 天（星期）（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT）
 *    @Scheduled(cron = "0 0 2 * * ?")　　//每天凌晨两点执行
 *
 * */
@Service
public class SchedulingService {

    @Autowired
    private MovieService movieService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulingService.class);
    /**
     * 统计销量
     * */
    @Scheduled(cron = "0 0 2 * * ?")
    public void SalesStatistics(){
        LOGGER.info("开始统计票的销量......");
        List<Movie> movies = movieService.queryAll();
        for (int i = 0; i < movies.size(); i++) {
            movieService.salesStatistics(movies.get(i).getId());
        }
        LOGGER.info("统计票的销量完毕......");
    }


}
