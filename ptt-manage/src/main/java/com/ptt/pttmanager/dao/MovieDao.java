package com.ptt.pttmanager.dao;

import com.ptt.pttmanager.bean.Movie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface  MovieDao {
   /**
    * 查找所有的电影
    * */
    List<Movie> getAll();

    /**
     * 添加一条电影信息
     * */
    void add(Movie movie);

    Movie getMovie(Long id);

    /**
     * 根据评分查找电影
     * */
    List<Movie> queryMovieOrderBySource();
 /**
  * 根据销量排行
  * */
    List<Movie> queryMovieBySalesVolume();
    /**
     *根据电影id统计销量
     * */
    Long salesStatistics(@Param("movieId") Long movieId);

    Integer updateSalesVolumeByMovieId(@Param("movieId")Long movieId, @Param("count")Long count);
}
