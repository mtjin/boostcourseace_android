package com.example.boostcourseaceproject4.model;

import java.io.Serializable;

//영화정보 : 일단 간단하게 몇개만 만들어놨다.
public class Movie implements Serializable {
    private int id;
    private int image;
    private String title;
    private String grade;
    private String reservedRate; //예매율

    public Movie(int id, int image, String title, String grade, String reservedRate) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.grade = grade;
        this.reservedRate = reservedRate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getReservedRate() {
        return reservedRate;
    }

    public void setReservedRate(String reservedRate) {
        this.reservedRate = reservedRate;
    }
}
