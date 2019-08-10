package com.example.boostcourseaceproject4.activity;

import android.os.Bundle;


import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.example.boostcourseaceproject4.R;
import com.example.boostcourseaceproject4.adapter.MoviePagerAdapter;
import com.example.boostcourseaceproject4.fragment.MovieFragment;
import com.example.boostcourseaceproject4.fragment.MovieInfoFragment;
import com.example.boostcourseaceproject4.model.Movie;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

//메인액티비티 : 영화목록들을 뷰페이저로 보여주는 역할
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static Stack<Fragment> fragmentStack;
    private ViewPager pager; //뷰페이저
    private List<MovieFragment> movieList =  new ArrayList<>();
    private MoviePagerAdapter moviePagerAdapter; //영화 목록 어댑터
    private Toolbar toolbar; //툴바

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView(); //뷰초기화
        init(); //값초기화세팅

    }

    //뷰초기화
    public void initView(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.main_nav_navigation);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }
    //값들 초기화 세팅
    public void init() {
        fragmentStack = new Stack<>();
        //영화프래그먼트(생성자에서 영화정보를 받게함)
        movieList.add(new MovieFragment(new Movie(1, R.drawable.image1, "군도", "15", "61" )));
        movieList.add(new MovieFragment(new Movie(2, R.drawable.image2, "공조", "12", "20" )));
        movieList.add(new MovieFragment(new Movie(3, R.drawable.image3, "더킹", "19", "10" )));
        movieList.add(new MovieFragment(new Movie(4, R.drawable.image4, "레지던트 이블", "15", "9" )));
        movieList.add(new MovieFragment(new Movie(5, R.drawable.image5, "럭키", "12", "5" )));
        movieList.add(new MovieFragment(new Movie(6, R.drawable.image6, "아수라", "19", "1" )));
        //뷰페이저
        pager = findViewById(R.id.main_pager);
        //뷰페이저에서 패래그먼트 옆화면 살짝보이게하기조절
        pager.setClipToPadding(false);
        // set padding manually, the more you set the padding the more you see of prev & next page(뷰페이저에서 패래그먼트 옆화면 살짝보이게하기조절)
        pager.setPadding(150, 0, 150, 0);
        //캐싱을 해놓을 프래그먼트 개수
        pager.setOffscreenPageLimit(6);
        //어댑터에 추가
        moviePagerAdapter = new MoviePagerAdapter(getSupportFragmentManager());
        moviePagerAdapter.addAllItem((ArrayList<MovieFragment>) movieList);
        pager.setAdapter(moviePagerAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override //바로가기메뉴 선택시 호출
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_0) {
            Toast.makeText(this, "영화목록으로 가기", Toast.LENGTH_LONG).show();
            clearStack(); //프래그먼트 백스택제거 및 remove
        } else if (id == R.id.nav_1) {
            Toast.makeText(this, "두번째 메뉴 선택됨.", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_2) {
            Toast.makeText(this, "세번째 메뉴 선택됨.", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //프래그먼트 백스텍 제거 및 remove (초기화면에서 프래그먼트를 6개 사용하므로 6을 기준으로 그 이상의 것을 제거하게했다.)
    public void clearStack() {
        //Here we are clearing back stack fragment entries
        int backStackEntry = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntry > 6) {
            for (int i = 6; i < backStackEntry; i++) {
                getSupportFragmentManager().popBackStackImmediate();
            }
        }
        //Here we are removing all the fragment that are shown here
        if (getSupportFragmentManager().getFragments() != null && getSupportFragmentManager().getFragments().size() > 6) {
            for (int i = 6; i < getSupportFragmentManager().getFragments().size(); i++) {
                Fragment mFragment = getSupportFragmentManager().getFragments().get(i);
                if (mFragment != null) {
                    getSupportFragmentManager().beginTransaction().remove(mFragment).commit();
                }
            }
        }
    }

}
