package com.example.boostcourseaceproject4.activity.comment_total;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.boostcourseaceproject4.activity.comment_write.CommentWriteActivity;
import com.example.boostcourseaceproject4.adapter.CommentAdapter;
import com.example.boostcourseaceproject4.R;
import com.example.boostcourseaceproject4.databinding.ActivityCommentTotalBinding;
import com.example.boostcourseaceproject4.interfaces.CommentItemClickListener;
import com.example.boostcourseaceproject4.model.Comment;
import com.example.boostcourseaceproject4.model.MovieInfo;

import java.util.ArrayList;
import java.util.List;

public class CommentTotalActivity extends AppCompatActivity implements CommentTotalContract.View {
    //TAG
    final static String TAG = "CommentTotalActivityT";
    //binding
    ActivityCommentTotalBinding binding;
    //requestCode
    final static int WRITE_REQUEST = 11;
    //putExtra key
    final static String COMMENT_LIST_EXTRA = "COMMENT_LIST_EXTRA";
    final static String MOVIEINFO_EXTRA = "MOVIEINFO_EXTRA";
    //value
    private ArrayList<Comment> commentList = new ArrayList<>();
    private CommentAdapter commentAdapter;
    private MovieInfo movieInfo;
    private CommentTotalPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comment_total);
        binding.setActivity(this);
        presenter = new CommentTotalPresenter(this); //프레젠터 생성
        processIntent(); //인텐트처리
        init(); //값 초기화
        presenter.requestCommentList(getApplicationContext(), movieInfo); //댓글요청
    }

    //인텐트 수신 처리
    private void processIntent() {
        Intent intent = getIntent();
        movieInfo =  intent.getParcelableExtra(MOVIEINFO_EXTRA);
        binding.commenttotalTvTitle.setText(movieInfo.getTitle());
        binding.commenttotalRbRating.setRating(movieInfo.getUser_rating());
        binding.commenttotalTvScore.setText(movieInfo.getUser_rating() * 2 + "");
  //      binding.commenttotalTvParticipation.setText(commentList.size()+"");
        if (movieInfo.getGrade() == 12) {
            binding.commenttotalIvGrade.setImageResource(R.drawable.ic_12);
        } else if (movieInfo.getGrade() == 15) {
            binding.commenttotalIvGrade.setImageResource(R.drawable.ic_15);
        } else if (movieInfo.getGrade() == 19) {
            binding.commenttotalIvGrade.setImageResource(R.drawable.ic_19);
        }
    }

    private void init(){
        //어댑터 아이템클릭리스너
        CommentItemClickListener commentItemClickListener = new CommentItemClickListener() {
            @Override
            public void onClickItem(Comment comment) {
                presenter.requestCommentRecommend(getApplicationContext(), comment);
            }
        };
        commentAdapter = new CommentAdapter(getApplicationContext(), commentList, commentItemClickListener);
        binding.commenttotalLivCommentlist.setAdapter(commentAdapter);//리스트뷰 어댑터연결
    }


    //작성하기클릭
    public void writeOnClick(View view) {
        onToastMessage("작성하기");
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
            presenter.requestCommentList(getApplicationContext(), movieInfo); //댓글 다시 받아온다.
        }
    }

    @Override
    public void onToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetCommentListResult(List<Comment> commentList) {
        this.commentList.clear();
        commentAdapter.clear();
        this.commentList.addAll(commentList);
        commentAdapter.notifyDataSetChanged();
        binding.commenttotalTvParticipation.setText(commentList.size()+"");
    }

    @Override
    public void onGetCommentRecommendResult(Comment comment) {
        comment.setRecommend(comment.getRecommend() + 1);
        commentAdapter.notifyDataSetChanged();
    }
}
