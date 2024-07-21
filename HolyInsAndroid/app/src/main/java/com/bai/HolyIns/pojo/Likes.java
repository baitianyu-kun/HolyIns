package com.bai.HolyIns.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


public class Likes {
    private int photo_id;
    private int like_user_id;
    private int like_id;
    private Date date_created;
    private Date date_updated;

    @Override
    public String toString() {
        return "Likes{" +
                "photo_id=" + photo_id +
                ", like_user_id=" + like_user_id +
                ", like_id=" + like_id +
                ", date_created=" + date_created +
                ", date_updated=" + date_updated +
                '}';
    }

    public Likes(int photo_id, int like_user_id, int like_id, Date date_created, Date date_updated) {
        this.photo_id = photo_id;
        this.like_user_id = like_user_id;
        this.like_id = like_id;
        this.date_created = date_created;
        this.date_updated = date_updated;
    }

    public Likes() {
    }

    public int getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(int photo_id) {
        this.photo_id = photo_id;
    }

    public int getLike_user_id() {
        return like_user_id;
    }

    public void setLike_user_id(int like_user_id) {
        this.like_user_id = like_user_id;
    }

    public int getLike_id() {
        return like_id;
    }

    public void setLike_id(int like_id) {
        this.like_id = like_id;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public Date getDate_updated() {
        return date_updated;
    }

    public void setDate_updated(Date date_updated) {
        this.date_updated = date_updated;
    }
}
