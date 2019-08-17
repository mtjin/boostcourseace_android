package com.example.boostcourseaceproject4.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.boostcourseaceproject4.R;
import com.example.boostcourseaceproject4.model.Comment;
import com.example.boostcourseaceproject4.modelview.CommentView;
import com.example.boostcourseaceproject4.utils.NetworkRequestHelper;
import com.example.boostcourseaceproject4.utils.NetworkStatusHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommentAdapter extends BaseAdapter {
    ArrayList<Comment> items;
    Context context;

    public CommentAdapter(ArrayList<Comment> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public void addItem(Comment item) {
        items.add(item);
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<Comment> items) {
        this.items = items;
    }

    public ArrayList<Comment> getAllItem() {
        return items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItemFirst(Comment item) {
        items.add(0, item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentView view = null;
        if (convertView == null) {
            view = new CommentView(context);
        } else {
            view = (CommentView) convertView;
        }
        final Comment item = items.get(position);
        view.setPhotoImageView(R.drawable.user1);
        view.setNickNameTextView(item.getWriter());
        view.setCommentTextView(item.getContents());
        view.setRatingBar(item.getRating());
        view.setTimeTextView(item.getTime() + "");
        view.setRecommendCountTextView(item.getRecommend());

        TextView recommendTextView = view.findViewById(R.id.comment_tv_recommend);
        recommendTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NetworkStatusHelper.getConnectivityStatus(context)) {//인터넷 연결된 경우
                    sendRecommendRequest(item.getId());
                    item.setRecommend(item.getRecommend() + 1);
                    notifyDataSetChanged();
                }else{
                    Toast.makeText(context, "인터넷이 끊켜있습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    //댓글 추천 서버에 전송
    public void sendRecommendRequest(final int id) {
        String url = "http://" + NetworkRequestHelper.host + ":" + NetworkRequestHelper.port + "/movie/increaseRecommend";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {  //응답을 문자열로 받아서 여기다 넣어달란말임(응답을 성공적으로 받았을 떄 이메소드가 자동으로 호출됨
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
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("review_id", id + "");
                params.put("writer", "이이22");
                return params;
            }
        };
        request.setShouldCache(false);
        NetworkRequestHelper.requestQueue.add(request);
    }
}
