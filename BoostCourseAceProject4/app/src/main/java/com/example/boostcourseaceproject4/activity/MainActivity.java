package com.example.boostcourseaceproject4.activity;

import android.os.Bundle;


import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.boostcourseaceproject4.R;
import com.example.boostcourseaceproject4.adapter.MoviePagerAdapter;
import com.example.boostcourseaceproject4.fragment.MovieFragment;
import com.example.boostcourseaceproject4.fragment.MovieInfoFragment;
import com.example.boostcourseaceproject4.interfaces.MovieFragmentListener;
import com.example.boostcourseaceproject4.model.Movie;
import com.example.boostcourseaceproject4.model.MovieInfo;
import com.example.boostcourseaceproject4.model.MovieInfoList;
import com.example.boostcourseaceproject4.model.MovieList;
import com.example.boostcourseaceproject4.model.ResponseMovie;
import com.example.boostcourseaceproject4.model.ResponseMovieInfo;
import com.example.boostcourseaceproject4.utils.NetworkHelper;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

//메인액티비티 : 영화목록들을 뷰페이저로 보여주는 역할
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MovieFragmentListener {
    final  static  String TAG = "MainActivityT";
    public static Stack<Fragment> fragmentStack;
    private ViewPager pager; //뷰페이저
    private ArrayList<MovieFragment> movieFragmentArrayList = new ArrayList<>();
    private MoviePagerAdapter moviePagerAdapter; //영화 목록 어댑터
    private MovieInfoFragment movieInfoFragment;
    public static Toolbar toolbar; //툴바
    final static String MOVIE_EXTRA = "MOVIE_EXTRA";
    final static String MOVIEINFO_EXTRA = "MOVIEINFO_EXTRA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView(); //뷰초기화
        init(); //값초기화세팅

    }

    //뷰초기화
    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        //리퀘스트큐 생성
        if (NetworkHelper.requestQueue == null) {
            NetworkHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        requestMovieList(); //서버에 영화 정보 요청
    }

    //영화 리스트 불러오기
    public void requestMovieList() {
        String url = "http://" + NetworkHelper.host + ":" + NetworkHelper.port + "/movie/readMovieList";
        url += "?" + "type=1"; //파리미터도 추가해줌

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processMovieListResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "영화 요청못받음");
                    }
                }
        );
        //아래 add코드처럼 넣어줄때 Volley라고하는게 내부에서 캐싱을 해준다, 즉, 한번 보내고 받은 응답결과가 있으면
        //그 다음에 보냈을 떄 이전 게 있으면 그냥 이전거를 보여줄수도  있다.
        //따라서 이렇게 하지말고 매번 받은 결과를 그대로 보여주기 위해 다음과같이 setShouldCache를 false로한다.
        request.setShouldCache(false);
        NetworkHelper.requestQueue.add(request);//리퀘스트큐에 넣으면 리퀘스트큐가 알아서 스레드로 서버에 요청해주고 응답가져옴
    }

    //영화  불러오기
    public void processMovieListResponse(String response) {
        Gson gson = new Gson();

        //먼저 앞에 3개를 자바객체 값에 담게 변환시켜줌( result는 객체배열이므로 따로 또 클래스만들어줘야함, 따로 해주기위해 이거 한 후에 뒤에서 변환시킴)
        ResponseMovie info = gson.fromJson(response, ResponseMovie.class);
        if (info.code == 200) { //코드가 200과 같다면 result라는거안에 데이터가 들어가있다는것을 확신할 수 있음
            MovieList movieList = gson.fromJson(response, MovieList.class);  //result부분 json속성값들 자바객체 값에 담게 변환시켜줌

            for (int i = 0; i < movieList.result.size(); i++) {
                Movie movie = movieList.result.get(i);
                //가져온 영화의 상세정보를 가져오기위해 id값을 보내 서버에 영화상세정보 데이터를 요청함
                MovieFragment movieFragment =new MovieFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(MOVIE_EXTRA, movie);
                movieFragment.setArguments(bundle);
                moviePagerAdapter.addItem(movieFragment);
                Log.d(TAG, "영화 아이디 : " + movie.getId());
               // requestMovieInfo(movie.getId());
            }
            moviePagerAdapter.notifyDataSetChanged();
        }
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
            if(movieInfoFragment != null) {
                getSupportFragmentManager().beginTransaction().remove(movieInfoFragment).commit();
                toolbar.setTitle("영화 목록");
            }
            //clearStack(); //프래그먼트 백스택제거 및 remove
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
        requestMovieInfo(movieId);
    }

    //id값을 받아 해당 영화 상세정보 요청
    public void requestMovieInfo(int id) {
        String url = "http://" + NetworkHelper.host + ":" + NetworkHelper.port + "/movie/readMovie?id=";
        url += id; //파리미터도 추가해줌

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processMovieInfoResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG", "영화 상세정보 요청못받음");
                    }
                }
        );
        request.setShouldCache(false);
        NetworkHelper.requestQueue.add(request);
    }

    //영화상세정보 받아옴
    public void processMovieInfoResponse(String response) {
        Gson gson = new Gson();
        ResponseMovieInfo info = gson.fromJson(response, ResponseMovieInfo.class);
        if (info.code == 200) { //코드가 200과 같다면 result라는거안에 데이터가 들어가있다는것을 확신할 수 있음
            MovieInfoList movieInfoList = gson.fromJson(response, MovieInfoList.class);  //result부분 json속성값들 자바객체 값에 담게 변환시켜줌
            MovieInfo movieInfo = movieInfoList.result.get(0);
            //영화 프래그먼트 페이지어댑터에 추가 후 notify
             movieInfoFragment = new MovieInfoFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(MOVIEINFO_EXTRA, movieInfo);
            movieInfoFragment.setArguments(bundle);
            /*
            *  TODO:: R.id.main_container에 추가돼는 fragment는 MovieInformation밖에 없습니다. getSupportFragmentManager().findFragmentById(R.id.main_container)를 사용하시면 fragment 숫자를 파악하는 로직이 굳이 필요하지 않을거 같습니다.
            *  라고 리뷰를 받았는데 어떻게하는건지 잘 이해가 안갑니다.
            * */
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().add(R.id.main_container, movieInfoFragment);
            // 해당 transaction 을 Back Stack 에 저장
            transaction.addToBackStack(null);
            // transaction 실행
            transaction.commit();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
