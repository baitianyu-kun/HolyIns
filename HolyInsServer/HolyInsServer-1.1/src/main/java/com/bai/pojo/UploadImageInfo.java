package com.bai.pojo;

public class UploadImageInfo {
    private String image_path;
    private long image_size;

    @Override
    public String toString() {
        return "UploadImageInfo{" +
                "image_path='" + image_path + '\'' +
                ", image_size=" + image_size +
                '}';
    }

    public UploadImageInfo() {
    }

    public UploadImageInfo(String image_path, long image_size) {
        this.image_path = image_path;
        this.image_size = image_size;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public long getImage_size() {
        return image_size;
    }

    public void setImage_size(long image_size) {
        this.image_size = image_size;
    }
}
