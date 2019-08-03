package com.example.boostcourseaceproject;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
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
    //mBinding
    ActivityMovieInfoBinding mBinding;
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
    final static String COMMENT_EXTRA = "COMMENT_EXTRA";
    final static String COMMENT_LIST_EXTRA = "COMMENT_LIST_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_info);
        mBinding.setActivity(this);
        //스크롤뷰안의 리스트뷰 스크롤은  android:nestedScrollingEnabled ="true"로 할 수 있으나 API21이전 기기에서는 작동을 안하므로 다음 코드를 추가해주었다.
        ViewCompat.setNestedScrollingEnabled(mBinding.movieinfoLivComments, true);
        //초기 좋아요 싫어요 개수 세팅
        mLikeCount = Integer.parseInt(mBinding.movieinfoTvLikecount.getText().toString().trim());
        mDisLikeCount = Integer.parseInt(mBinding.movieinfoTvDislikecount.getText().toString().trim());
        //리스트뷰 어댑터
        Comment comment = new Comment(R.drawable.ic_userprofile, 4, "진승언", "볼만했어요!", 0, "10");
        Comment comment1 = new Comment(R.drawable.ic_userprofile, 4, "진승언", "볼만했어요!", 0, "10");
        Comment comment2 = new Comment(R.drawable.ic_userprofile, 4, "진승언", "볼만했어요!", 0, "10");
        Comment comment3 = new Comment(R.drawable.ic_userprofile, 4, "진승언", "볼만했어요!", 0, "10");
        Comment comment4 = new Comment(R.drawable.ic_userprofile, 4, "진승언", "볼만했어요!", 0, "10");
        Comment comment5 = new Comment(R.drawable.ic_userprofile, 4, "진승언", "볼만했어요!", 0, "10");
        mCommentList.add(comment);
        mCommentList.add(comment1);
        mCommentList.add(comment2);
        mCommentList.add(comment3);
        mCommentList.add(comment4);
        mCommentList.add(comment5);
        mCommentAdapter = new CommentAdapter(mCommentList, getApplicationContext());
        mBinding.movieinfoLivComments.setAdapter(mCommentAdapter);//리스트뷰 어댑터연결
    }

   /* //스타터패턴
    public static void start(Context context, ArrayList<Comment> commentList ) {
        Intent starter = new Intent(context, CommentTotalActivity.class);
        starter.putExtra(COMMENT_LIST_EXTRA, commentList);
        context.startActivity(starter);
    }*/

    //좋아요클릭
    public void onLikeClick(View view) {
        if (mLikeState) {
            mLikeCount -= 1;
            mBinding.movieinfoTvLikecount.setText(String.valueOf(mLikeCount));
            mBinding.movieinfoIbtnLikeup.setBackgroundResource(R.drawable.selector_thumbs_up);
        } else {
            if (mDisLikeState) {
                mDisLikeCount -= 1;
                mBinding.movieinfoTvDislikecount.setText(String.valueOf(mDisLikeCount));
                mBinding.movieinfoIbtnDislikeup.setBackgroundResource(R.drawable.selector_thumbs_down);
                mDisLikeState = false;
            }
            mLikeCount += 1;
            mBinding.movieinfoTvLikecount.setText(String.valueOf(mLikeCount));
            mBinding.movieinfoIbtnLikeup.setBackgroundResource(R.drawable.ic_thumb_up_selected);
        }
        mLikeState = !mLikeState;
    }

    //싫어요클릭
    public void onDislikeClick(View view) {
        if (mDisLikeState) {
            mDisLikeCount -= 1;
            mBinding.movieinfoTvDislikecount.setText(String.valueOf(mDisLikeCount));
            mBinding.movieinfoIbtnDislikeup.setBackgroundResource(R.drawable.selector_thumbs_down);
        } else {
            if (mLikeState) {
                mLikeCount -= 1;
                mBinding.movieinfoTvLikecount.setText(String.valueOf(mLikeCount));
                mBinding.movieinfoIbtnLikeup.setBackgroundResource(R.drawable.selector_thumbs_up);
                mLikeState = false;
            }
            mDisLikeCount += 1;
            mBinding.movieinfoTvDislikecount.setText(String.valueOf(mDisLikeCount));
            mBinding.movieinfoIbtnDislikeup.setBackgroundResource(R.drawable.ic_thumb_down_selected);
        }
        mDisLikeState = !mDisLikeState;
    }


    //댓글모두보기 버튼 클릭
    public void onAllViewClick(View view) {
        Toast.makeText(getApplicationContext(), "모두보기", Toast.LENGTH_SHORT).show();
        Intent totalCommentIntent = new Intent(MovieInfoActivity.this, CommentTotalActivity.class);
        totalCommentIntent.putExtra(COMMENT_LIST_EXTRA, mCommentList);
        startActivityForResult(totalCommentIntent, TOTAL_REQUEST);
        //    start(this, mCommentList);
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
            Comment comment = (Comment) data.getSerializableExtra(COMMENT_EXTRA);
            if (comment != null) {
                mCommentList.add(comment);
                mCommentAdapter.notifyDataSetChanged();
            } else {
                Log.d(TAG, "작성하기 RESULT 실패");
            }
        } else if (requestCode == TOTAL_REQUEST && resultCode == RESULT_OK) { //댓글전체보기 결과
            mCommentList.clear();
            mCommentAdapter.clear();
            mCommentList = (ArrayList<Comment>) data.getSerializableExtra(COMMENT_LIST_EXTRA);
            mCommentAdapter.addAll((ArrayList<Comment>) data.getSerializableExtra(COMMENT_LIST_EXTRA));
            mCommentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent resultIntent = new Intent();
        resultIntent.putExtra(COMMENT_LIST_EXTRA, mCommentList);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
