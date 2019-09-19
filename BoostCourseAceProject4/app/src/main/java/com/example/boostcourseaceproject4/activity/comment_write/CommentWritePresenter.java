package com.example.boostcourseaceproject4.activity.comment_write;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.boostcourseaceproject4.api.NetworkManager;

import java.util.HashMap;
import java.util.Map;

public class CommentWritePresenter implements CommentWriteContract.Presenter {
    private CommentWriteContract.View view;

    public CommentWritePresenter(CommentWriteContract.View view) {
        this.view = view;
    }


    @Override
    public void requestWriteComment(final int movieId, final String writer, final float score, final String content) {
        String url = "http://" + NetworkManager.host + ":" + NetworkManager.port + "/movie/createComment";
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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", movieId + "");
                params.put("writer", writer);
                params.put("rating", score+ "");
                params.put("contents", content);
                return params;
            }
        };
        request.setShouldCache(false);
        NetworkManager.requestQueue.add(request);
    }
}
