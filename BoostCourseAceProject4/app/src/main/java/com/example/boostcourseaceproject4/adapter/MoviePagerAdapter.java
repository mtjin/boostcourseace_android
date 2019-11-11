package com.example.boostcourseaceproject4.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.boostcourseaceproject4.fragment.MovieFragment;

import java.util.ArrayList;

public class MoviePagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<MovieFragment> movies = new ArrayList<MovieFragment>();

    public MoviePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addItem(MovieFragment item) {
        movies.add(item);
    }

    public void addAllItem(ArrayList<MovieFragment> items){
        movies.addAll(items);
    }

    @Override
    public MovieFragment getItem(int position) {
        return movies.get(position);
    }

    public void clear() {
        movies.clear();
    }

    @Override
    public int getCount() {
        return movies.size();
    }

}
