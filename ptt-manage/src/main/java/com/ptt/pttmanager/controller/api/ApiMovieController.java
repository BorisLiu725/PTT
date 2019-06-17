package com.ptt.pttmanager.controller.api;

import com.ptt.pttmanager.bean.Movie;
import com.ptt.pttmanager.service.MovieService;
import com.ptt.pttmanager.utils.PageResult;
import com.ptt.pttmanager.utils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/movie/api")
@Controller
public class ApiMovieController {

    @Autowired
    private MovieService movieService;


    /**
     * 正在热映
     * （已测）
     * */
    @GetMapping(value = "/hotting/list/{page}/{rows}")
    public ResponseEntity<PageResult> getMoviesByPageAndDate(@PathVariable(value = "page")Integer page,
                                                      @PathVariable(value = "rows")Integer rows){
        if (page<=0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        try{
            PageResult result = this.movieService.getMoviesByPageAndDate(page,rows);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 即将上映
     * （已测）
     * */
    @GetMapping(value = "/coming/list/{page}/{rows}")
    public ResponseEntity<PageResult> getMoviesByPageAndAfterDate(@PathVariable(value = "page")Integer page,
                                                             @PathVariable(value = "rows")Integer rows){
        if (page<=0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        try{
            PageResult result = this.movieService.getMoviesByPageAndAfterDate(page,rows);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 返回评分最高的电影
     * */
    @GetMapping(value = "/star/best/{page}/{rows}")
    @ResponseBody
    public PageResult queryMovieBySource(@PathVariable(value = "page")Integer page,
                                         @PathVariable(value = "rows")Integer rows){
        PageResult pageResult = this.movieService.queryMovieOrderBySource(page,rows);
        return pageResult;
    }

    /**
     * 按照销量排行
     * */

    @GetMapping(value = "/salesVolume/best/{page}/{rows}")
    @ResponseBody
    public PageResult queryMovieBySalesVolume(@PathVariable(value = "page")Integer page,
                                         @PathVariable(value = "rows")Integer rows){
        PageResult pageResult = this.movieService.queryMovieBySalesVolume(page,rows);
        return pageResult;
    }




}
