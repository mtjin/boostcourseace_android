package com.example.boostcourseaceproject;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentView extends LinearLayout {
    private CircleImageView mPhotoImageView;  //사진
    private RatingBar mRatingBar;  //별점
    private TextView mNickNameTextView; //이름
    private TextView mCommentTextView; //한줄평
    private TextView mTimeTextView;  //작성시간
    private TextView mRecommendCountTextView; //추천수

    public CommentView(Context context) {
        super(context);
        init(context);
    }

    public CommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        //인플래이션 서비스를 사용하기위해 갖고옴
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //인플레이션의 기능을 사용해서 아이템뷰를 inflate해줌(붙여줌)
        inflater.inflate(R.layout.item_comment, this, true);
        mPhotoImageView = findViewById(R.id.comment_civ_photo);
        mRatingBar = findViewById(R.id.comment_rb_rating);
        mNickNameTextView = findViewById(R.id.comment_tv_nickname);
        mCommentTextView = findViewById(R.id.comment_tv_comment);
        mTimeTextView = findViewById(R.id.comment_tv_time);
        mRecommendCountTextView = findViewById(R.id.comment_tv_recommendcount);
    }


    public void setmPhotoImageView(int photo) {
        mPhotoImageView.setImageResource(photo);
    }

    public void setmRatingBar(float ratingScore) {
        mRatingBar.setRating(ratingScore);
    }


    public void setmNickNameTextView(String nickName) {
        mNickNameTextView.setText(nickName);
    }

    public void setmCommentTextView(String comment) {
        mCommentTextView.setText(comment);
    }


    public void setmTimeTextView(String time) {
        mTimeTextView.setText(time);
    }


    public void setmRecommendCountTextView(int recommendCount) {
        mRecommendCountTextView.setText(recommendCount+"");
    }
}
