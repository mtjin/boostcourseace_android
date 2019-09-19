package com.example.boostcourseaceproject4.activity.comment_write;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.boostcourseaceproject4.R;
import com.example.boostcourseaceproject4.databinding.ActivityCommentWriteBinding;
import com.example.boostcourseaceproject4.model.MovieInfo;
import com.example.boostcourseaceproject4.utils.NetworkStatusHelper;


public class CommentWriteActivity extends AppCompatActivity implements CommentWriteContract.View {
    ActivityCommentWriteBinding binding;
    //putExtra key
    final static String MOVIEINFO_EXTRA = "MOVIEINFO_EXTRA";
    //value
    private int id; //영화 아이디
    private MovieInfo movieInfo;
    private CommentWritePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comment_write);
        binding.setActivity(this);
        //presenter 생성
        presenter = new CommentWritePresenter(this);
        //인텐트처리
        processIntent();
    }

    //저장버튼클릭
    public void onOkClick(View view) {
        if(NetworkStatusHelper.getConnectivityStatus(getApplicationContext())) {
            String message = binding.commentwriteEtWrite.getText().toString().trim();
            float rating = binding.commentwriteRbRating.getRating();
            if (message.length() <= 0) {
                onToastMessage("한글자 이상 적어주세요");
            } else {
                presenter.requestWriteComment(id, "이이22", rating, message);
                setResult(RESULT_OK);
                finish();
            }
        }else{
            onToastMessage("인터넷 연결이 끊켜있습니다.");
        }
    }

    private void processIntent() {
        Intent resultIntent = getIntent();
        movieInfo = (MovieInfo) resultIntent.getParcelableExtra(MOVIEINFO_EXTRA);
        String title = movieInfo.getTitle();
        int grade = movieInfo.getGrade();
        binding.commentwriteTvTitle.setText(title);
        if (grade == 12) {
            binding.commentwriteIvGrade.setImageResource(R.drawable.ic_12);
        } else if (grade == 15) {
            binding.commentwriteIvGrade.setImageResource(R.drawable.ic_15);
        } else if (grade == 19) {
            binding.commentwriteIvGrade.setImageResource(R.drawable.ic_19);
        }
        id = movieInfo.getId();
    }

    //취소버튼클릭
    public void onCancelClick(View view) {
        finish();
    }

    @Override
    public void onToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
