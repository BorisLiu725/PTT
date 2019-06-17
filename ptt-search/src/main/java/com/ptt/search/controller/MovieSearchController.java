package com.ptt.search.controller;

import com.ptt.search.bean.Movie;
import com.ptt.search.repository.MovieRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movieSearch")
public class MovieSearchController {

    //设置每页的数量
    @Value("${PAGE_SIZE}")
    public static Integer PAGE_SIZE;


    @Autowired
    private MovieRepository movieRepository;


    @PostMapping("/save")
    public String save(@RequestBody Movie movie){
        this.movieRepository.save(movie);
        return "success";
    }

    @DeleteMapping("/delete/{movieId}")
    public String delete(@PathVariable("movieId") Long movieId){
        this.movieRepository.delete(movieId);
        return "success";
    }

    @PostMapping("/update")
    public String update(@RequestBody Movie movie){
        this.movieRepository.save(movie);
        return "success";
    }
    
    @GetMapping("/query/{movieId}")
    public Movie queryOne(@PathVariable("movieId") Long movieId){
        Movie movie = this.movieRepository.findOne(movieId);
        return movie;
    }

    @GetMapping("/list/{curPage}/{keyWords}")
    public List<Movie> movieLists(@PathVariable("curPage") Integer curPage,
                                  @PathVariable("keyWords") String keyWords){
        if (curPage<=0) curPage = 0;

        SearchQuery searchQuery = this.getEntitySearchQuery(curPage,PAGE_SIZE,"下一任");
        Page<Movie> moviePage = this.movieRepository.search(searchQuery);
        return moviePage.getContent();

    }


    private SearchQuery getEntitySearchQuery(Integer curPage,Integer pageSize,String keyWords){

        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
                .add(QueryBuilders.matchPhraseQuery("movieName",keyWords),ScoreFunctionBuilders.weightFactorFunction(100))
                .add(QueryBuilders.matchPhraseQuery("movieDesc",keyWords),ScoreFunctionBuilders.weightFactorFunction(100))
                //求和模式
                .scoreMode("sum")
                //最低权重
                .setMinScore(10);
        //设置分页
        Pageable pageable = new PageRequest(curPage,pageSize);

        return new NativeSearchQueryBuilder().withPageable(pageable)
                .withQuery(functionScoreQueryBuilder).build();
    }


    

}
