package com.ptt.pttmanager.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ptt.pttmanager.bean.Movie;
import com.ptt.pttmanager.dao.MovieDao;
import com.ptt.pttmanager.mapper.MovieMapper;
import com.ptt.pttmanager.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MovieService extends BaseService<Movie> {
    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private MovieDao movieDao;

    /**
     * 分页查询电影
     * @param page 当前页
     * @param rows  每页有几个
     * */
    public PageResult getMoviesByPage(Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
//        List<Movie> movies = this.movieDao.getAll();
        Example example = new Example(Movie.class);
        example.setOrderByClause("start_time DESC");
        example.createCriteria().andEqualTo("status",1);
        List<Movie> movies = this.movieMapper.selectByExample(example);
        PageInfo<Movie> pageInfo = new PageInfo<>(movies);
        return new PageResult(page, (int) pageInfo.getTotal(),pageInfo.getList());
    }
    /**
     * 添加电影
     * */
    public Movie addMovie(Movie movie){
        movie.setId(null);
//        movie.setStartTime(new Date());
        movie.setSalesVolume(0L);
        movie.setStatus(1);
        Integer flage = this.save(movie);

        return movie;
    }
    /**
     * 正在热映的电影
     * */
    public PageResult getMoviesByPageAndDate(Integer page, Integer rows){
        PageHelper.startPage(page,rows);
        Example example = new Example(Movie.class);
        //获取明天0:0:0的日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        Date date = new Date(calendar.getTimeInMillis());
        example.createCriteria().andLessThan("startTime",date).andEqualTo("status",1);
        List<Movie> movies = this.movieMapper.selectByExample(example);
        PageInfo<Movie> pageInfo = new PageInfo<>(movies);
        return new PageResult(page,(int) pageInfo.getTotal(),pageInfo.getList());
    }



    /**
     *  即将上映的电影
     * */
    public PageResult getMoviesByPageAndAfterDate(Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        Example example = new Example(Movie.class);
        //获取明天0:0:0的日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        Date date = new Date(calendar.getTimeInMillis());
        example.createCriteria().andGreaterThanOrEqualTo("startTime",date).andEqualTo("status",1);
        List<Movie> movies = this.movieMapper.selectByExample(example);
        PageInfo<Movie> pageInfo = new PageInfo<>(movies);
        return new PageResult(page,(int) pageInfo.getTotal(),pageInfo.getList());
    }

    /**
     * 查询电影，根据评分排序
     * */
    public PageResult queryMovieOrderBySource(Integer page,Integer rows) {
        PageHelper.startPage(page,rows);
        List<Movie> movies = movieDao.queryMovieOrderBySource();
        PageInfo<Movie> pageInfo = new PageInfo<>(movies);
        return new PageResult(page,(int) pageInfo.getTotal(),pageInfo.getList());
    }


    public PageResult queryMovieBySalesVolume(Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        List<Movie> movies = movieDao.queryMovieBySalesVolume();
        PageInfo<Movie> pageInfo = new PageInfo<>(movies);
        return new PageResult(page,(int) pageInfo.getTotal(),pageInfo.getList());
    }

    /**
     * 统计电影的销量
     * */
    public Long salesStatistics(Long movieId){
        Long count = this.movieDao.salesStatistics(movieId);
        Integer num =  this.movieDao.updateSalesVolumeByMovieId(movieId,count);
        return count;
    }



}
