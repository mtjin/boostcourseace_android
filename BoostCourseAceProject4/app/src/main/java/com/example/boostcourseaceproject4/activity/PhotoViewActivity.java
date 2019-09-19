package com.example.boostcourseaceproject4.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.boostcourseaceproject4.R;
import com.github.chrisbanes.photoview.PhotoView;

public class PhotoViewActivity extends AppCompatActivity {
    public static final String EXTRA_PHOTOVIEW = "EXTRA_PHOTOVIEW";
    PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        setTitle("사진 보기");

        photoView = findViewById(R.id.photoview_iv_photoview);
        //MovieInfoFragment에서부터 인텐트 받음
        processIntent();
    }

    private void processIntent() {
        Intent intent = getIntent();
        //사진 포토뷰 라이브러리 사용해서 확대 및 축소 가능하게 보여줌
        String url = intent.getStringExtra(EXTRA_PHOTOVIEW);
        Glide.with(this).load(url).into(photoView);
    }
}
