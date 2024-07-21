package com.bai.HolyIns.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


public class Photo {
    private int photo_id;
    private int user_id;
    private String photo_description;
    private String photo_path;
    private String photo_size;
    private Date date_created;

    @Override
    public String toString() {
        return "Photo{" +
                "photo_id=" + photo_id +
                ", user_id=" + user_id +
                ", photo_description='" + photo_description + '\'' +
                ", photo_path='" + photo_path + '\'' +
                ", photo_size='" + photo_size + '\'' +
                ", date_created=" + date_created +
                ", date_updated=" + date_updated +
                '}';
    }

    public Photo(int photo_id, int user_id, String photo_description, String photo_path, String photo_size, Date date_created, Date date_updated) {
        this.photo_id = photo_id;
        this.user_id = user_id;
        this.photo_description = photo_description;
        this.photo_path = photo_path;
        this.photo_size = photo_size;
        this.date_created = date_created;
        this.date_updated = date_updated;
    }

    public Photo() {
    }

    private Date date_updated;

    public int getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(int photo_id) {
        this.photo_id = photo_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPhoto_description() {
        return photo_description;
    }

    public void setPhoto_description(String photo_description) {
        this.photo_description = photo_description;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public String getPhoto_size() {
        return photo_size;
    }

    public void setPhoto_size(String photo_size) {
        this.photo_size = photo_size;
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