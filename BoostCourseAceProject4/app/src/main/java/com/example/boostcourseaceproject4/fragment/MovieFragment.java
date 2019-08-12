package com.example.boostcourseaceproject4.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.boostcourseaceproject4.R;
import com.example.boostcourseaceproject4.interfaces.MovieFragmentListener;
import com.example.boostcourseaceproject4.model.Movie;
import com.example.boostcourseaceproject4.model.MovieInfo;
import com.example.boostcourseaceproject4.model.MovieInfoList;
import com.example.boostcourseaceproject4.model.ResponseMovieInfo;
import com.example.boostcourseaceproject4.utils.NetworkHelper;
import com.google.gson.Gson;

//영화 군도
public class MovieFragment extends Fragment {
    //xml
    private Button detailButton; //상세보기 버튼
    private TextView movieNumTextView; //영화번호
    private TextView titleTextView; //영화제목
    private TextView reservedRateTextView; //예매율
    private TextView gradeTextView; //관람등급
    private ImageView posterImageView;
    //value
    private Movie movie;
    private MovieFragmentListener movieFragmentListener;
    //key
    final static String MOVIE_EXTRA = "MOVIE_EXTRA";
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof MovieFragmentListener){
            movieFragmentListener = (MovieFragmentListener) context;
        }
    }
    public MovieFragment() {

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
                movieFragmentListener.onDetailButtonClicked(movie.getId());
            }
        });
        init();
        return rootView;
    }

    //값 초기화 및 세팅
    public void init() {
        Bundle bundle = getArguments();
        movie = (Movie) bundle.getSerializable(MOVIE_EXTRA);

        movieNumTextView.setText(movie.getId() + "");
        titleTextView.setText(movie.getTitle());
        reservedRateTextView.setText(movie.getReservation_rate() + "");
        gradeTextView.setText(movie.getGrade() + "");
        if (movie.getImage() != null) {
            Glide.with(getActivity()).load(movie.getImage()).into(posterImageView);
        }
    }
}
