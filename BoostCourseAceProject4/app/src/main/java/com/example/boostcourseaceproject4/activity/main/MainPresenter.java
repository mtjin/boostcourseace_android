package com.example.boostcourseaceproject4.activity.main;

import android.content.Context;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.boostcourseaceproject4.database.AppDatabase;
import com.example.boostcourseaceproject4.model.MovieList;
import com.example.boostcourseaceproject4.api.NetworkManager;
import com.example.boostcourseaceproject4.utils.NetworkStatusHelper;
import com.google.gson.Gson;

public class MainPresenter implements MainContract.Presenter{
    final static String TAG = "MainPresenterT";
    private MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    //영화  불러오기 요청
    public void requestMovieList(Context context){
        if(NetworkStatusHelper.getConnectivityStatus(context)) { //인터넷 연결 상태인 경우
            String url = "http://" + NetworkManager.host + ":" + NetworkManager.port + "/movie/readMovieList";
            url += "?" + "type=1"; //파리미터도 추가해줌

            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            responseMovieList(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "영화 요청못받음");
                        }
                    }
            );
            //캐시제거(매번 다시불러오게함)
            request.setShouldCache(false);
            NetworkManager.requestQueue.add(request);//리퀘스트큐에 넣으면 리퀘스트큐가 알아서 스레드로 서버에 요청해주고 응답가져옴
        }else{ //인터넷 연결 끊킨 경우 디비에서 불러오기
            String movieJson = AppDatabase.selectMovieJsonData();
            if(movieJson != null){
                responseMovieList(movieJson);
                view.onToastMessage("DB로부터 영화 불러왔습니다.");
            }
        }
    }

    //영화 불러오기 응답
    public void responseMovieList(String response){
        Gson gson = new Gson();
        //TODO: 리뷰 => json으로 두번 파싱할 필요없이, ResponseMovie를 MovieList가 상속받으면 한번의 파싱으로 해결이 됩니다.
        MovieList movieList  = (MovieList) gson.fromJson(response, MovieList.class);
        if (movieList.code == 200) { //코드가 200과 같다면 result라는거안에 데이터가 들어가있다는것을 확신할 수 있음
            view.onGetMovieListResult(movieList.result);
            //데이터베이스에 해당 json을 넣어준다. TODO:: Repository만들어서 데이터베이스에 넣는다고하는데 시간상 나중에 보기로한다.
            AppDatabase.insertMovieJson(1, response);
        }
    }
}
