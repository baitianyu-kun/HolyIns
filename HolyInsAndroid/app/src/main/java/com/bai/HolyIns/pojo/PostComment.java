package com.bai.HolyIns.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
评论的帖子
 */

public class PostComment {
    private Comment comment;
    private Photo photo;
    private UserInfo userInfo;//评论的那个人的info
    private HashTag hashTag;

    @Override
    public String toString() {
        return "PostComment{" +
                "comment=" + comment +
                ", photo=" + photo +
                ", userInfo=" + userInfo +
                ", hashTag=" + hashTag +
                '}';
    }

    public PostComment(Comment comment, Photo photo, UserInfo userInfo, HashTag hashTag) {
        this.comment = comment;
        this.photo = photo;
        this.userInfo = userInfo;
        this.hashTag = hashTag;
    }

    public PostComment() {
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
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
