package com.ptt.search.repository;

import com.ptt.search.bean.Movie;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MovieRepository extends ElasticsearchRepository<Movie,Long> {

    List<Movie> findMovieByMovieNameLike(String movieName);

}
