package com.example.boostcourseaceproject4.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.boostcourseaceproject4.model.Comment;
import com.example.boostcourseaceproject4.adapter.CommentAdapter;
import com.example.boostcourseaceproject4.R;
import com.example.boostcourseaceproject4.databinding.ActivityCommentTotalBinding;

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
    //value
    private ArrayList<Comment> mCommentList = new ArrayList<>();
    private CommentAdapter mCommentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comment_total);
        binding.setActivity(this);
        //인텐트처리
        processIntent();
    }

    //인텐트 수신 처리
    private void processIntent(){
        Intent intent = getIntent();
        mCommentList = (ArrayList<Comment>) intent.getSerializableExtra(COMMENT_LIST_EXTRA);
        mCommentAdapter = new CommentAdapter(mCommentList, getApplicationContext());
        binding.commenttotalLivCommentlist.setAdapter(mCommentAdapter);//리스트뷰 어댑터연결
    }


    //작성하기클릭
    public void writeOnClick(View view){
        Toast.makeText(getApplicationContext(), "작성하기", Toast.LENGTH_SHORT).show();
        Intent writeIntent = new Intent(CommentTotalActivity.this, CommentWriteActivity.class);
        startActivityForResult(writeIntent, WRITE_REQUEST);
    }

    public void backOnClick(View view){
        Intent resultIntent = new Intent();
        resultIntent.putExtra(COMMENT_LIST_EXTRA, mCommentList);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(COMMENT_LIST_EXTRA, mCommentList);
        setResult(RESULT_OK, resultIntent);
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WRITE_REQUEST && resultCode == RESULT_OK) { //작성하기결과
            Comment comment = (Comment) data.getSerializableExtra(COMMENT_EXTRA);
            if (comment != null) {
                mCommentList.add(comment);
                mCommentAdapter.notifyDataSetChanged();
            } else {
                Log.d(TAG, "작성하기 RESULT 실패");
            }
        }

    }
}
