package com.ptt.pttmanager.utils;

public class FileUploadResult {
    /**
     * 图片的访问网址
     * */
    private String url;
    /**
     * 上传的状态
     * */
    private String status;
    /**
     * 图片是否合法
     * */
    private Boolean isImg = false;

    private Boolean isLegal = false;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getLegal() {
        return isLegal;
    }

    public void setLegal(Boolean legal) {
        isLegal = legal;
    }

    public Boolean getImg() {
        return isImg;
    }

    public void setImg(Boolean img) {
        isImg = img;
    }
}
