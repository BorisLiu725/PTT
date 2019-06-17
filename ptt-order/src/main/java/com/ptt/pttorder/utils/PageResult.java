package com.ptt.pttorder.utils;


import java.util.List;

public class PageResult {
    // 当前页
    private Integer page;
    // 每页有几个
    private Integer rows;
    //分页的数据
    private List<?> lists;

    public PageResult() {
    }

    public PageResult(Integer page, Integer rows, List<?> lists) {
        this.page = page;
        this.rows = rows;
        this.lists = lists;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public List<?> getLists() {
        return lists;
    }

    public void setLists(List<?> lists) {
        this.lists = lists;
    }
}
