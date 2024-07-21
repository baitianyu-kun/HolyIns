package com.bai.pojo;

import java.util.Date;

public class Comment {
    private int photo_id;
    private int comment_user_id;
    private int comment_id;
    private String comment_text;
    private Date comment_time;

    public Comment() {
    }

    @Override
    public String toString() {
        return "Comment{" +
                "photo_id=" + photo_id +
                ", comment_user_id=" + comment_user_id +
                ", comment_id=" + comment_id +
                ", comment_text='" + comment_text + '\'' +
                ", comment_time=" + comment_time +
                '}';
    }

    public Comment(int photo_id, int comment_user_id, int comment_id, String comment_text, Date comment_time) {
        this.photo_id = photo_id;
        this.comment_user_id = comment_user_id;
        this.comment_id = comment_id;
        this.comment_text = comment_text;
        this.comment_time = comment_time;
    }

    public int getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(int photo_id) {
        this.photo_id = photo_id;
    }

    public int getComment_user_id() {
        return comment_user_id;
    }

    public void setComment_user_id(int comment_user_id) {
        this.comment_user_id = comment_user_id;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public Date getComment_time() {
        return comment_time;
    }

    public void setComment_time(Date comment_time) {
        this.comment_time = comment_time;
    }
}
