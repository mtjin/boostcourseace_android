package com.example.boostcourseaceproject4.fragment.movie_info;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
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

public class MovieInfoPresenter implements MovieInfoContract.Presenter {
    private MovieInfoContract.View view;
    public final static String TAG = "MovieInfoPresenterT";

    public MovieInfoPresenter(MovieInfoContract.View view) {
        this.view = view;
    }

    @Override //댓글 요청
    public void requestCommentList(Context context, int movieId) {
        if (NetworkStatusHelper.getConnectivityStatus(context)) {
            String url = "http://" + NetworkManager.host + ":" + NetworkManager.port +
                    "/movie/readCommentList?id=";
            url += movieId + "&length=" + 300; //읽어올 개수
            Log.d(TAG, url + "");
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            responseCommentList(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("에러(영화상세정보 댓글)", "요청못받음");
                            Log.d("에러(영화상세정보 댓글)", error + "");
                        }
                    }
            );
            request.setShouldCache(false);
            NetworkManager.requestQueue.add(request);//리퀘스트큐에 넣으면 리퀘스트큐가 알아서 스레드로 서버에 요청해주고 응답가져옴
        } else {
            ArrayList<Comment> result = AppDatabase.selectCommentData(movieId);
            view.onToastMessage("DB로부터 댓글을 불러왔습니다.");
            view.onGetCommentListResult(result);
        }
    }

    @Override //댓글 요청 응답
    public void responseCommentList(String response) {
        Gson gson = new Gson();
        Comment comment = gson.fromJson(response, Comment.class);
        if (comment.code == 200) { //코드가 200과 같다면 result라는거안에 데이터가 들어가있다는것을 확신할 수 있음
            view.onGetCommentListResult(comment.result);
            //디비삽입
            AppDatabase.insertComment(comment.result);//TODO:: Repository만들어서 데이터베이스에 넣는다고하는데 시간상 나중에 보기로한다.
        }
    }

    @Override //영화상세정보 요청
    public void requestMovieInfo(Context context, final int movieId) {
        if (NetworkStatusHelper.getConnectivityStatus(context)) { //인터넷 연결 안되있을 경우
            String url = "http://" + NetworkManager.host + ":" + NetworkManager.port + "/movie/readMovie?id=";
            url += movieId; //파리미터도 추가해줌

            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            responseMovieInfo(response, movieId);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("TAG", "영화 상세정보 요청못받음");
                        }
                    }
            );
            request.setShouldCache(false);
            NetworkManager.requestQueue.add(request);
        } else {
            String movieInfoJson = AppDatabase.selectMovieInfoJsonData(movieId);
            responseMovieInfo(movieInfoJson, movieId);
            view.onToastMessage("DB로부터 영화 상세정보 불러왔습니다.");
        }
    }

    @Override //영화상세정보 응답
    public void responseMovieInfo(String response, int movieId) {
        Gson gson = new Gson();
        MovieInfo movieInfo = gson.fromJson(response, MovieInfo.class);
        if (movieInfo.code == 200) { //코드가 200과 같다면 result라는거안에 데이터가 들어가있다는것을 확신할 수 있음
            view.onGetMovieInfoResult(movieInfo.result.get(0));
            AppDatabase.insertMovieInfoJson(movieId, response);
        }
    }

    @Override
    public void requestMovieLike(final int movieId, final boolean likeCancel, final boolean likeUp, final boolean dislikeUp, final boolean dislikeCancel) {
        String url = "http://" + NetworkManager.host + ":" + NetworkManager.port + "/movie/increaseLikeDisLike";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {  //응답을 문자열로 받아서 여기다 넣어달란말임(응답을 성공적으로 받았을 떄 이메소드가 자동으로 호출됨
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "좋아요싫어요 응답 성공");
                    }
                },
                new Response.ErrorListener() { //에러발생시 호출될 리스너 객체
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "좋아요싫어요 응답 에러");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", movieId + "");
                Log.d(TAG, "영화 아이디 : " + movieId);
                if (likeCancel) {
                    params.put("likeyn", "N");
                    Log.i(TAG, "좋아요 취소");
                } else if (likeUp) {
                    params.put("likeyn", "Y");
                    Log.i(TAG, "좋아요");
                } else if (dislikeUp) {
                    params.put("dislikeyn", "Y");
                    Log.i(TAG, "싫어요");
                } else if (dislikeCancel) {
                    params.put("dislikeyn", "N");
                    Log.i(TAG, "싫어요 취소");
                }
                return params;
            }
        };

        request.setShouldCache(false);
        NetworkManager.requestQueue.add(request);
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
