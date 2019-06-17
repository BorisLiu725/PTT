package com.ptt.pttmanager.service;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

@Service
public class PicService {

    @Value(value = "${PTT_MOVIE_IMAGES_PATH}")
    public String PTT_MOVIE_IMAGES_PATH;

    @Value(value = "${PTT_MOVIE_URL_PRIFEX}")
    public String PTT_MOVIE_URL_PRIFEX;

    /**
     * 允许上传的图片类型
     * */
    public static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg", ".jpeg", ".gif", ".png" };


    /**
     * 判断上传的是不是图片
     * */
    public boolean isImg(File file){
        if (file==null){
            System.out.println("文件为null");
            return false;
        }
        boolean islegal =  false;
        try {
            BufferedImage image = ImageIO.read(file);
            if (image!=null){
                image.getWidth();
                islegal = true;
            }
            return islegal;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getPath(String uploadFileName){
        //基本路径
        String baseFolder = this.PTT_MOVIE_IMAGES_PATH + File.separator + "movieImages";
        Date nowDate = new Date();
        //yyyy//MM//dd
        //目录打散
        String fileFolder = baseFolder + File.separator + new DateTime(nowDate).toString("yyyy") + File.separator + new DateTime(nowDate).toString("MM")
                + File.separator + new DateTime(nowDate).toString("dd");
        File file = new File(fileFolder);
        if (!file.isDirectory()){
            file.mkdirs();
        }
        String fileName = new DateTime(nowDate).toString("yyyyMMddhhmmssSSSS")+RandomUtils.nextInt(100,9999) + "." + StringUtils.substringAfter(uploadFileName,".");

        return fileFolder + File.separator + fileName;
    }


}
