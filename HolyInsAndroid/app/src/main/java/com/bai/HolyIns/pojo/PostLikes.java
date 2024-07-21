package com.bai.HolyIns.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
喜欢的帖子
 */

public class PostLikes {
    private Likes likes;
    private Photo photo;
    private UserInfo userInfo;//喜欢的那个人的info
    private HashTag hashTag;

    @Override
    public String toString() {
        return "PostLikes{" +
                "likes=" + likes +
                ", photo=" + photo +
                ", userInfo=" + userInfo +
                ", hashTag=" + hashTag +
                '}';
    }

    public Likes getLikes() {
        return likes;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
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

    public PostLikes(Likes likes, Photo photo, UserInfo userInfo, HashTag hashTag) {
        this.likes = likes;
        this.photo = photo;
        this.userInfo = userInfo;
        this.hashTag = hashTag;
    }

    public PostLikes() {
    }
}
