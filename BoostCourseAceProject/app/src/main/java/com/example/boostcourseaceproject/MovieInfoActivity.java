package com.example.boostcourseaceproject;


import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.boostcourseaceproject.databinding.ActivityMovieInfoBinding;

import java.util.ArrayList;

public class MovieInfoActivity extends AppCompatActivity {
    ActivityMovieInfoBinding binding;
    //value
    private boolean mLikeState = false;
    private boolean mDisLikeState = false;
    private int mLikeCount = 0;
    private int mDisLikeCount = 0;
    private ArrayList<Comment> commentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_info);
        binding.setActivity(this);
        //초기 좋아요 싫어요 개수 세팅
        mLikeCount = Integer.parseInt(binding.movieinfoTvLikecount.getText().toString().trim());
        mDisLikeCount = Integer.parseInt(binding.movieinfoTvDislikecount.getText().toString().trim());
        //리스트뷰 어댑터
        Comment comment = new Comment(R.drawable.ic_userprofile, 4, "진승언", "볼만했어요!", 0, "10");
        commentList.add(comment);
        commentList.add(comment);
        commentList.add(comment);
        commentList.add(comment);
        commentList.add(comment);
        CommentAdapter adapter = new CommentAdapter(commentList, getApplicationContext());
        binding.movieinfoLivComments.setAdapter(adapter);//리스트뷰 어댑터연결
    }

    public void onLikeClick(View view) {
        if (mLikeState) {
            decrLikeCount();
        } else {
            incrLikeCount();
        }
        mLikeState = !mLikeState;
    }

    public void onDislikeClick(View view) {
        if (mDisLikeState) {
            decrDislikeCount();
        } else {
            incrDislikeCount();
        }
        mDisLikeState = !mDisLikeState;
    }

    private void incrLikeCount() {
        if (mDisLikeState) {
            decrDislikeCount();
            mDisLikeState = false;
        }
        mLikeCount += 1;
        binding.movieinfoTvLikecount.setText(String.valueOf(mLikeCount));

        binding.movieinfoIbtnLikeup.setBackgroundResource(R.drawable.ic_thumb_up_selected);
    }

    private void decrLikeCount() {
        mLikeCount -= 1;
        binding.movieinfoTvLikecount.setText(String.valueOf(mLikeCount));
        binding.movieinfoIbtnLikeup.setBackgroundResource(R.drawable.selector_thumbs_up);
    }

    private void incrDislikeCount() {
        if(mLikeState){
            decrLikeCount();
            mLikeState = false;
        }
        mDisLikeCount += 1;
        binding.movieinfoTvDislikecount.setText(String.valueOf(mDisLikeCount));
        binding.movieinfoIbtnDislikeup.setBackgroundResource(R.drawable.ic_thumb_down_selected);
    }

    private void decrDislikeCount() {
        mDisLikeCount -= 1;
        binding.movieinfoTvDislikecount.setText(String.valueOf(mDisLikeCount));
        binding.movieinfoIbtnDislikeup.setBackgroundResource(R.drawable.selector_thumbs_down);
    }


    public void onAllViewClick(View view) {
        Toast.makeText(getApplicationContext(), "모두보기", Toast.LENGTH_SHORT).show();
    }

    public void onWriteClick(View view) {
        Toast.makeText(getApplicationContext(), "작성하기", Toast.LENGTH_SHORT).show();
    }


}
