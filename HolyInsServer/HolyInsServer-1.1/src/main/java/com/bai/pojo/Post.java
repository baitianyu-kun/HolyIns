package com.bai.pojo;

import java.util.List;
/*
例如  一对多的数据对象，本来是一的对象下面有多的list集合对象，但是在映射时会出现一的list集合，而这个集合是按照多的来一个一个的封装的，这样并不是我们想要的结果，
解决的方法：是在每个封装对象的关系映射中加id来辨别，这样数据就不会出现错误。
在看到resultMap的官方文档中：
id – 一个 ID 结果;标记结果作为 ID 可以帮助提高整体效能
或许是作为唯一的标记来规范，id为此resultMap的标识
 */
/*
发的帖子
 */

public class Post {
    private int photo_id;
    private HashTag hashTag;
    private Photo photo;
    private List<Comment> comments;
    private List<Forwarding> forwardings;
    private List<Likes> likes;

    @Override
    public String toString() {
        return "Post{" +
                "photo_id=" + photo_id +
                ", hashTag=" + hashTag +
                ", photo=" + photo +
                ", comments=" + comments +
                ", forwardings=" + forwardings +
                ", likes=" + likes +
                ", userInfo=" + userInfo +
                '}';
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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

    public Post(int photo_id, HashTag hashTag, Photo photo, List<Comment> comments, List<Forwarding> forwardings, List<Likes> likes, UserInfo userInfo) {
        this.photo_id = photo_id;
        this.hashTag = hashTag;
        this.photo = photo;
        this.comments = comments;
        this.forwardings = forwardings;
        this.likes = likes;
        this.userInfo = userInfo;
    }

    public Post() {
    }

    private UserInfo userInfo;//发帖子的人的info
}
