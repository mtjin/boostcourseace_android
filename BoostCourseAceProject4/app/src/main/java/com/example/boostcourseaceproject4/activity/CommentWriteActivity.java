package com.example.boostcourseaceproject4.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.boostcourseaceproject4.model.Comment;
import com.example.boostcourseaceproject4.R;
import com.example.boostcourseaceproject4.databinding.ActivityCommentWriteBinding;


public class CommentWriteActivity extends AppCompatActivity {
    ActivityCommentWriteBinding binding;
    //putExtra key
    final static String COMMENT_EXTRA = "COMMENT_EXTRA" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comment_write);
        binding.setActivity(this);
    }

    //저장버튼클릭
    public void onOkClick(View view){
       String message =  binding.commentwriteEtWrite.getText().toString().trim();
       float rating =  binding.commentwriteRbRating.getRating();
       if(message.length() <= 0){
           Toast.makeText(this, "한글자 이상 적어주세요", Toast.LENGTH_SHORT).show();
       }else {
           //메세지내용, 레이팅바 점수 외 나머진 고정으로 해서 전달해주도록 한다.
           Comment comment = new Comment(R.drawable.ic_userprofile, rating, "진승언", message, 0, "10");
           Intent resultIntent = new Intent();
           resultIntent.putExtra(COMMENT_EXTRA, comment);
           setResult(RESULT_OK, resultIntent);
           finish();
       }
    }

    //취소버튼클릭
    public void onCancelClick(View view){
        finish();
    }
}
