package com.example.boostcourseaceproject4.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/*"result": [
        {
        "id": 3445,
        "writer": "sjh",
        "movieId": 1,
        "writer_image": null,
        "time": "2019-03-23 20:59:12",
        "timestamp": 1553342351,
        "rating": 4.4,
        "contents": "헉 넘나 재밌어용하하하",
        "recommend": 0
        },*/
public class Comment  extends CommentList implements Parcelable {
    public int id;
    public String writer;
    public int movieId;
    public String time;
    public float rating;
    public String contents;
    public int recommend;

    public Comment() {
    }

    public Comment(int id, String writer, int movieId, String time, float rating, String contents, int recommend) {
        this.id = id;
        this.writer = writer;
        this.movieId = movieId;
        this.time = time;
        this.rating = rating;
        this.contents = contents;
        this.recommend = recommend;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.writer);
        dest.writeInt(this.movieId);
        dest.writeString(this.time);
        dest.writeFloat(this.rating);
        dest.writeString(this.contents);
        dest.writeInt(this.recommend);
    }

    protected Comment(Parcel in) {
        this.id = in.readInt();
        this.writer = in.readString();
        this.movieId = in.readInt();
        this.time = in.readString();
        this.rating = in.readFloat();
        this.contents = in.readString();
        this.recommend = in.readInt();
    }

    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
