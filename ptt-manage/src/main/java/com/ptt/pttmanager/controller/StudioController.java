package com.ptt.pttmanager.controller;

import com.ptt.pttmanager.bean.Studio;
import com.ptt.pttmanager.service.StudioService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/studio")
public class StudioController {

    @Autowired
    private StudioService studioService;

    /**
     * 添加演出厅
     * */
    @PostMapping(value = "/add")
    public ResponseEntity<Map<String,Long>> addStudio(@RequestBody Studio studio){
        Map<String,Long> map = new HashMap<>();
        try{
            Long studioId = studioService.saveStudio(studio);

            map.put("studioId",studioId);

            return ResponseEntity.ok(map);
        }catch (Exception e){
            e.printStackTrace();
        }
        map.put("studioId",-1L);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
    }

    /**
     * 删除演出厅
     * */
    @DeleteMapping(value = "/delete/{studioId}")
    public ResponseEntity<Void> deleteStudio(@PathVariable("studioId") Long studioId){
        try{
            studioService.deleteById(studioId);
            return ResponseEntity.ok(null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 更新演出厅
     * */
    @PostMapping(value = "/update")
    public ResponseEntity<Void> updateStudio(@RequestBody Studio studio){
        try{
            System.out.println(studio+"*****************************");
            studioService.update(studio);
            return ResponseEntity.ok(null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 根据演出厅id查询演出厅
     * */
    @GetMapping(value = "/query/{studioId}")
    public ResponseEntity<Studio> queryStudio(@PathVariable("studioId") Long studioId){
        try{
            Studio studio = studioService.queryByIdWithAllSeats(studioId);
            return ResponseEntity.ok(studio);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @GetMapping(value = "/query/list")
    public ResponseEntity<List<Studio>> queryAllWithAllSeats(){
        try{
            List<Studio> lists  = studioService.queryAllWithAllSeats();

            return ResponseEntity.ok(lists);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


}
