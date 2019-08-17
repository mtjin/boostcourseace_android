package com.example.boostcourseaceproject4.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.boostcourseaceproject4.activity.MainActivity;
import com.example.boostcourseaceproject4.R;
import com.example.boostcourseaceproject4.activity.CommentTotalActivity;
import com.example.boostcourseaceproject4.activity.CommentWriteActivity;
import com.example.boostcourseaceproject4.adapter.CommentAdapter;
import com.example.boostcourseaceproject4.databinding.FragmentMovieInfoBinding;
import com.example.boostcourseaceproject4.model.Comment;
import com.example.boostcourseaceproject4.model.MovieInfo;
import com.example.boostcourseaceproject4.utils.NetworkRequestHelper;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//영화상세정보 프래그먼트 (지금은 군도만띄워지게 해놨지만 추후 프로젝트에서 영화에대한 정보가 나오면 번들로 값을 받아 해당영화를 띄워줄 계획이다.)
public class MovieInfoFragment extends Fragment {
    //TAG
    final static String TAG = "MovieInfoActivityT";
    //binding
    FragmentMovieInfoBinding layout;
    //value
    private int movieId;
    private boolean likeState = false;
    private boolean disLikeState = false;
    private int likeCount = 0;
    private int disLikeCount = 0;
    private boolean likeUp = false;   //좋아요 싫어요 적용여부에 따른 서버 전송 및 저장을 위해 사용되는 변수
    private boolean likeCancel = false;
    private boolean dislikeUp = false;
    private boolean dislikeCancel = false;
    private ArrayList<Comment> commentArrayList = new ArrayList<>();
    private CommentAdapter commentAdapter;
    private MovieInfo movieInfo;
    //requestCode
    final static int WRITE_REQUEST = 11;
    final static int TOTAL_REQUEST = 12;
    //putExtra key
    final static String COMMENT_LIST_EXTRA = "COMMENT_LIST_EXTRA";
    final static String MOVIEINFO_EXTRA = "MOVIEINFO_EXTRA";
    final static String MOVIEID_EXTRA = "MOVIEID_EXTRA";

