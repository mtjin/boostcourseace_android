package com.example.boostcourseaceproject4.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.boostcourseaceproject4.R;
import com.example.boostcourseaceproject4.interfaces.CommentItemClickListener;
import com.example.boostcourseaceproject4.model.Comment;
import com.example.boostcourseaceproject4.modelview.CommentView;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Comment> items;
    private CommentItemClickListener itemClickListener;

    public CommentAdapter(Context context, ArrayList<Comment> items, CommentItemClickListener itemClickListener) {
        this.context = context;
        this.items = items;
        this.itemClickListener = itemClickListener;
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
                itemClickListener.onClickItem(item);
            }
        });
        return view;
    }

}
