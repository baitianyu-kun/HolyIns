package com.bai.HolyIns.pojo;

import java.util.List;

public class UserPost {
    private int photo_id;
    private HashTag hashTag;
    private Photo photo;
    private List<Integer> comment_id;
    private List<Forwarding> forwardings;
    private List<Likes> likes;
    private UserInfo userInfo;

    public UserPost(int photo_id, HashTag hashTag, Photo photo, List<Integer> comment_id, List<Forwarding> forwardings, List<Likes> likes, UserInfo userInfo) {
        this.photo_id = photo_id;
        this.hashTag = hashTag;
        this.photo = photo;
        this.comment_id = comment_id;
        this.forwardings = forwardings;
        this.likes = likes;
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "UserPost{" +
                "photo_id=" + photo_id +
                ", hashTag=" + hashTag +
                ", photo=" + photo +
                ", comment_id=" + comment_id +
                ", forwardings=" + forwardings +
                ", likes=" + likes +
                ", userInfo=" + userInfo +
                '}';
    }

    public UserPost() {
    }

    public int getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(int photo_id) {
        this.photo_id = photo_id;
    }

    public HashTag getHashTag() {
        return hashTag;
    }

    public void setHashTag(HashTag hashTag) {
        this.hashTag = hashTag;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public List<Integer> getComment_id() {
        return comment_id;
    }

    public void setComment_id(List<Integer> comment_id) {
        this.comment_id = comment_id;
    }

    public List<Forwarding> getForwardings() {
        return forwardings;
    }

    public void setForwardings(List<Forwarding> forwardings) {
        this.forwardings = forwardings;
    }

    public List<Likes> getLikes() {
        return likes;
    }

    public void setLikes(List<Likes> likes) {
        this.likes = likes;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
