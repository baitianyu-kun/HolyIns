package com.bai.HolyIns.pojo;

public class UserFirstPagePost {
    private int photo_id;
    private HashTag hashTag;
    private Photo photo;
    private String first_comment;
    private UserInfo userInfo;
    private UserInfo first_comment_user_info;

    @Override
    public String toString() {
        return "UserFirstPagePost{" +
                "photo_id=" + photo_id +
                ", hashTag=" + hashTag +
                ", photo=" + photo +
                ", first_comment='" + first_comment + '\'' +
                ", userInfo=" + userInfo +
                ", first_comment_user_info=" + first_comment_user_info +
                '}';
    }

    public UserFirstPagePost(int photo_id, HashTag hashTag, Photo photo, String first_comment, UserInfo userInfo, UserInfo first_comment_user_info) {
        this.photo_id = photo_id;
        this.hashTag = hashTag;
        this.photo = photo;
        this.first_comment = first_comment;
        this.userInfo = userInfo;
        this.first_comment_user_info = first_comment_user_info;
    }

    public UserFirstPagePost() {
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

    public String getFirst_comment() {
        return first_comment;
    }

    public void setFirst_comment(String first_comment) {
        this.first_comment = first_comment;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfo getFirst_comment_user_info() {
        return first_comment_user_info;
    }

    public void setFirst_comment_user_info(UserInfo first_comment_user_info) {
        this.first_comment_user_info = first_comment_user_info;
    }
}
