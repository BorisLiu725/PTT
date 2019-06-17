package com.ppt.pptsso.bean;

public class RegistMsg {
    //类型 1-发邮件
    private Integer type;
    //用户是否注册成功
    private Boolean userStatus;
    //激活回调URL
    private String url;
    //userId
    private Long userId;
    // 发给谁
    private String toEmail;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Boolean userStatus) {
        this.userStatus = userStatus;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "RegistMsg{" +
                "type=" + type +
                ", userStatus=" + userStatus +
                ", url='" + url + '\'' +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }
}
