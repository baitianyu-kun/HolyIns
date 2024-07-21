package com.bai.pojo;



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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public PersonInfo() {
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
}
