package com.bai.pojo;


import org.springframework.web.multipart.MultipartFile;


public class ReleasePost {
    private Photo photo;
    private HashTag hashTag;
    private MultipartFile multipartFile;

    @Override
    public String toString() {
        return "ReleasePost{" +
                "photo=" + photo +
                ", hashTag=" + hashTag +
                ", multipartFile=" + multipartFile +
                '}';
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public HashTag getHashTag() {
        return hashTag;
    }

    public void setHashTag(HashTag hashTag) {
        this.hashTag = hashTag;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public ReleasePost() {
    }

    public ReleasePost(Photo photo, HashTag hashTag, MultipartFile multipartFile) {
        this.photo = photo;
        this.hashTag = hashTag;
        this.multipartFile = multipartFile;
    }
}
