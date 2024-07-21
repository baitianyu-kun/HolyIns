package com.bai.HolyIns.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


public class Forwarding {
    public Forwarding() {
    }

    @Override
    public String toString() {
        return "Forwarding{" +
                "photo_id=" + photo_id +
                ", forward_id=" + forward_id +
                ", forward_user_id=" + forward_user_id +
                ", forward_text='" + forward_text + '\'' +
                ", forward_time=" + forward_time +
                '}';
    }

    public Forwarding(int photo_id, int forward_id, int forward_user_id, String forward_text, Date forward_time) {
        this.photo_id = photo_id;
        this.forward_id = forward_id;
        this.forward_user_id = forward_user_id;
        this.forward_text = forward_text;
        this.forward_time = forward_time;
    }

    private int photo_id;
    private int forward_id;
    private int forward_user_id;
    private String forward_text;
    private Date forward_time;

    public int getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(int photo_id) {
        this.photo_id = photo_id;
    }

    public int getForward_id() {
        return forward_id;
    }

    public void setForward_id(int forward_id) {
        this.forward_id = forward_id;
    }

    public int getForward_user_id() {
        return forward_user_id;
    }

    public void setForward_user_id(int forward_user_id) {
        this.forward_user_id = forward_user_id;
    }

    public String getForward_text() {
        return forward_text;
    }

    public void setForward_text(String forward_text) {
        this.forward_text = forward_text;
    }

    public Date getForward_time() {
        return forward_time;
    }

    public void setForward_time(Date forward_time) {
        this.forward_time = forward_time;
    }
}
