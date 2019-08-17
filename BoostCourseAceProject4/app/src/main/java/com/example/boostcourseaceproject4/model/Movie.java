package com.example.boostcourseaceproject4.model;

import android.os.Parcel;
import android.os.Parcelable;

/*
 "id": 1,
"title": "꾼",
"title_eng": "The Swindlers",
"date": "2017-11-22",
"user_rating": 4.1,
"audience_rating": 8.36,
"reviewer_rating": 4.33,
"reservation_rate": 61.69,
"reservation_grade": 1,
"grade": 15,
"thumb": "http://movie2.phinf.naver.net/20171107_251/1510033896133nWqxG_JPEG/movie_image.jpg?type=m99_141_2",
"image": "http://movie.phinf.naver.net/20171107_251/1510033896133nWqxG_JPEG/movie_image.jpg"
* */
public class Movie implements Parcelable {

    public int id;
    public String title;
    public String title_eng;
    public String data;
    public float user_rating;
    public float audience_rating;
    public float reviewer_rating;
    public float reservation_rate;
    public int reservation_grade;
    public int grade;
    public String thumb;
    public String image;

    public Movie() {
    }

    public Movie(int id, String title, String title_eng, String data, float user_rating, float audience_rating, float reviewer_rating, float reservation_rate, int reservation_grade, int grade, String thumb, String image) {
        this.id = id;
        this.title = title;
        this.title_eng = title_eng;
        this.data = data;
        this.user_rating = user_rating;
        this.audience_rating = audience_rating;
        this.reviewer_rating = reviewer_rating;
        this.reservation_rate = reservation_rate;
        this.reservation_grade = reservation_grade;
        this.grade = grade;
        this.thumb = thumb;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_eng() {
        return title_eng;
    }

    public void setTitle_eng(String title_eng) {
        this.title_eng = title_eng;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public float getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(float user_rating) {
        this.user_rating = user_rating;
    }

    public float getAudience_rating() {
        return audience_rating;
    }

    public void setAudience_rating(float audience_rating) {
        this.audience_rating = audience_rating;
    }

    public float getReviewer_rating() {
        return reviewer_rating;
    }

    public void setReviewer_rating(float reviewer_rating) {
        this.reviewer_rating = reviewer_rating;
    }

    public float getReservation_rate() {
        return reservation_rate;
    }

    public void setReservation_rate(float reservation_rate) {
        this.reservation_rate = reservation_rate;
    }

    public int getReservation_grade() {
        return reservation_grade;
    }

    public void setReservation_grade(int reservation_grade) {
        this.reservation_grade = reservation_grade;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.title_eng);
        dest.writeString(this.data);
        dest.writeFloat(this.user_rating);
        dest.writeFloat(this.audience_rating);
        dest.writeFloat(this.reviewer_rating);
        dest.writeFloat(this.reservation_rate);
        dest.writeInt(this.reservation_grade);
        dest.writeInt(this.grade);
        dest.writeString(this.thumb);
        dest.writeString(this.image);
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.title_eng = in.readString();
        this.data = in.readString();
        this.user_rating = in.readFloat();
        this.audience_rating = in.readFloat();
        this.reviewer_rating = in.readFloat();
        this.reservation_rate = in.readFloat();
        this.reservation_grade = in.readInt();
        this.grade = in.readInt();
        this.thumb = in.readString();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
