package com.bai.pojo;

public class UserPostLike {
    //我的喜欢的信息
    private Likes likes;
    //我的喜欢的帖子
    private UserPost userPost;

    @Override
    public String toString() {
        return "UserPostLike{" +
                "likes=" + likes +
                ", userPost=" + userPost +
                '}';
    }

    public Likes getLikes() {
        return likes;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    public UserPost getUserPost() {
        return userPost;
    }

    public void setUserPost(UserPost userPost) {
        this.userPost = userPost;
    }

    public UserPostLike(Likes likes, UserPost userPost) {
        this.likes = likes;
        this.userPost = userPost;
    }

    public UserPostLike() {
    }
}
