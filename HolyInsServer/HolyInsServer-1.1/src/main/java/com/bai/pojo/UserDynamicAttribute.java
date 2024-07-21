package com.bai.pojo;


public class UserDynamicAttribute {
    private int user_id;
    private int subscribers_count;

    @Override
    public String toString() {
        return "UserDynamicAttribute{" +
                "user_id=" + user_id +
                ", subscribers_count=" + subscribers_count +
                ", posts_count=" + posts_count +
                '}';
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getSubscribers_count() {
        return subscribers_count;
    }

    public void setSubscribers_count(int subscribers_count) {
        this.subscribers_count = subscribers_count;
    }

    public int getPosts_count() {
        return posts_count;
    }

    public void setPosts_count(int posts_count) {
        this.posts_count = posts_count;
    }

    private int posts_count;

    public UserDynamicAttribute(int user_id, int subscribers_count, int posts_count) {
        this.user_id = user_id;
        this.subscribers_count = subscribers_count;
        this.posts_count = posts_count;
    }

    public UserDynamicAttribute() {
    }
}
