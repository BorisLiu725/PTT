package com.ptt.pttmanager.bean;

import java.util.Date;

public class ResultTicket {

    //影院名称
    private String logalName;
    //演出厅名称
    private String stdioName;
    //电影名称
    private String movieName;
    //电影图片
    private String movieImg;
    //语言版本
    private String language;
    //座位行号
    private Integer row;
    //座位列号
    private Integer column;
    //票价
    private Long price;
    //票的二维码
    private String qrCode;
    //影片开始时间
    private Date startTime;


    public String getLogalName() {
        return logalName;
    }

    public void setLogalName(String logalName) {
        this.logalName = logalName;
    }

    public String getStdioName() {
        return stdioName;
    }

    public void setStdioName(String stdioName) {
        this.stdioName = stdioName;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieImg() {
        return movieImg;
    }

    public void setMovieImg(String movieImg) {
        this.movieImg = movieImg;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
