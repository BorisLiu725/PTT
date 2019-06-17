package com.ptt.pttmanager.controller;

import com.ptt.pttmanager.bean.Movie;
import com.ptt.pttmanager.service.MovieService;
import com.ptt.pttmanager.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;









    /**
     * 分页查询电影
     * （已测）
     * */
    @GetMapping(value = "/query/list/{page}/{rows}")
    public ResponseEntity<PageResult> getMoviesByPage(@PathVariable(value = "page")Integer page,
                                                @PathVariable(value = "rows")Integer rows){
        if (page<=0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        try{
            PageResult result = this.movieService.getMoviesByPage(page,rows);
            return ResponseEntity.ok(result);
        }catch (Exception e){

        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 添加电影
     * （已测）
     * * */
    @PostMapping("/add")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie){
        try{
            Movie record = this.movieService.addMovie(movie);
            return ResponseEntity.ok(record);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    /**
     * 修改电影前(先根据id查找电影)
     * （已测）
     * */
    @GetMapping(value = "/query/{id}")
    public ResponseEntity<Movie> updateMoviePre(@PathVariable("id")Long id){
        try{
            Movie movie = this.movieService.queryById(id);
            return ResponseEntity.ok(movie);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 修改电影（不为空的字段更新）
     * （已测）
     * */
    @PostMapping(value = "/update")
    public ResponseEntity<Void> updateMovie(@RequestBody Movie movie){
        try{
            this.movieService.updateSelective(movie);
            return ResponseEntity.ok(null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 删除电影
     * （已测）
     * */
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable("id")Long id){
        try{
            this.movieService.deleteById(id);
            return ResponseEntity.ok(null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }




}
