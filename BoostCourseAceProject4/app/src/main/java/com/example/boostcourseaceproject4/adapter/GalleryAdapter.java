package com.example.boostcourseaceproject4.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.boostcourseaceproject4.R;
import com.example.boostcourseaceproject4.activity.PhotoViewActivity;
import com.example.boostcourseaceproject4.databinding.ItemPhotovideoBinding;
import com.example.boostcourseaceproject4.model.Gallery;
import com.example.boostcourseaceproject4.utils.NetworkStatusHelper;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {
    private Context context;
    private ArrayList<Gallery> items;
    private GalleryItemClickListener itemClickListener;

    public GalleryAdapter(Context context, ArrayList<Gallery> items, GalleryItemClickListener itemClickListener) {
        this.context = context;
        this.items = items;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //아이템을 추가
    public void addItem(Gallery item) {
        items.add(item);
    }


    public void addItems(ArrayList<Gallery> items) {
        this.items = items;
    }

    //아이템 전부 삭제
    public void clear() {
        items.clear();
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_photovideo, viewGroup, false);
        return new GalleryViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int i) {
        final Gallery model = items.get(i);
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

    public class GalleryViewHolder extends RecyclerView.ViewHolder implements GalleryItemClickListener, View.OnClickListener {
        ItemPhotovideoBinding binding;
        GalleryItemClickListener itemClickListener;

        GalleryViewHolder(@NonNull final View itemView, GalleryItemClickListener itemClickListener) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            this.itemClickListener = itemClickListener;
            assert binding != null;
            binding.photovideoIvPhotovideo.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view, getAdapterPosition());
        }

        @Override
        public void onItemClick(View view, int position) {

        }
    }

    public interface GalleryItemClickListener {
        void onItemClick(View view, int position);
    }

 /*   @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }*/
}

