package com.ptt.pttmanager.controller;

import com.ptt.pttmanager.service.PicService;
import com.ptt.pttmanager.utils.FileUploadResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/pic")
public class PicUploadController {

    @Autowired
    private PicService picService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PicUploadController.class);

    /**
     * 图片上传
     * */
    @PostMapping("/upload")
    @ResponseBody
    public FileUploadResult upload(@RequestParam("uploadFile")MultipartFile uploadFile) throws IOException {
        LOGGER.info("开始上传文件...");
        //保存上传的状态
        FileUploadResult result = new FileUploadResult();
        boolean isLegal = false;
        for (String type: PicService.IMAGE_TYPE) {
            //检查上传的类型是否合法
            if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(),type)){
                isLegal = true;
                result.setLegal(true);
            }
        }
        if (!isLegal){
            result.setLegal(false);
            result.setStatus("亲，上传图片不合法哦！");
            return result;
        }

        //文件保存的路径
        String filePath = picService.getPath(uploadFile.getOriginalFilename());

        //保存图片的路径
        String picUrl = picService.PTT_MOVIE_URL_PRIFEX +  StringUtils.replace(StringUtils.substringAfter(filePath,picService.PTT_MOVIE_IMAGES_PATH),"\\","/");


        File targetFile = new File(filePath);
        uploadFile.transferTo(targetFile);


        isLegal = false;
        //判断上传的是不是图片
        isLegal = picService.isImg(targetFile);
        if (!isLegal){
            result.setImg(false);
            result.setStatus("亲，需要上传图片才行哦！");
            //不合法的文件，直接删除
            targetFile.delete();
        }else{
            result.setStatus("ok");
            result.setImg(true);
            result.setUrl(picUrl);
        }

        return result;
    }




}

