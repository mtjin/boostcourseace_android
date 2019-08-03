package com.example.boostcourseaceproject;

import java.io.Serializable;

public class Comment implements Serializable {
    private int photo;
    private float ratingScore;
    private String nickName;
    private String comment;
    private int recommend;
    private String time;


    public Comment(int photo, float ratingScore, String nickName, String text, int recommend, String time) {
        this.photo = photo;
        this.ratingScore = ratingScore;
        this.nickName = nickName;
        this.comment = text;
        this.recommend = recommend;
        this.time = time;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public float getRatingScore() {
        return ratingScore;
    }

    public void setRatingScore(float ratingScore) {
        this.ratingScore = ratingScore;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
