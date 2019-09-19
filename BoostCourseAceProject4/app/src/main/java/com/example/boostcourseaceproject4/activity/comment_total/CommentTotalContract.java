package com.example.boostcourseaceproject4.activity.comment_total;

import android.content.Context;

import com.example.boostcourseaceproject4.model.Comment;
import com.example.boostcourseaceproject4.model.MovieInfo;

import java.util.List;

public interface CommentTotalContract {
    interface View {
        void onToastMessage(String message); //토스트메세지
        void onGetCommentListResult(List<Comment> commentList);  //댓글리스트 서버 요청완료 후 작업
        void onGetCommentRecommendResult(Comment comment); //댓글추천 서버 요청완료 후 작업
    }
    interface Presenter{
        void requestCommentList(Context context, MovieInfo movieInfo); //댓글리스트 서버 요청
        void requestCommentRecommend(Context context, Comment comment); //댓글 추천 서버 요청
    }
}
