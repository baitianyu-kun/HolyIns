package com.bai.HolyIns.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class PersonInfo {
    private Post post;
    private PostForward postForward;
    private PostComment postComment;
    public PostLikes postLikes;
    private UserInfo userInfo;
    private Subscribe subscribe;
    private UserDynamicAttribute userDynamicAttribute;

    @Override
    public String toString() {
        return "PersonInfo{" +
                "post=" + post +
                ", postForward=" + postForward +
                ", postComment=" + postComment +
                ", postLikes=" + postLikes +
                ", userInfo=" + userInfo +
                ", subscribe=" + subscribe +
                ", userDynamicAttribute=" + userDynamicAttribute +
                '}';
    }

    public PersonInfo(Post post, PostForward postForward, PostComment postComment, PostLikes postLikes, UserInfo userInfo, Subscribe subscribe, UserDynamicAttribute userDynamicAttribute) {
        this.post = post;
        this.postForward = postForward;
        this.postComment = postComment;
        this.postLikes = postLikes;
        this.userInfo = userInfo;
        this.subscribe = subscribe;
        this.userDynamicAttribute = userDynamicAttribute;
    }

    public PersonInfo() {
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public PostForward getPostForward() {
        return postForward;
    }

    public void setPostForward(PostForward postForward) {
        this.postForward = postForward;
    }

    public PostComment getPostComment() {
        return postComment;
    }

    public void setPostComment(PostComment postComment) {
        this.postComment = postComment;
    }

    public PostLikes getPostLikes() {
        return postLikes;
    }

    public void setPostLikes(PostLikes postLikes) {
        this.postLikes = postLikes;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Subscribe getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Subscribe subscribe) {
        this.subscribe = subscribe;
    }

    public UserDynamicAttribute getUserDynamicAttribute() {
        return userDynamicAttribute;
    }

    public void setUserDynamicAttribute(UserDynamicAttribute userDynamicAttribute) {
        this.userDynamicAttribute = userDynamicAttribute;
    }
}
