package com.example.boostcourseaceproject4.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.boostcourseaceproject4.R;
import com.example.boostcourseaceproject4.databinding.FragmentMovieBinding;
import com.example.boostcourseaceproject4.interfaces.MovieFragmentListener;
import com.example.boostcourseaceproject4.model.Movie;

//영화 군도
public class MovieFragment extends Fragment {
    //binding
    FragmentMovieBinding layout;
    //value
    private Movie movie;
    private MovieFragmentListener movieFragmentListener; //영화자세히보기 리스너
    //key
    final static String MOVIE_EXTRA = "MOVIE_EXTRA";

    public MovieFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof MovieFragmentListener){
            movieFragmentListener = (MovieFragmentListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false);
        layout.setFragment(this); //위와같이 정의한것을 세팅해줌(가져와줌)
        init();
        return layout.getRoot();
    }

    //영화 자세히보기 버튼 클릭 이벤트
    public void onDetatilViewClick(View view){
        Log.d("DDDD" , movie.getId()+"");
        movieFragmentListener.onDetailButtonClicked(movie.getId());
    }

    //값 초기화 및 세팅
    public void init() {
        Bundle bundle = getArguments();
        movie = (Movie) bundle.getParcelable(MOVIE_EXTRA);
        layout.movieTvNum.setText(movie.getId() + "");
        layout.movieTvTitle.setText(movie.getTitle());
        layout.movieTvReservedrate.setText(movie.getReservation_rate() + "");
        layout.movieTvGrade.setText(movie.getGrade() + "");
        if (movie.getImage() != null) {
            Glide.with(getActivity()).load(movie.getImage()).into(layout.movieIvImage);
        }
    }
}