    public MovieInfoFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_info, container, false);
        layout.setFragment(this); //위와같이 정의한것을 세팅해줌(가져와줌)
        //스크롤뷰안의 리스트뷰 스크롤은  android:nestedScrollingEnabled ="true"로 할 수 있으나 API21이전 기기에서는 작동을 안하므로 다음 코드를 추가해주었다.
        ViewCompat.setNestedScrollingEnabled(layout.movieinfoLivComments, true);
        //어댑터생성
        commentAdapter = new CommentAdapter(commentArrayList, getActivity());
        layout.movieinfoLivComments.setAdapter(commentAdapter);//리스트뷰 어댑터연결
        //전달받은 인텐트처리
        processBundle();
        //영화상세정보 요청
        requestMovieInfo();
        //댓글요청
        requestComment();
        return layout.getRoot();
    }

    //뷰초기화
    private void initView() {
        Glide.with(this).load(movieInfo.getThumb()).into(layout.movieinfoIvPoster);
        layout.movieinfoTvTitle.setText(movieInfo.getTitle());
        layout.movieinfoTvDates.setText(movieInfo.getDate());
        layout.movieinfoTvGenere.setText(movieInfo.getGenre());
        layout.movieinfoTvRunningtime.setText(movieInfo.getDuration() + "");
        layout.movieinfoTvRank.setText(movieInfo.getReservation_grade() + "");
        layout.movieinfoTvShare.setText(movieInfo.getReservation_rate() + ""); //예매율
        layout.movieinfoRbRating.setRating((float) movieInfo.getUser_rating());
        layout.movieinfoTvRatingscore.setText(movieInfo.getUser_rating() * 2 + "");
        layout.movieinfoTvAttendance.setText(movieInfo.getAudience() + "");
        layout.movieinfoTvContent.setText(movieInfo.getSynopsis());
        layout.movieinfoTvDirector.setText(movieInfo.getDirector());
        layout.movieinfoTvActor.setText(movieInfo.getActor());
        layout.movieinfoTvLikecount.setText(movieInfo.getLike() + "");
        layout.movieinfoTvDislikecount.setText(movieInfo.getDislike() + "");
        likeCount = movieInfo.getLike();
        disLikeCount = movieInfo.getDislike();
        MainActivity.toolbar.setTitle("영화 상세");
        //댓글 서버 요청
        requestComment();
    }

    //전달받은 번들 값 처리
    private void processBundle() {
        Bundle bundle = getArguments();
        movieId = bundle.getInt(MOVIEID_EXTRA, -1);
        Log.d(TAG , "전달받은 영화 아이디 => " + movieId);
    }

    //좋아요클릭
    public void onLikeClick(View view) {
        if (likeState) {//좋아요 취소
            likeCount -= 1;
            layout.movieinfoTvLikecount.setText(String.valueOf(likeCount));
            layout.movieinfoIbtnLikeup.setBackgroundResource(R.drawable.selector_thumbs_up);
            likeUp = false;   //좋아요 싫어요 적용여부에 따른 서버 전송 및 저장을 위해 사용되는 플래그
            likeCancel = true;
            dislikeUp = false;
            dislikeCancel = false;
        } else {  //좋아요
            if (disLikeState) { //싫어요 눌러져있는 경우 취소하고 좋아요
                disLikeCount -= 1;
                layout.movieinfoTvDislikecount.setText(String.valueOf(disLikeCount));
                layout.movieinfoIbtnDislikeup.setBackgroundResource(R.drawable.selector_thumbs_down);
                disLikeState = false;
            }
            likeCount += 1;
            layout.movieinfoTvLikecount.setText(String.valueOf(likeCount));
            layout.movieinfoIbtnLikeup.setBackgroundResource(R.drawable.ic_thumb_up_selected);
            likeUp = true;   //좋아요 싫어요 적용여부에 따른 서버 전송 및 저장을 위해 사용되는 플래그
            likeCancel = false;
            dislikeUp = false;
            dislikeCancel = false;

        }
        likeState = !likeState;
        sendLikeRequest();
    }

    //싫어요클릭
    public void onDislikeClick(View view) {
        if (disLikeState) { //싫어요 취소
            disLikeCount -= 1;
            layout.movieinfoTvDislikecount.setText(String.valueOf(disLikeCount));
            layout.movieinfoIbtnDislikeup.setBackgroundResource(R.drawable.selector_thumbs_down);
            likeUp = false;   //좋아요 싫어요 적용여부에 따른 서버 전송 및 저장을 위해 사용되는 플래그
            likeCancel = false;
            dislikeUp = false;
            dislikeCancel = true;
        } else { //싫어요
            if (likeState) { //좋아요 눌러져있던 경우 취소하고 싫어요
                likeCount -= 1;
                layout.movieinfoTvLikecount.setText(String.valueOf(likeCount));
                layout.movieinfoIbtnLikeup.setBackgroundResource(R.drawable.selector_thumbs_up);
                likeState = false;
            }
            disLikeCount += 1;
            layout.movieinfoTvDislikecount.setText(String.valueOf(disLikeCount));
            layout.movieinfoIbtnDislikeup.setBackgroundResource(R.drawable.ic_thumb_down_selected);
            likeUp = false;   //좋아요 싫어요 적용여부에 따른 서버 전송 및 저장을 위해 사용되는 플래그
            likeCancel = false;
            dislikeUp = true;
            dislikeCancel = false;
        }
        disLikeState = !disLikeState;
        sendLikeRequest();
    }


    //댓글모두보기 버튼 클릭
    public void onAllViewClick(View view) {
     //   Toast.makeText(getActivity(), "모두보기", Toast.LENGTH_SHORT).show();
        Intent totalCommentIntent = new Intent(getActivity(), CommentTotalActivity.class);
    //    totalCommentIntent.putExtra(COMMENT_LIST_EXTRA, commentArrayList); //댓글리스트 전달
        totalCommentIntent.putExtra(MOVIEINFO_EXTRA, movieInfo); //영화 정보전달(뷰 세팅과 id값 하는데 사용)
        startActivityForResult(totalCommentIntent, TOTAL_REQUEST);
    }

    //작성하기 버튼 클릭
    public void onWriteClick(View view) {
        Toast.makeText(getActivity(), "작성하기", Toast.LENGTH_SHORT).show();
        Intent writeIntent = new Intent(getActivity(), CommentWriteActivity.class);
        writeIntent.putExtra(MOVIEINFO_EXTRA, movieInfo);
        startActivityForResult(writeIntent, WRITE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WRITE_REQUEST && resultCode == getActivity().RESULT_OK) {
            requestComment(); //댓글요청
        } else if (requestCode == TOTAL_REQUEST && resultCode == getActivity().RESULT_OK) { //댓글전체보기 결과
            requestComment();
        }
    }

    //좋아요, 싫어요 서버에 전송 및 저장
    private void sendLikeRequest() {
        Log.d(TAG, "좋아요, 싫어요 서버에 전송 및 저장");
        String url = "http://" + NetworkRequestHelper.host + ":" + NetworkRequestHelper.port + "/movie/increaseLikeDisLike";
        Log.d(TAG, "좋아요싫어요 url : " + url);
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
                params.put("id", movieInfo.getId() + "");
                Log.d(TAG, "영화 아이디 : " + movieInfo.getId());
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
        NetworkRequestHelper.requestQueue.add(request);
    }

    //id값에 해당하는 영화 상세정보 요청
    public void requestMovieInfo() {
        if (movieId == -1) {
            Toast.makeText(getContext(), "영화정보 아이디를 불러오지 못했습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
        } else {
            String url = "http://" + NetworkRequestHelper.host + ":" + NetworkRequestHelper.port + "/movie/readMovie?id=";
            url += movieId; //파리미터도 추가해줌

            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            processMovieInfoResponse(response);
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
            NetworkRequestHelper.requestQueue.add(request);
        }
    }

    //영화상세정보 요청 응답
    public void processMovieInfoResponse(String response) {
        Gson gson = new Gson();
        MovieInfo movieInfo = gson.fromJson(response, MovieInfo.class);
        if (movieInfo.code == 200) { //코드가 200과 같다면 result라는거안에 데이터가 들어가있다는것을 확신할 수 있음
            this.movieInfo = movieInfo.result.get(0);
            initView(); //뷰초기화
        }
    }

    //댓글 불러오기 요청
    private void requestComment() {
        if (movieId == -1) {
            Toast.makeText(getContext(), "영화정보 아이디를 불러오지 못했습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
        } else {
            String url = "http://" + NetworkRequestHelper.host + ":" + NetworkRequestHelper.port +
                    "/movie/readCommentList?id=";
            url += movieId + "&length=" + Integer.MAX_VALUE; //파리미터도 추가해줌(최대개수를 불러옴)
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
                            Log.d("에러(영화상세정보 댓글)", "요청못받음");
                            Log.d("에러(영화상세정보 댓글)", error + "");
                        }
                    }
            );
            request.setShouldCache(false);
            NetworkRequestHelper.requestQueue.add(request);//리퀘스트큐에 넣으면 리퀘스트큐가 알아서 스레드로 서버에 요청해주고 응답가져옴
        }
    }

    //댓글 요청응답
    private void processCommentResponse(String response) {
        Gson gson = new Gson();
        Comment comment = gson.fromJson(response, Comment.class);
        if (comment.code == 200) { //코드가 200과 같다면 result라는거안에 데이터가 들어가있다는것을 확신할 수 있음
            commentArrayList.clear();
            commentAdapter.clear();
            commentArrayList.addAll(comment.result); //comment.result타입 => ArrayList<Comment>
            commentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainActivity.toolbar.setTitle("영화 목록");
    }
}
