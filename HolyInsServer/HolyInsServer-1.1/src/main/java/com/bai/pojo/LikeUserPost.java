package com.bai.pojo;

public class LikeUserPost {
    private UserPost userPost;
    private Likes likes;

    @Override
    public String toString() {
        return "LikeUserPost{" +
                "userPost=" + userPost +
                ", likes=" + likes +
                '}';
    }

    public LikeUserPost(UserPost userPost, Likes likes) {
        this.userPost = userPost;
        this.likes = likes;
    }

    public LikeUserPost() {
    }

    public UserPost getUserPost() {
        return userPost;
    }

    public void setUserPost(UserPost userPost) {
        this.userPost = userPost;
    }

    public Likes getLikes() {
        return likes;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
    }
}
