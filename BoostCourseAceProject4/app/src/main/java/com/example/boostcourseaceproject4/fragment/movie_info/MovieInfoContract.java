package com.example.boostcourseaceproject4.fragment.movie_info;

import android.content.Context;

import com.example.boostcourseaceproject4.model.Comment;
import com.example.boostcourseaceproject4.model.MovieInfo;

import java.util.List;

public interface MovieInfoContract {
    interface View {
        void onToastMessage(String message); //토스트메세지
        void onGetCommentListResult(List<Comment> commentList); //댓글리스트 서버요청 완료 후 작업(UI작업)
        void onGetMovieInfoResult(MovieInfo movieInfo); //영화 상세정보 요청 완료 후 작업
        void onGetCommentRecommendResult(Comment comment); //댓글 추천 서버요청 완료 후 작업
    }

    interface Presenter{
        void requestCommentList(Context context, int movieId); // 댓글리스트 서버 요청
        void requestMovieInfo(Context context, int movieId); // 영화 상세정보 서버 요청
        void requestMovieLike(int movieId, boolean likeCancel, boolean likeUp, boolean dislikeUp, boolean dislikeCancel); // 영화 좋아요 싫어요 서버 요청
        void requestCommentRecommend(Context context, Comment comment); //댓글 추천 서버 요청
    }
}
