package com.example.boostcourseaceproject4.activity.main;

import android.os.Bundle;


import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.util.Log;
import android.view.MenuItem;

import com.android.volley.toolbox.Volley;
import com.example.boostcourseaceproject4.R;
import com.example.boostcourseaceproject4.adapter.MoviePagerAdapter;
import com.example.boostcourseaceproject4.database.AppDatabase;
import com.example.boostcourseaceproject4.fragment.MovieFragment;
import com.example.boostcourseaceproject4.fragment.movie_info.MovieInfoFragment;
import com.example.boostcourseaceproject4.interfaces.MovieFragmentListener;
import com.example.boostcourseaceproject4.model.Movie;
import com.example.boostcourseaceproject4.api.NetworkManager;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.widget.Toast;

import java.util.List;

//메인액티비티 : 영화목록들을 뷰페이저로 보여주는 역할
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MovieFragmentListener, MainContract.View {
    final  static  String TAG = "MainActivityT";
    private ViewPager pager; //뷰페이저
    private MoviePagerAdapter moviePagerAdapter; //영화 목록 어댑터
    private MovieInfoFragment movieInfoFragment;
    public static Toolbar toolbar; //툴바
    private MainPresenter presenter;;
    final static String MOVIE_EXTRA = "MOVIE_EXTRA";
    final static String MOVIEID_EXTRA = "MOVIEID_EXTRA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this); //프레젠터 생성
        initView(); //뷰초기화
        init(); //값초기화세팅

    }

    //뷰초기화
    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("영화 목록");
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
        pager.setAdapter(moviePagerAdapter);
        //데이터베이스 오픈
        AppDatabase.openDatabase(getApplicationContext(), "CinemaApp");
        //리퀘스트큐 생성
        if (NetworkManager.requestQueue == null) {
            NetworkManager.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        presenter.requestMovieList(getApplicationContext()); //서버에 영화 정보 요청
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override //바로가기메뉴 선택시 호출
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_0) {
            Toast.makeText(this, "영화목록으로 가기", Toast.LENGTH_LONG).show();
            if(movieInfoFragment != null) {
                getSupportFragmentManager().beginTransaction().remove(movieInfoFragment).commit();
                toolbar.setTitle("영화 목록");
            }
        } else if (id == R.id.nav_1) {
            Toast.makeText(this, "두번째 메뉴 선택됨.", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_2) {
            Toast.makeText(this, "세번째 메뉴 선택됨.", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override //영화프래그먼트 상세보기 버튼 클릭시 호출
    public void onDetailButtonClicked(int movieId) {
        movieInfoFragment = new MovieInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(MOVIEID_EXTRA, movieId);
        //영화 id 값 전달
        movieInfoFragment.setArguments(bundle );
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().add(R.id.main_container, movieInfoFragment);
        // 해당 transaction 을 Back Stack 에 저장
        transaction.addToBackStack(null);
        // transaction 실행
        transaction.commit();
    }

    @Override
    public void onToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetMovieListResult(List<Movie> movieList) {
        for (int i = 0; i < movieList.size(); i++) {
            Movie movie = movieList.get(i);
            //가져온 영화의 상세정보를 가져오기위해 id값을 보내 서버에 영화상세정보 데이터를 요청함
            MovieFragment movieFragment =new MovieFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(MOVIE_EXTRA, movie);
            movieFragment.setArguments(bundle);
            moviePagerAdapter.addItem(movieFragment);
            Log.d(TAG, "영화 아이디 : " + movie.getId());
            // requestMovieInfo(movie.getId());
        }
        moviePagerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //디비close
        AppDatabase.closeDatabase();
    }
}
