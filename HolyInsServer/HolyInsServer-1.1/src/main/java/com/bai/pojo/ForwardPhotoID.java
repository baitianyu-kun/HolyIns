package com.bai.pojo;

public class ForwardPhotoID {
    //一个forwarding对应一个photo_id
    private Forwarding forwarding;
    private int photo_id;
    //伪id，防止mybatis在主键相同的情况下自动去重
    private int row_no;

    public ForwardPhotoID(Forwarding forwarding, int photo_id, int row_no) {
        this.forwarding = forwarding;
        this.photo_id = photo_id;
        this.row_no = row_no;
    }

    public Forwarding getForwarding() {
        return forwarding;
    }

    public void setForwarding(Forwarding forwarding) {
        this.forwarding = forwarding;
    }

    public int getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(int photo_id) {
        this.photo_id = photo_id;
    }

    public int getRow_no() {
        return row_no;
    }

    public void setRow_no(int row_no) {
        this.row_no = row_no;
    }

    @Override
    public String toString() {
        return "ForwardPhotoID{" +
                "forwarding=" + forwarding +
                ", photo_id=" + photo_id +
                ", row_no=" + row_no +
                '}';
    }

    public ForwardPhotoID() {
    }
}
