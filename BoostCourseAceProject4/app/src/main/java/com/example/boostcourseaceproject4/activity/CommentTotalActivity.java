package com.example.boostcourseaceproject4.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.boostcourseaceproject4.adapter.CommentAdapter;
import com.example.boostcourseaceproject4.R;
import com.example.boostcourseaceproject4.databinding.ActivityCommentTotalBinding;
import com.example.boostcourseaceproject4.model.Comment;
import com.example.boostcourseaceproject4.model.MovieInfo;

import java.util.ArrayList;

public class CommentTotalActivity extends AppCompatActivity {
    //TAG
    final static String TAG = "CommentTotalActivityT";
    //binding
    ActivityCommentTotalBinding binding;
    //requestCode
    final static int WRITE_REQUEST = 11;
    //putExtra key
    final static String COMMENT_EXTRA = "COMMENT_EXTRA";
    final static String COMMENT_LIST_EXTRA = "COMMENT_LIST_EXTRA";
    final static String MOVIEINFO_EXTRA = "MOVIEINFO_EXTRA";
    //value
    private ArrayList<Comment> commentList = new ArrayList<>();
    private CommentAdapter commentAdapter;
    private MovieInfo movieInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comment_total);
        binding.setActivity(this);
        //인텐트처리
        processIntent();
    }

    //인텐트 수신 처리
    private void processIntent() {
        Intent intent = getIntent();
        commentList =intent.getParcelableArrayListExtra(COMMENT_LIST_EXTRA);
        movieInfo = (MovieInfo) intent.getParcelableExtra(MOVIEINFO_EXTRA);
        commentAdapter = new CommentAdapter(commentList, getApplicationContext());
        binding.commenttotalLivCommentlist.setAdapter(commentAdapter);//리스트뷰 어댑터연결
        binding.commenttotalRbRating.setRating(movieInfo.getUser_rating());
        binding.commenttotalTvScore.setText(movieInfo.getUser_rating() * 2 + "");
        binding.commenttotalTvParticipation.setText(commentList.size()+"");
    }


    //작성하기클릭
    public void writeOnClick(View view) {
        Toast.makeText(getApplicationContext(), "작성하기", Toast.LENGTH_SHORT).show();
        Intent writeIntent = new Intent(CommentTotalActivity.this, CommentWriteActivity.class);
        writeIntent.putExtra(MOVIEINFO_EXTRA, movieInfo);
        startActivityForResult(writeIntent, WRITE_REQUEST);
    }

    public void backOnClick(View view) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(COMMENT_LIST_EXTRA, commentList);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(COMMENT_LIST_EXTRA, commentList);
        setResult(RESULT_OK, resultIntent);
        Log.d(TAG, "댓글모두보기화면 뒤로가기클릭");
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WRITE_REQUEST && resultCode == RESULT_OK) { //작성하기결과
            Comment comment = (Comment) data.getParcelableExtra(COMMENT_EXTRA);
            if (comment != null) {
                commentAdapter.addItemFirst(comment);
                commentAdapter.notifyDataSetChanged();
            } else {
                Log.d(TAG, "작성하기 RESULT 실패");
            }
        }
    }
}
