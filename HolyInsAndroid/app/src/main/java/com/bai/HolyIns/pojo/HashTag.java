package com.bai.HolyIns.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class HashTag {
    private int hashtag_id;
    private String hashtag_text;

    @Override
    public String toString() {
        return "HashTag{" +
                "hashtag_id=" + hashtag_id +
                ", hashtag_text='" + hashtag_text + '\'' +
                '}';
    }

    public HashTag(int hashtag_id, String hashtag_text) {
        this.hashtag_id = hashtag_id;
        this.hashtag_text = hashtag_text;
    }

    public HashTag() {
    }

    public int getHashtag_id() {
        return hashtag_id;
    }

    public void setHashtag_id(int hashtag_id) {
        this.hashtag_id = hashtag_id;
    }

    public String getHashtag_text() {
        return hashtag_text;
    }

    public void setHashtag_text(String hashtag_text) {
        this.hashtag_text = hashtag_text;
    }
}
