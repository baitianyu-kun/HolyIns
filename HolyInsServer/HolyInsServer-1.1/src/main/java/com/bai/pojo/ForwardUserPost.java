package com.bai.pojo;

public class ForwardUserPost {
    private UserPost userPost;
    private Forwarding forwarding;

    @Override
    public String toString() {
        return "ForwardUserPost{" +
                "userPost=" + userPost +
                ", forwarding=" + forwarding +
                '}';
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

    public ForwardUserPost() {
    }

    public ForwardUserPost(UserPost userPost, Forwarding forwarding) {
        this.userPost = userPost;
        this.forwarding = forwarding;
    }
}
