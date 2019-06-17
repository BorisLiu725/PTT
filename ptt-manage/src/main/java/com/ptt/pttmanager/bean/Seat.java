package com.ptt.pttmanager.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 座位
 * */
@Table(name = "seat")
public class Seat {
    /**
     * 座位ID
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;
    /**
     * 座位所在演出厅id
     * */
    private Long studioId;
    /**
     * 座位所在行数
     * */
    private Integer row;
    /**
     * 座位所在列数
     * */
    private Integer column;
    /**
     * 座位的状态
     * */
    private Integer status;

    @Override
    public String toString() {
        return "Seat{" +
                "seatId=" + seatId +
                ", studioId=" + studioId +
                ", row=" + row +
                ", column=" + column +
                ", status=" + status +
                '}';
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public Long getStudioId() {
        return studioId;
    }

    public void setStudioId(Long studioId) {
        this.studioId = studioId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
