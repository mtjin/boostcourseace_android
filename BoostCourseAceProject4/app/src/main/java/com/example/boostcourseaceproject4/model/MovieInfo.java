package com.example.boostcourseaceproject4.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/*"result": [
        {
        "title": "꾼",
        "id": 1,
        "date": "2017-11-22",
        "user_rating": 4.1,
        "audience_rating": 8.36,
        "reviewer_rating": 4.33,
        "reservation_rate": 61.69,
        "reservation_grade": 1,
        "grade": 15,
        "thumb": "http://movie2.phinf.naver.net/20171107_251/1510033896133nWqxG_JPEG/movie_image.jpg?type=m99_141_2",
        "image": "http://movie.phinf.naver.net/20171107_251/1510033896133nWqxG_JPEG/movie_image.jpg",
        "photos": "http://movie.phinf.naver.net/20171010_164/1507615090097Sml1w_JPEG/movie_image.jpg?type=m665_443_2,http://movie.phinf.naver.net/20171010_127/1507615090528Dgk7z_JPEG/movie_image.jpg?type=m665_443_2,http://movie.phinf.naver.net/20171010_165/1507615090907eGO1R_JPEG/movie_image.jpg?type=m665_443_2,http://movie.phinf.naver.net/20171010_39/1507615091158cqVLU_JPEG/movie_image.jpg?type=m665_443_2,http://movie.phinf.naver.net/20171010_210/1507615091408Co9EB_JPEG/movie_image.jpg?type=m665_443_2",
        "videos": "https://youtu.be/VJAPZ9cIbs0,https://youtu.be/y422jVFruic,https://youtu.be/JNL44p5kzTk",
        "outlinks": null,
        "genre": "범죄",
        "duration": 117,
        "audience": 476810,
        "synopsis": "‘희대의 사기꾼’을 잡기 위해 사기‘꾼’들이 뭉쳤다!\r\n“판 다시 짜야죠, 팀플레이로!”\r\n대한민국을 발칵 뒤집어 놓은 ‘희대의 사기꾼’ 장두칠이 돌연 사망했다는 뉴스가 발표된다.\r\n 그러나 그가 아직 살아있다는 소문과 함께 \r\n 그를 비호했던 권력자들이 의도적으로 풀어준 거라는 추측이 나돌기 시작한다. \r\n \r\n 사기꾼만 골라 속이는 사기꾼 지성(현빈)은 장두칠이 아직 살아있다며\r\n 사건 담당 검사 박희수(유지태)에게 그를 확실하게 잡자는 제안을 한다.\r\n 박검사의 비공식 수사 루트인 사기꾼 3인방 고석동(배성우), 춘자(나나), 김 과장(안세하)까지 합류시켜 \r\n 잠적한 장두칠의 심복 곽승건(박성웅)에게 접근하기 위한 새로운 판을 짜기 시작한다. \r\n \r\n 하지만 박검사는 장두칠 검거가 아닌 또 다른 목적을 위해 은밀히 작전을 세우고,\r\n 이를 눈치 챈 지성과 다른 꾼들도 서로 속지 않기 위해 각자만의 계획을 세우기 시작하는데…\r\n \r\n 이 판에선 누구도 믿지 마라!\r\n 진짜 ‘꾼’들의 예측불가 팀플레이가 시작된다!",
        "director": "장창원",
        "actor": "현빈(황지성), 유지태(박희수 검사), 배성우(고석동)",
        "like": 2163,
        "dislike": 421
        }
        ]*/
public class MovieInfo extends MovieInfoList implements Parcelable {
    @PrimaryKey
    @NonNull
    public String title;
    public int id;
    public String date;
    public float user_rating;
    public float audience_rating;
    public float reviewer_rating;
    public float reservation_rate;
    public int reservation_grade;
    public int grade;
    public String thumb;
    public String image;
    public String photos;
    public String videos;
    public String outlinks;
    public String genre;
    public int duration;
    public int audience;
    public String synopsis;
    public String director;
    public String actor;
    public int like;
    public int dislike;

    public MovieInfo() {
    }

    public MovieInfo(String title, int id, String date, float user_rating, float audience_rating, float reviewer_rating, float reservation_rate, int reservation_grade, int grade, String thumb, String image, String photos, String videos, String outlinks, String genre, int duration, int audience, String synopsis, String director, String actor, int like, int dislike) {
        this.title = title;
        this.id = id;
        this.date = date;
        this.user_rating = user_rating;
        this.audience_rating = audience_rating;
        this.reviewer_rating = reviewer_rating;
        this.reservation_rate = reservation_rate;
        this.reservation_grade = reservation_grade;
        this.grade = grade;
        this.thumb = thumb;
        this.image = image;
        this.photos = photos;
        this.videos = videos;
        this.outlinks = outlinks;
        this.genre = genre;
        this.duration = duration;
        this.audience = audience;
        this.synopsis = synopsis;
        this.director = director;
        this.actor = actor;
        this.like = like;
        this.dislike = dislike;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public String getOutlinks() {
        return outlinks;
    }

    public void setOutlinks(String outlinks) {
        this.outlinks = outlinks;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getAudience() {
        return audience;
    }

    public void setAudience(int audience) {
        this.audience = audience;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.id);
        dest.writeString(this.date);
        dest.writeFloat(this.user_rating);
        dest.writeFloat(this.audience_rating);
        dest.writeFloat(this.reviewer_rating);
        dest.writeFloat(this.reservation_rate);
        dest.writeInt(this.reservation_grade);
        dest.writeInt(this.grade);
        dest.writeString(this.thumb);
        dest.writeString(this.image);
        dest.writeString(this.photos);
        dest.writeString(this.videos);
        dest.writeString(this.outlinks);
        dest.writeString(this.genre);
        dest.writeInt(this.duration);
        dest.writeInt(this.audience);
        dest.writeString(this.synopsis);
        dest.writeString(this.director);
        dest.writeString(this.actor);
        dest.writeInt(this.like);
        dest.writeInt(this.dislike);
    }

    protected MovieInfo(Parcel in) {
        this.title = in.readString();
        this.id = in.readInt();
        this.date = in.readString();
        this.user_rating = in.readFloat();
        this.audience_rating = in.readFloat();
        this.reviewer_rating = in.readFloat();
        this.reservation_rate = in.readFloat();
        this.reservation_grade = in.readInt();
        this.grade = in.readInt();
        this.thumb = in.readString();
        this.image = in.readString();
        this.photos = in.readString();
        this.videos = in.readString();
        this.outlinks = in.readString();
        this.genre = in.readString();
        this.duration = in.readInt();
        this.audience = in.readInt();
        this.synopsis = in.readString();
        this.director = in.readString();
        this.actor = in.readString();
        this.like = in.readInt();
        this.dislike = in.readInt();
    }

    public static final Parcelable.Creator<MovieInfo> CREATOR = new Parcelable.Creator<MovieInfo>() {
        @Override
        public MovieInfo createFromParcel(Parcel source) {
            return new MovieInfo(source);
        }

        @Override
        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }
    };
}
