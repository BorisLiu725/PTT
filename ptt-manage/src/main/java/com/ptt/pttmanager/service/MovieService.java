package com.ptt.pttmanager.service;

import com.ptt.pttmanager.bean.Movie;
import com.ptt.pttmanager.exception.MovieException;
import com.ptt.pttmanager.utils.PageResult;
/**
 * 电影
 * */
public interface MovieService {
    /**
     * 分页查询电影
     * @param page 当前页
     * @param rows  每页有几个
     * */
     PageResult getMoviesByPage(Integer page, Integer rows);
    /**
     * 添加电影
     * */
     Movie addMovie (Movie movie) throws MovieException;
    /**
     * 正在热映的电影
     * */
     PageResult getMoviesByPageAndDate(Integer page, Integer rows);
    /**
     *  即将上映的电影
     * */
     PageResult getMoviesByPageAndAfterDate(Integer page, Integer rows);

    /**
     * 查询电影，根据评分排序
     * */
     PageResult queryMovieOrderBySource(Integer page,Integer rows);

     /**
      * 根据销量串行电影（排序）
      * */
     PageResult queryMovieBySalesVolume(Integer page, Integer rows);

    /**
     * 统计电影的销量
     * */
     Long salesStatistics(Long movieId);


}
