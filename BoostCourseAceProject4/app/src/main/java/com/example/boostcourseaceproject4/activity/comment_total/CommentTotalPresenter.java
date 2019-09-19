package com.example.boostcourseaceproject4.activity.comment_total;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.boostcourseaceproject4.database.AppDatabase;
import com.example.boostcourseaceproject4.model.Comment;
import com.example.boostcourseaceproject4.model.MovieInfo;
import com.example.boostcourseaceproject4.api.NetworkManager;
import com.example.boostcourseaceproject4.utils.NetworkStatusHelper;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommentTotalPresenter implements CommentTotalContract.Presenter {
    private CommentTotalContract.View view;
    public final static String TAG = "CommentTotalPresenterT";

    public CommentTotalPresenter(CommentTotalContract.View view) {
        this.view = view;
    }


    @Override
    public void requestCommentList(Context context, MovieInfo movieInfo) {
        if (NetworkStatusHelper.getConnectivityStatus(context)) {
            String url = "http://" + NetworkManager.host + ":" + NetworkManager.port +
                    "/movie/readCommentList?id=";
            url += movieInfo.getId() + "&length=" + 300;
            Log.d(TAG, url + "");
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Gson gson = new Gson();
                            Comment comment = gson.fromJson(response, Comment.class);
                            if (comment.code == 200) { //코드가 200과 같다면 result라는거안에 데이터가 들어가있다는것을 확신할 수 있음
                                AppDatabase.insertComment(comment.result);  //TODO:: Repository만들어서 데이터베이스에 넣는다고하는데 시간상 나중에 보기로한다.
                                view.onToastMessage("서버로부터 댓글을 불러왔습니다.");
                                view.onGetCommentListResult(comment.result);
                            }
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
            NetworkManager.requestQueue.add(request);//리퀘스트큐에 넣으면 리퀘스트큐가 알아서 스레드로 서버에 요청해주고 응답가져옴
        }else{
            ArrayList<Comment> result = AppDatabase.selectCommentData(movieInfo.getId());
            view.onToastMessage("DB 로부터 댓글을 불러왔습니다.");
            view.onGetCommentListResult(result);
        }
    }



    @Override
    public void requestCommentRecommend(Context context, final Comment comment) {
        if (NetworkStatusHelper.getConnectivityStatus(context)) {//인터넷 연결된 경우

            String url = "http://" + NetworkManager.host + ":" + NetworkManager.port + "/movie/increaseRecommend";
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    url,
                    new Response.Listener<String>() {  //응답을 문자열로 받아서 여기다 넣어달란말임(응답을 성공적으로 받았을 떄 이메소드가 자동으로 호출됨
                        @Override
                        public void onResponse(String response) {
                            view.onGetCommentRecommendResult(comment);
                        }
                    },
                    new Response.ErrorListener() { //에러발생시 호출될 리스너 객체
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("review_id", comment.getId() + "");
                    params.put("writer", "이이22");
                    return params;
                }
            };
            request.setShouldCache(false);
            NetworkManager.requestQueue.add(request);
        } else {
            view.onToastMessage("인터넷이 끊켜있습니다.");
        }
    }
}
