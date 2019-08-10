package com.example.boostcourseaceproject4.modelview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.boostcourseaceproject4.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentView extends LinearLayout {
    private CircleImageView photoImageView;  //사진
    private RatingBar ratingBar;  //별점
    private TextView nickNameTextView; //이름
    private TextView commentTextView; //한줄평
    private TextView timeTextView;  //작성시간
    private TextView recommendCountTextView; //추천수

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
        photoImageView = findViewById(R.id.comment_civ_photo);
        ratingBar = findViewById(R.id.comment_rb_rating);
        nickNameTextView = findViewById(R.id.comment_tv_nickname);
        commentTextView = findViewById(R.id.comment_tv_comment);
        timeTextView = findViewById(R.id.comment_tv_time);
        recommendCountTextView = findViewById(R.id.comment_tv_recommendcount);
    }


    public void setPhotoImageView(int photo) {
        photoImageView.setImageResource(photo);
    }

    public void setRatingBar(float ratingScore) {
        ratingBar.setRating(ratingScore);
    }


    public void setNickNameTextView(String nickName) {
        nickNameTextView.setText(nickName);
    }

    public void setCommentTextView(String comment) {
        commentTextView.setText(comment);
    }


    public void setTimeTextView(String time) {
        timeTextView.setText(time);
    }


    public void setRecommendCountTextView(int recommendCount) {
        recommendCountTextView.setText(recommendCount+"");
    }
}
