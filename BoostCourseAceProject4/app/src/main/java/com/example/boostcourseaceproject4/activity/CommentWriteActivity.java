package com.example.boostcourseaceproject4.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.boostcourseaceproject4.R;
import com.example.boostcourseaceproject4.databinding.ActivityCommentWriteBinding;
import com.example.boostcourseaceproject4.model.Comment;
import com.example.boostcourseaceproject4.model.MovieInfo;
import com.example.boostcourseaceproject4.utils.NetworkHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class CommentWriteActivity extends AppCompatActivity {
    ActivityCommentWriteBinding binding;
    //putExtra key
    final static String MOVIEINFO_EXTRA = "MOVIEINFO_EXTRA";
    final static String COMMENT_EXTRA = "COMMENT_EXTRA";
    //value
    int id; //영화 아이디
    MovieInfo movieInfo;
    Comment comment;
    //날짜포맷
    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comment_write);
        binding.setActivity(this);
        //인텐트처리
        processIntent();
    }

    //저장버튼클릭
    public void onOkClick(View view) {
        String message = binding.commentwriteEtWrite.getText().toString().trim();
        float rating = binding.commentwriteRbRating.getRating();
        if (message.length() <= 0) {
            Toast.makeText(this, "한글자 이상 적어주세요", Toast.LENGTH_SHORT).show();
        } else {
            //작성시간 put
            Calendar time = Calendar.getInstance();
            String format_time1 = format1.format(time.getTime());
            comment = new Comment(-1, "이이22", id, format_time1, rating, message, 0);
            sendWriteRequest();
            Intent intent = new Intent();
            intent.putExtra(COMMENT_EXTRA, comment);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private void processIntent() {
        Intent resultIntent = getIntent();
        movieInfo = (MovieInfo) resultIntent.getParcelableExtra(MOVIEINFO_EXTRA);
        if (resultIntent != null) {
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
    }

    public void sendWriteRequest() {
        String url = "http://" + NetworkHelper.host + ":" + NetworkHelper.port + "/movie/createComment";

        //StringRequest를 만듬 (파라미터구분을 쉽게하기위해 엔터를 쳐서 구분하면 좋다)
        //StringRequest는 요청객체중 하나이며 가장 많이 쓰인다고한다.
        //요청객체는 다음고 같이 보내는방식(GET,POST), URL, 응답성공리스너, 응답실패리스너 이렇게 4개의 파라미터를 전달할 수 있다.(리퀘스트큐에 )
        //화면에 결과를 표시할때 핸들러를 사용하지 않아도되는 장점이있다.
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                },
                new Response.ErrorListener() { //에러발생시 호출될 리스너 객체
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ) {
            //만약 POST 방식에서 전달할 요청 파라미터가 있다면 getParams 메소드에서 반환하는 HashMap 객체에 넣어줍니다.
            //이렇게 만든 요청 객체는 요청 큐에 넣어주는 것만 해주면 됩니다.
            //POST방식으로 안할거면 없어도 되는거같다.
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id + "");
                params.put("writer", "이이22");
                params.put("rating", binding.commentwriteRbRating.getRating() + "");
                params.put("contents", binding.commentwriteEtWrite.getText() + "");
                return params;
            }
        };
        request.setShouldCache(false);
        NetworkHelper.requestQueue.add(request);

    }

    //취소버튼클릭
    public void onCancelClick(View view) {
        finish();
    }

}
