package com.example.boostcourseaceproject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {
    ArrayList<Comment> items;
    Context context;

    public CommentAdapter(ArrayList<Comment> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public void addItem(Comment item){
        items.add(item);
    }

    public void clear(){
        items.clear();
    }

    public void addAll(ArrayList<Comment> items){
        this.items = items;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentView view = null;
        if (convertView == null) {
            view = new CommentView(context);
        } else {
            view = (CommentView) convertView;
        }
        Comment item = items.get(position);
        view.setPhotoImageView(item.getPhoto());
        view.setNickNameTextView(item.getNickName());
        view.setCommentTextView(item.getComment());
        view.setRatingBar(item.getRatingScore());
        view.setTimeTextView(item.getTime());
        view.setRecommendCountTextView(item.getRecommend());
        return  view;
    }
}
