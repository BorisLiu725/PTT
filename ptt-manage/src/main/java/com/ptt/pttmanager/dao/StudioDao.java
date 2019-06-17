package com.ptt.pttmanager.dao;


import com.ptt.pttmanager.bean.Studio;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudioDao {

    //级联查出座位
    Studio queryById(Long studioId);

    List<Studio> queryAllWithAllSeats();

    Studio queryByStudioId(Long studioId);
}
