package com.example.boostcourseaceproject4.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.print.PrinterId;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.boostcourseaceproject4.R;
import com.example.boostcourseaceproject4.model.Movie;

//영화 군도
public class MovieFragment extends Fragment {
    private Movie movie;
    private Button detailButton; //상세보기 버튼
    private TextView movieNumTextView; //영화번호
    private TextView titleTextView; //영화제목
    private TextView reservedRateTextView; //예매율
    private TextView gradeTextView; //관람등급
    private ImageView posterImageView;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }



    public MovieFragment(Movie movie){
        this.movie = movie;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_movie, container, false);
        detailButton = rootView.findViewById(R.id.movie_btn_detailview);
        movieNumTextView = rootView.findViewById(R.id.movie_tv_num);
        titleTextView = rootView.findViewById(R.id.movie_tv_title);
        reservedRateTextView = rootView.findViewById(R.id.movie_tv_reservedrate);
        gradeTextView = rootView.findViewById(R.id.movie_tv_grade);
        posterImageView = rootView.findViewById(R.id.movie_iv_image);
        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieInfoFragment movieInfoFragment = new MovieInfoFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().add(R.id.main_container, movieInfoFragment);
                // 해당 transaction 을 Back Stack 에 저장
                transaction.addToBackStack(null);
                // transaction 실행
                transaction.commit();
            }
        });
        init();
        return rootView;
    }

    //값 초기화 및 세팅
    public void init(){
        movieNumTextView.setText(movie.getId()+"");
        titleTextView.setText(movie.getTitle());
        reservedRateTextView.setText(movie.getReservedRate());
        gradeTextView.setText(movie.getGrade());
        posterImageView.setImageResource(movie.getImage());
    }


}
