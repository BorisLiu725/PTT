package com.ptt.pttmanager.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * 演出厅
 * */
@Table(name = "studio")
public class Studio {
    /**
     * 演出厅id
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studioId;
    /**
     * 演出厅名字
     * */
    private String studioName;

    /**
     * 演出厅的类型
     * */
    private Integer studioType;

    /**
     * 座位总行数
     * */
    private Integer rowsCount;
    /**
     * 座位总列数
     * */
    private Integer colsCount;

    /**
     * 座位
     * */
    private List<Seat> seats;

    @Override
    public String toString() {
        return "Studio{" +
                "studioId=" + studioId +
                ", studioName='" + studioName + '\'' +
                ", studioType=" + studioType +
                ", rowsCount=" + rowsCount +
                ", colsCount=" + colsCount +
                ", seats=" + seats +
                '}';
    }

    public Long getStudioId() {
        return studioId;
    }

    public void setStudioId(Long studioId) {
        this.studioId = studioId;
    }

    public String getStudioName() {
        return studioName;
    }

    public void setStudioName(String studioName) {
        this.studioName = studioName;
    }

    public Integer getRowsCount() {
        return rowsCount;
    }

    public void setRowsCount(Integer rowsCount) {
        this.rowsCount = rowsCount;
    }

    public Integer getColsCount() {
        return colsCount;
    }

    public void setColsCount(Integer colsCount) {
        this.colsCount = colsCount;
    }


    public Integer getStudioType() {
        return studioType;
    }

    public void setStudioType(Integer studioType) {
        this.studioType = studioType;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
