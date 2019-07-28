package com.example.boostcourseaceproject;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.boostcourseaceproject.databinding.ActivityMovieInfoBinding;

import java.util.ArrayList;

public class MovieInfoActivity extends AppCompatActivity {
    //TAG
    final static String TAG = "MovieInfoActivityT";
    //binding
    ActivityMovieInfoBinding binding;
    //value
    private boolean mLikeState = false;
    private boolean mDisLikeState = false;
    private int mLikeCount = 0;
    private int mDisLikeCount = 0;
    private ArrayList<Comment> mCommentList = new ArrayList<>();
    private CommentAdapter mCommentAdapter;
    //requestCode
    final static int WRITE_REQUEST = 11;
    final static int TOTAL_REQUEST = 12;
    //putExtra key
    final static String commentExtra = "commentExtra";
    final static String commentListExtra = "commentListExtra";

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
        mCommentList.add(comment);
        mCommentAdapter = new CommentAdapter(mCommentList, getApplicationContext());
        binding.movieinfoLivComments.setAdapter(mCommentAdapter);//리스트뷰 어댑터연결
    }

    //좋아요클릭
    public void onLikeClick(View view) {
        if (mLikeState) {
            mLikeCount -= 1;
            binding.movieinfoTvLikecount.setText(String.valueOf(mLikeCount));
            binding.movieinfoIbtnLikeup.setBackgroundResource(R.drawable.selector_thumbs_up);
        } else {
            if (mDisLikeState) {
                mDisLikeCount -= 1;
                binding.movieinfoTvDislikecount.setText(String.valueOf(mDisLikeCount));
                binding.movieinfoIbtnDislikeup.setBackgroundResource(R.drawable.selector_thumbs_down);
                mDisLikeState = false;
            }
            mLikeCount += 1;
            binding.movieinfoTvLikecount.setText(String.valueOf(mLikeCount));
            binding.movieinfoIbtnLikeup.setBackgroundResource(R.drawable.ic_thumb_up_selected);
        }
        mLikeState = !mLikeState;
    }

    //싫어요클릭
    public void onDislikeClick(View view) {
        if (mDisLikeState) {
            mDisLikeCount -= 1;
            binding.movieinfoTvDislikecount.setText(String.valueOf(mDisLikeCount));
            binding.movieinfoIbtnDislikeup.setBackgroundResource(R.drawable.selector_thumbs_down);
        } else {
            if (mLikeState) {
                mLikeCount -= 1;
                binding.movieinfoTvLikecount.setText(String.valueOf(mLikeCount));
                binding.movieinfoIbtnLikeup.setBackgroundResource(R.drawable.selector_thumbs_up);
                mLikeState = false;
            }
            mDisLikeCount += 1;
            binding.movieinfoTvDislikecount.setText(String.valueOf(mDisLikeCount));
            binding.movieinfoIbtnDislikeup.setBackgroundResource(R.drawable.ic_thumb_down_selected);
        }
        mDisLikeState = !mDisLikeState;
    }


    //댓글모두보기 버튼 클릭
    public void onAllViewClick(View view) {
        Toast.makeText(getApplicationContext(), "모두보기", Toast.LENGTH_SHORT).show();
        Intent totalCommentIntent = new Intent(MovieInfoActivity.this, CommentTotalActivity.class );
        totalCommentIntent.putExtra(commentListExtra, mCommentList);
        startActivityForResult(totalCommentIntent, TOTAL_REQUEST);
    }

    //작성하기 버튼 클릭
    public void onWriteClick(View view) {
        Toast.makeText(getApplicationContext(), "작성하기", Toast.LENGTH_SHORT).show();
        Intent writeIntent = new Intent(MovieInfoActivity.this, CommentWriteActivity.class);
        startActivityForResult(writeIntent, WRITE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WRITE_REQUEST && resultCode == RESULT_OK) {
            Comment comment = (Comment) data.getSerializableExtra(commentExtra);
            if (comment != null) {
                mCommentList.add(comment);
                mCommentAdapter.notifyDataSetChanged();
            } else {
                Log.d(TAG, "작성하기 RESULT 실패");
            }
        }else if(requestCode == TOTAL_REQUEST && resultCode == RESULT_OK){ //댓글전체보기 결과
            mCommentList.clear();
            mCommentAdapter.clear();
            mCommentList = (ArrayList<Comment>) data.getSerializableExtra(commentListExtra);
            mCommentAdapter.addAll((ArrayList<Comment>) data.getSerializableExtra(commentListExtra));
            mCommentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent resultIntent = new Intent();
        resultIntent.putExtra(commentListExtra, mCommentList);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
