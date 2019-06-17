package com.ptt.pttmanager.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 演出安排
 * */
@Table(name = "schedule")
public class Schedule implements Serializable {
    /**
     * 演出安排id
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;
    /**
     *  电影id
     */
    private Long movieId;
    /**
     *  放映厅id
     * */
    private Long studioId;

    /**
     * 放映厅上座数
     * */
    private Long seatNum;

    /**
     *
     *  影片现价
     */
    private Long newPrice;
    /**
     * 影片原价
     * */
    private Long oldPrice;

    /**
     * 放映时间
     * */
    private Date startTime;
    /**
     * 结束时间
     * */
    private Date endTime;
    /**
     * 演出计划里有票的信息
     * */
    private List<Ticket> tickets;
    private Movie movie;
    private Studio studio;


    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getStudioId() {
        return studioId;
    }

    public void setStudioId(Long studioId) {
        this.studioId = studioId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

//    public String getLanguage() {
//        return language;
//    }
//
//    public void setLanguage(String language) {
//        this.language = language;
//    }

    public Long getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(Long seatNum) {
        this.seatNum = seatNum;
    }

    public Long getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(Long newPrice) {
        this.newPrice = newPrice;
    }

    public Long getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Long oldPrice) {
        this.oldPrice = oldPrice;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleId=" + scheduleId +
                ", movieId=" + movieId +
                ", studioId=" + studioId +
                ", seatNum=" + seatNum +
                ", newPrice=" + newPrice +
                ", oldPrice=" + oldPrice +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }
}
