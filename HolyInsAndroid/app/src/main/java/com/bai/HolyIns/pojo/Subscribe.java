package com.bai.HolyIns.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


public class Subscribe {
    private int now_user_id;
    private int subscribe_userid;
    private Date subscribe_time;

    @Override
    public String toString() {
        return "Subscribe{" +
                "now_user_id=" + now_user_id +
                ", subscribe_userid=" + subscribe_userid +
                ", subscribe_time=" + subscribe_time +
                '}';
    }

    public Subscribe(int now_user_id, int subscribe_userid, Date subscribe_time) {
        this.now_user_id = now_user_id;
        this.subscribe_userid = subscribe_userid;
        this.subscribe_time = subscribe_time;
    }

    public Subscribe() {
    }

    public int getNow_user_id() {
        return now_user_id;
    }

    public void setNow_user_id(int now_user_id) {
        this.now_user_id = now_user_id;
    }

    public int getSubscribe_userid() {
        return subscribe_userid;
    }

    public void setSubscribe_userid(int subscribe_userid) {
        this.subscribe_userid = subscribe_userid;
    }

    public Date getSubscribe_time() {
        return subscribe_time;
    }

    public void setSubscribe_time(Date subscribe_time) {
        this.subscribe_time = subscribe_time;
    }
}
