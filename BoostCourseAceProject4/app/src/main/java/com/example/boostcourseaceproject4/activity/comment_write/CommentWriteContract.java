package com.example.boostcourseaceproject4.activity.comment_write;


public interface CommentWriteContract {
    interface View{
        void onToastMessage(String message); //토스트메세지
    }

    interface  Presenter{
        void   requestWriteComment(int movieId, String writer, float score, String content ); //댓글 작성 서버 요청
    }
}
