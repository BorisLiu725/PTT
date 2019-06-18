package com.ptt.pttmanager.controller.api;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.ptt.pttmanager.bean.Movie;
import com.ptt.pttmanager.service.impl.MovieServiceImpl;
import com.ptt.pttmanager.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/movie/api")
@Controller
public class ApiMovieController {

    @Autowired
    private MovieServiceImpl movieService;


    /**
     * 正在热映
     * （已测）
     * */
//    @GetMapping(value = "/hotting/list/{page}/{rows}")
//    public ResponseEntity<PageResult> getMoviesByPageAndDate(@PathVariable(value = "page")Integer page,
//                                                      @PathVariable(value = "rows")Integer rows){
//        if (page<=0){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//        try{
//            PageResult result = this.movieService.getMoviesByPageAndDate(page,rows);
//            return ResponseEntity.ok(result);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//    }

    @HystrixCommand(fallbackMethod = "processHystrixgetMovies") //一旦服务调用失败并抛出错误信息后，会自动调用@HystrixCommand标注好的fallBackMethod调用类中知道的方法
    @GetMapping(value = "/hotting/list/{page}/{rows}")
    @ResponseBody
    public PageResult getMoviesByPageAndDate(@PathVariable(value = "page")Integer page,
                                             @PathVariable(value = "rows")Integer rows){
        if (page<=0){
            return null;
        }
        try{
            PageResult result = this.movieService.getMoviesByPageAndDate(page,rows);
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public PageResult processHystrixgetMovies(@PathVariable(value = "page")Integer page,
                                              @PathVariable(value = "rows")Integer rows){
        PageResult pageResult = new PageResult();
        List<Movie> info = new ArrayList<>();
        Movie movie = new Movie();
        movie.setMovieDesc("目前没有查到电影哦...");
        movie.setMovieName("目前没有查到电影哦...");
        info.add(movie);
        pageResult.setLists(info);
        return pageResult;
    }

    /**
     * 即将上映
     * （已测）
     * */
//    @GetMapping(value = "/coming/list/{page}/{rows}")
//    public ResponseEntity<PageResult> getMoviesByPageAndAfterDate(@PathVariable(value = "page")Integer page,
//                                                             @PathVariable(value = "rows")Integer rows){
//        if (page<=0){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//        try{
//            PageResult result = this.movieService.getMoviesByPageAndAfterDate(page,rows);
//            return ResponseEntity.ok(result);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//    }

    @GetMapping(value = "/coming/list/{page}/{rows}")
    @HystrixCommand(fallbackMethod = "processHystrixgetMovies")
    @ResponseBody
    public PageResult getMoviesByPageAndAfterDate(@PathVariable(value = "page")Integer page,
                                                  @PathVariable(value = "rows")Integer rows){
        if (page<=0){
            return null;
        }
        try{
            PageResult result = this.movieService.getMoviesByPageAndAfterDate(page,rows);
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回评分最高的电影
     * */
    @GetMapping(value = "/star/best/{page}/{rows}")
    @HystrixCommand(fallbackMethod = "processHystrixgetMovies")
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
    @HystrixCommand(fallbackMethod = "processHystrixgetMovies")
    public PageResult queryMovieBySalesVolume(@PathVariable(value = "page")Integer page,
                                         @PathVariable(value = "rows")Integer rows){
        PageResult pageResult = this.movieService.queryMovieBySalesVolume(page,rows);
        return pageResult;
    }




}
