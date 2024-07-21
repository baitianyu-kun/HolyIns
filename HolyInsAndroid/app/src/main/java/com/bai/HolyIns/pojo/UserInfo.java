package com.bai.HolyIns.pojo;


import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



public class UserInfo implements Serializable {
    private int user_id;
    private String name;
    private String account;
    private String e_mail;
    private String headPicturePath;
    private String headPictureSize;
    private String sex;
    private Date registerTime;

    @Override
    public String toString() {
        return "UserInfo{" +
                "user_id=" + user_id +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", e_mail='" + e_mail + '\'' +
                ", headPicturePath='" + headPicturePath + '\'' +
                ", headPictureSize='" + headPictureSize + '\'' +
                ", sex='" + sex + '\'' +
                ", registerTime=" + registerTime +
                '}';
    }

    public UserInfo(int user_id, String name, String account, String e_mail, String headPicturePath, String headPictureSize, String sex, Date registerTime) {
        this.user_id = user_id;
        this.name = name;
        this.account = account;
        this.e_mail = e_mail;
        this.headPicturePath = headPicturePath;
        this.headPictureSize = headPictureSize;
        this.sex = sex;
        this.registerTime = registerTime;
    }

    public UserInfo() {
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getHeadPicturePath() {
        return headPicturePath;
    }

    public void setHeadPicturePath(String headPicturePath) {
        this.headPicturePath = headPicturePath;
    }

    public String getHeadPictureSize() {
        return headPictureSize;
    }

    public void setHeadPictureSize(String headPictureSize) {
        this.headPictureSize = headPictureSize;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }
}
