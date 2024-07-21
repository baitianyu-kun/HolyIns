package com.bai.HolyIns.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
转发的帖子
 */

public class PostForward {
    private Forwarding forwarding;
    private Photo photo;
    private UserInfo userInfo;
    private HashTag hashTag;

    @Override
    public String toString() {
        return "PostForward{" +
                "forwarding=" + forwarding +
                ", photo=" + photo +
                ", userInfo=" + userInfo +
                ", hashTag=" + hashTag +
                '}';
    }

    public PostForward(Forwarding forwarding, Photo photo, UserInfo userInfo, HashTag hashTag) {
        this.forwarding = forwarding;
        this.photo = photo;
        this.userInfo = userInfo;
        this.hashTag = hashTag;
    }

    public PostForward() {
    }

    public Forwarding getForwarding() {
        return forwarding;
    }

    public void setForwarding(Forwarding forwarding) {
        this.forwarding = forwarding;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public HashTag getHashTag() {
        return hashTag;
    }

    public void setHashTag(HashTag hashTag) {
        this.hashTag = hashTag;
    }
}
