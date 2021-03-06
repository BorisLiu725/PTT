package com.ptt.search.bean;

import io.searchbox.annotations.JestId;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * 电影
 * */
@Document(indexName = "ptt",type = "movie")
public class Movie implements Serializable {
    //电影ID
    @JestId
    private Long id;
    //电影名字
    private String movieName;
    //导演
    private String movieDirect;
    //主演
    private String moviePerformer;
    //类型
    private Integer movieType;
    //制片国家/地区
    private String movieArea;
    //片长
    private Integer movieTime;
    //剧情介绍
    private String movieDesc;
    //电影图片
    private String movieImages;
    //上架时间
    private Date startTime;
    //状态
    private Integer status;
    //电影评分
    private Double star;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Long getMovieId() {
//        return movieId;
//    }
//
//    public void setMovieId(Long movieId) {
//        this.movieId = movieId;
//    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieDirect() {
        return movieDirect;
    }

    public void setMovieDirect(String movieDirect) {
        this.movieDirect = movieDirect;
    }

    public String getMoviePerformer() {
        return moviePerformer;
    }

    public void setMoviePerformer(String moviePerformer) {
        this.moviePerformer = moviePerformer;
    }


    public String getMovieArea() {
        return movieArea;
    }

    public void setMovieArea(String movieArea) {
        this.movieArea = movieArea;
    }

    public Integer getMovieTime() {
        return movieTime;
    }

    public void setMovieTime(Integer movieTime) {
        this.movieTime = movieTime;
    }

    public String getMovieDesc() {
        return movieDesc;
    }

    public void setMovieDesc(String movieDesc) {
        this.movieDesc = movieDesc;
    }

    public String getMovieImages() {
        return movieImages;
    }

    public void setMovieImages(String movieImages) {
        this.movieImages = movieImages;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Double getStar() {
        return star;
    }

    public void setStar(Double star) {
        this.star = star;
    }

    public Integer getMovieType() {
        return movieType;
    }

    public void setMovieType(Integer movieType) {
        this.movieType = movieType;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + id +
                ", movieName='" + movieName + '\'' +
                ", movieDirect='" + movieDirect + '\'' +
                ", moviePerformer='" + moviePerformer + '\'' +
                ", movieType=" + movieType +
                ", movieArea='" + movieArea + '\'' +
                ", movieTime=" + movieTime +
                ", movieDesc='" + movieDesc + '\'' +
                ", movieImages='" + movieImages + '\'' +
                ", startTime=" + startTime +
                ", status=" + status +
                ", star=" + star +
                '}';
    }
}
