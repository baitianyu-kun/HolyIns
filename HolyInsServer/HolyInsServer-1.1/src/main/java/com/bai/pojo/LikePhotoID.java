package com.bai.pojo;

public class LikePhotoID {
    //一个likes对应一个photo_id
    private Likes likes;
    private int photo_id;

    public LikePhotoID(Likes likes, int photo_id) {
        this.likes = likes;
        this.photo_id = photo_id;
    }

    public LikePhotoID() {
    }

    @Override
    public String toString() {
        return "LikePhotoID{" +
                "likes=" + likes +
                ", photo_id=" + photo_id +
                '}';
    }

    public Likes getLikes() {
        return likes;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    public int getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(int photo_id) {
        this.photo_id = photo_id;
    }
}
