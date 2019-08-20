package com.example.boostcourseaceproject4.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.boostcourseaceproject4.adapter.CommentAdapter;
import com.example.boostcourseaceproject4.R;
import com.example.boostcourseaceproject4.databinding.ActivityCommentTotalBinding;
import com.example.boostcourseaceproject4.db.AppDatabase;
import com.example.boostcourseaceproject4.model.Comment;
import com.example.boostcourseaceproject4.model.MovieInfo;
import com.example.boostcourseaceproject4.utils.NetworkRequestHelper;
import com.example.boostcourseaceproject4.utils.NetworkStatusHelper;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CommentTotalActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comment_total);
        binding.setActivity(this);
        //인텐트처리
        processIntent();
        init(); //값 초기화
        requestComment();
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
        commentAdapter = new CommentAdapter(commentList, getApplicationContext());
        binding.commenttotalLivCommentlist.setAdapter(commentAdapter);//리스트뷰 어댑터연결
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
            requestComment(); //댓글 다시 받아온다.
        }
    }

    //댓글 불러오기 요청
    private void requestComment() {
        if (NetworkStatusHelper.getConnectivityStatus(getApplicationContext())) {
            String url = "http://" + NetworkRequestHelper.host + ":" + NetworkRequestHelper.port +
                    "/movie/readCommentList?id=";
            url += movieInfo.getId() + "&length=" + 300;
            Log.d(TAG, url + "");
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            processCommentResponse(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "요청못받음");
                            Log.d(TAG, error + "");
                        }
                    }
            );
            request.setShouldCache(false);
            NetworkRequestHelper.requestQueue.add(request);//리퀘스트큐에 넣으면 리퀘스트큐가 알아서 스레드로 서버에 요청해주고 응답가져옴
        }else{
            String commentJson = AppDatabase.selectCommentJsonData(movieInfo.getId());
            processCommentResponse(commentJson);
            Toast.makeText(this, "DB로부터 댓글을 불러왔습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    //댓글 요청응답
    private void processCommentResponse(String response) {
        Gson gson = new Gson();
        Comment comment = gson.fromJson(response, Comment.class);
        if (comment.code == 200) { //코드가 200과 같다면 result라는거안에 데이터가 들어가있다는것을 확신할 수 있음
            commentList.clear();
            commentAdapter.clear();
            commentList.addAll(comment.result); //comment.result타입 => ArrayList<Comment>
            commentAdapter.notifyDataSetChanged();
            binding.commenttotalTvParticipation.setText(commentList.size()+"");
            AppDatabase.insertCommentJson(movieInfo.getId(), response);
        }
    }

    //TODO:현재 서버에서 배열로 오는 값을 json 그대로 DB에 저장합니다만,
    //
    //이 경우 총 개수를 알수 없으므로
    //배열값 각각을 하나의 record형태로 저장하는것이 좋습니다.
}
