package com.ptt.pttmanager.controller;

import com.ptt.pttmanager.bean.MovieType;
import com.ptt.pttmanager.service.impl.MovieTypeService;
import com.ptt.pttmanager.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/movieType")
@Controller
public class MovieTypeController {

    @Autowired
    private MovieTypeService movieTypeService;
    /**
     * 返回所有的电影类型
     * */
    @GetMapping(value = "/list")
    public ResponseEntity<PageResult> getAllMoviesType(){
        try{

            MovieType record = new MovieType();
            record.setStatus(1);
            List<MovieType> lists = this.movieTypeService.queryOne(record);
            PageResult pageResult = new PageResult();
            pageResult.setLists(lists);
            pageResult.setPage(1);
            pageResult.setRows(lists.size());
            return ResponseEntity.ok(pageResult);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

}
