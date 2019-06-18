package com.ptt.pttmanager.service.impl;

import com.ptt.pttmanager.bean.Seat;
import com.ptt.pttmanager.bean.Studio;
import com.ptt.pttmanager.dao.SeatDao;
import com.ptt.pttmanager.dao.StudioDao;
import com.ptt.pttmanager.mapper.StudioMapper;

import com.ptt.pttmanager.service.BaseService;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudioService extends BaseService<Studio> {


    private static final Logger LOGGER = LoggerFactory.getLogger(StudioService.class);

    @Autowired
    private StudioDao studioDao;

    @Autowired
    private StudioMapper studioMapper;

    @Autowired
    private SeatDao seatDao;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 保存演出厅，并添加座位
     * （已测）
     * TODO 此时应该异步生成座位
     * */
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
    public Long saveStudio(Studio record){
        LOGGER.info("插入演出厅..."+record);
        this.studioMapper.insert(record);
        LOGGER.info("插入演出厅完成..."+record);
        List<Seat> seats = new ArrayList<Seat>();
        for (int i = 1; i <= record.getRowsCount(); i++) {
            for (int j = 1; j <= record.getColsCount(); j++) {
                Seat seat = new Seat();
                seat.setRow(i);
                seat.setColumn(j);
                seat.setStudioId(record.getStudioId());
                seat.setStatus(1);
                seats.add(seat);
            }
        }
        LOGGER.info("开始保存座位"+seats);
        this.seatDao.saveSeats(seats);
        LOGGER.info("保存座位完成..");
        return record.getStudioId();
    }

    public Studio queryByIdWithAllSeats(Long studioId){
        return this.studioDao.queryById(studioId);
    }


    public List<Studio> queryAllWithAllSeats() {
        return this.studioDao.queryAllWithAllSeats();
    }
}
