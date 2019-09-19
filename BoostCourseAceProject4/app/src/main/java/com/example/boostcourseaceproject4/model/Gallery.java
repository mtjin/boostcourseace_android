package com.example.boostcourseaceproject4.model;

public class Gallery {
    String url;
    int type; //0: 사진 , 1: 비디오

    public Gallery(String url, int type) {
        this.url = url;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
