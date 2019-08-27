package com.example.boostcourseaceproject4.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.boostcourseaceproject4.R;
import com.example.boostcourseaceproject4.activity.PhotoViewActivity;
import com.example.boostcourseaceproject4.databinding.ItemPhotovideoBinding;
import com.example.boostcourseaceproject4.model.PhotoVideo;
import com.example.boostcourseaceproject4.utils.NetworkStatusHelper;

import java.util.ArrayList;

public class PhotoVideoAdapter extends RecyclerView.Adapter<PhotoVideoAdapter.PhotoVideoViewHolder> {
    public static final String EXTRA_PHOTOVIEW = "EXTRA_PHOTOVIEW";
    Context context;
    ArrayList<PhotoVideo> items = new ArrayList<PhotoVideo>();

    public PhotoVideoAdapter(ArrayList<PhotoVideo> items, Context context) {
        this.context = context;
        addItems(items);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //아이템을 추가
    public void addItem(PhotoVideo item) {
        items.add(item);
    }


    public void addItems(ArrayList<PhotoVideo> items) {
        this.items = items;
    }

    //아이템 전부 삭제
    public void clear() {
        items.clear();
    }

    @NonNull
    @Override
    public PhotoVideoAdapter.PhotoVideoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_photovideo, viewGroup, false);
        return new PhotoVideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoVideoAdapter.PhotoVideoViewHolder holder, int i) {
        final PhotoVideo model = items.get(i);
        if (model.getType() == 0) { //사진
            //TODO:: xml이미지뷰에 app:imageUrl="@{item.url}" 를 해서 했었으나 비디오쪽에서는 파싱을해서 썸네일을 띄어야하는데 그냥 모델의 url이 적용되서 안띄어져서 못함
          //  holder.binding.setItem(model);
            holder.binding.photovideoIvPlayicon.setVisibility(View.GONE);
            Glide.with(context).load(model.getUrl()).into(holder.binding.photovideoIvPhotovideo);

        } else if (model.getType() == 1) { //비디오
            String id = model.getUrl().substring(model.getUrl().lastIndexOf("/") + 1);  //맨마지막 '/'뒤에 id가있으므로 그것만 파싱해줌
            String url = "https://img.youtube.com/vi/" + id + "/" + "default.jpg";  //유튜브 썸네일 불러오는 방법
            holder.binding.photovideoIvPlayicon.setVisibility(View.VISIBLE);
            Glide.with(context).load(url).into(holder.binding.photovideoIvPhotovideo);
        }
    }

    public class PhotoVideoViewHolder extends RecyclerView.ViewHolder {
        ItemPhotovideoBinding binding;

        public PhotoVideoViewHolder(@NonNull final View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.photovideoIvPhotovideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (NetworkStatusHelper.getConnectivityStatus(context)) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            PhotoVideo item = items.get(position);
                            if (item.getType() == 0) {
                                Toast.makeText(context, "사진", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(context, PhotoViewActivity.class);
                                intent.putExtra(EXTRA_PHOTOVIEW, item.getUrl());
                                context.startActivity(intent);
                            } else {
                                Toast.makeText(context, "동영상", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getUrl()));
                                context.startActivity(intent);
                            }
                        }
                    } else {
                        Toast.makeText(context, "네트워크 연결이 필요합니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

 /*   @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }*/
}

