package com.bai.pojo;

public class UserPostForward {
    //我的转发的帖子
    private UserPost userPost;
    //转发的信息
    private Forwarding forwarding;

    @Override
    public String toString() {
        return "UserPostForward{" +
                "userPost=" + userPost +
                ", forwarding=" + forwarding +
                '}';
    }

    public UserPostForward() {
    }

    public UserPostForward(UserPost userPost, Forwarding forwarding) {
        this.userPost = userPost;
        this.forwarding = forwarding;
    }

    public UserPost getUserPost() {
        return userPost;
    }

    public void setUserPost(UserPost userPost) {
        this.userPost = userPost;
    }

    public Forwarding getForwarding() {
        return forwarding;
    }

    public void setForwarding(Forwarding forwarding) {
        this.forwarding = forwarding;
    }
}
