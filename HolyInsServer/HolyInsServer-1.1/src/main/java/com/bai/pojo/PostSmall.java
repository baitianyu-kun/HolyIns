package com.bai.pojo;


import java.io.Serializable;
import java.util.List;

public class PostSmall implements Serializable {
    private static final long serialVersionUID=1L;
    private int photo_id;
    private Photo photo;
    private List<Comment> comments;

    @Override
    public String toString() {
        return "PostSmall{" +
                "photo_id=" + photo_id +
                ", photo=" + photo +
                ", comments=" + comments +
                '}';
    }

    public int getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(int photo_id) {
        this.photo_id = photo_id;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public PostSmall(int photo_id, Photo photo, List<Comment> comments) {
        this.photo_id = photo_id;
        this.photo = photo;
        this.comments = comments;
    }

    public PostSmall() {
    }
}
