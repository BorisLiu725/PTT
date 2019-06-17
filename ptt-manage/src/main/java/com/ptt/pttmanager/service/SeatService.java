package com.ptt.pttmanager.service;

import com.ptt.pttmanager.bean.Seat;
import com.ptt.pttmanager.dao.SeatDao;
import com.ptt.pttmanager.utils.PageResult;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SeatService  {

    @Autowired
    private SeatDao seatDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(SeatService.class);


    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    /**
     * 批量保存座位
     * */
    @Transactional
    public void saveSeats(List<Seat> seats){
        seatDao.saveSeats(seats);
    }


    /**
     * 根据演出厅id查询座位
     * */
    public PageResult queryAllSeatsByStudioId(Long studioId){
        List<Seat> seats = this.seatDao.queryAllSeatsByStudioId(studioId);
        LOGGER.info("座位数量："+seats.size());
        PageResult pageResult = new PageResult();
        pageResult.setLists(seats);
        pageResult.setPage(1);
        pageResult.setRows(seats.size());
        return pageResult;
    }



    /**
     * 批量更新演出座位
     * */
    public void updateSeats(List<Integer> ids,Integer status){
        seatDao.updateSeats(ids,status);
    }




}
