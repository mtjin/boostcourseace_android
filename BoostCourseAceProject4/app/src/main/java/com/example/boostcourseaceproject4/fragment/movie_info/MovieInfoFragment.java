package com.example.boostcourseaceproject4.fragment.movie_info;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.boostcourseaceproject4.activity.PhotoViewActivity;
import com.example.boostcourseaceproject4.activity.main.MainActivity;
import com.example.boostcourseaceproject4.R;
import com.example.boostcourseaceproject4.activity.comment_total.CommentTotalActivity;
import com.example.boostcourseaceproject4.activity.comment_write.CommentWriteActivity;
import com.example.boostcourseaceproject4.adapter.CommentAdapter;
import com.example.boostcourseaceproject4.adapter.GalleryAdapter;
import com.example.boostcourseaceproject4.databinding.FragmentMovieInfoBinding;
import com.example.boostcourseaceproject4.interfaces.CommentItemClickListener;
import com.example.boostcourseaceproject4.model.Comment;
import com.example.boostcourseaceproject4.model.MovieInfo;
import com.example.boostcourseaceproject4.model.Gallery;
import com.example.boostcourseaceproject4.utils.NetworkStatusHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//영화상세정보 프래그먼트 (지금은 군도만띄워지게 해놨지만 추후 프로젝트에서 영화에대한 정보가 나오면 번들로 값을 받아 해당영화를 띄워줄 계획이다.)
public class MovieInfoFragment extends Fragment implements MovieInfoContract.View {
    //TAG
    final static String TAG = "MovieInfoActivityT";
    //binding
    FragmentMovieInfoBinding layout;
    //value
    private int movieId;
    private boolean likeState = false;
    private boolean disLikeState = false;
    private int likeCount = 0;
    private int disLikeCount = 0;
    private boolean likeUp = false;   //좋아요 싫어요 적용여부에 따른 서버 전송 및 저장을 위해 사용되는 변수
    private boolean likeCancel = false;
    private boolean dislikeUp = false;
    private boolean dislikeCancel = false;
    private ArrayList<Comment> commentArrayList = new ArrayList<>();
    private CommentAdapter commentAdapter;
    private MovieInfo movieInfo;
    private ArrayList<Gallery> galleryArrayList = new ArrayList<>();
    private MovieInfoPresenter presenter;
    private GalleryAdapter.GalleryItemClickListener galleryItemClickListener;


    //requestCode
    final static int WRITE_REQUEST = 11;
    final static int TOTAL_REQUEST = 12;
    //putExtra key
    final static String MOVIEINFO_EXTRA = "MOVIEINFO_EXTRA";
    final static String MOVIEID_EXTRA = "MOVIEID_EXTRA";
    final static String EXTRA_PHOTOVIEW = "EXTRA_PHOTOVIEW";

    public MovieInfoFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_info, container, false);
        layout.setFragment(this); //위와같이 정의한것을 세팅해줌(가져와줌)
        //스크롤뷰안의 리스트뷰 스크롤은  android:nestedScrollingEnabled ="true"로 할 수 있으나 API21이전 기기에서는 작동을 안하므로 다음 코드를 추가해주었다.
        ViewCompat.setNestedScrollingEnabled(layout.movieinfoLivComments, true);
        //presenter 생성
        presenter = new MovieInfoPresenter(this);
        //어댑터생성
        initCommentAdapter();
        //전달받은 인텐트처리
        processBundle();
        //영화상세정보 요청
        presenter.requestMovieInfo(getContext(), movieId);
        //댓글요청
        presenter.requestCommentList(getContext(), movieId);
        return layout.getRoot();
    }

    //댓글 어댑터 작업
    private void initCommentAdapter() {
        //어댑터 아이템클릭리스너
        CommentItemClickListener commentItemClickListener = new CommentItemClickListener() {
            @Override
            public void onClickItem(Comment comment) {
                presenter.requestCommentRecommend(getContext(), comment);
            }
        };
        //어댑터생성
        commentAdapter = new CommentAdapter(getActivity(), commentArrayList, commentItemClickListener);
        layout.movieinfoLivComments.setAdapter(commentAdapter);//리스트뷰 어댑터연결
    }

    //갤러리 어댑터 작업
    private void initGalleryAdapter() {
        if (movieInfo.getPhotos() != null) {
            String[] photoUrls = movieInfo.getPhotos().split(",");
            for (int i = 0; i < photoUrls.length; i++) {
                galleryArrayList.add(new Gallery(photoUrls[i], 0));
            }
        }
        if (movieInfo.getVideos() != null) {
            String[] videoUrls = movieInfo.getVideos().split(",");
            for (int i = 0; i < videoUrls.length; i++) {
                galleryArrayList.add(new Gallery(videoUrls[i], 1));
            }
        }

        GalleryAdapter photoVideoAdapter = new GalleryAdapter(getContext(), galleryArrayList, galleryItemClickListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false); //레이아웃매니저 생성
        layout.movieinfoRevGallery.setLayoutManager(layoutManager);
        layout.movieinfoRevGallery.setAdapter(photoVideoAdapter);//만든 레이아웃매니저 객체를(설정을) 리사이클러 뷰에 설정해줌
    }

    //뷰초기화
    @SuppressLint("SetTextI18n")
    private void initView() {
        Glide.with(this).load(movieInfo.getThumb()).into(layout.movieinfoIvPoster);
        layout.movieinfoTvTitle.setText(movieInfo.getTitle());
        layout.movieinfoTvDates.setText(movieInfo.getDate());
        layout.movieinfoTvGenere.setText(movieInfo.getGenre());
        layout.movieinfoTvRunningtime.setText(movieInfo.getDuration() + "");
        layout.movieinfoTvRank.setText(movieInfo.getReservation_grade() + "");
        layout.movieinfoTvShare.setText(movieInfo.getReservation_rate() + ""); //예매율
        layout.movieinfoRbRating.setRating((float) movieInfo.getUser_rating());
        layout.movieinfoTvRatingscore.setText(movieInfo.getUser_rating() * 2 + "");
        layout.movieinfoTvAttendance.setText(movieInfo.getAudience() + "");
        layout.movieinfoTvContent.setText(movieInfo.getSynopsis());
        layout.movieinfoTvDirector.setText(movieInfo.getDirector());
        layout.movieinfoTvActor.setText(movieInfo.getActor());
        layout.movieinfoTvLikecount.setText(movieInfo.getLike() + "");
        layout.movieinfoTvDislikecount.setText(movieInfo.getDislike() + "");
        likeCount = movieInfo.getLike();
        disLikeCount = movieInfo.getDislike();
        MainActivity.toolbar.setTitle("영화 상세");
        //댓글 서버 요청
        presenter.requestCommentList(getContext(), movieId);
        //영화 사진 및 비디오 리사이클러뷰 세팅
        initGalleryAdapter();
    }


    //전달받은 번들 값 처리
    private void processBundle() {
        Bundle bundle = getArguments();
        movieId = bundle.getInt(MOVIEID_EXTRA, -1);
        Log.d(TAG, "전달받은 영화 아이디 => " + movieId);
    }

    //좋아요클릭
    public void onLikeClick(View view) {
        if (NetworkStatusHelper.getConnectivityStatus(Objects.requireNonNull(getContext()))) { //인터넷 연결 되있을 경우
            if (likeState) {//좋아요 취소
                likeCount -= 1;
                layout.movieinfoTvLikecount.setText(String.valueOf(likeCount));
                layout.movieinfoIbtnLikeup.setBackgroundResource(R.drawable.selector_thumbs_up);
                likeUp = false;   //좋아요 싫어요 적용여부에 따른 서버 전송 및 저장을 위해 사용되는 플래그
                likeCancel = true;
                dislikeUp = false;
                dislikeCancel = false;
            } else {  //좋아요
                if (disLikeState) { //싫어요 눌러져있는 경우 취소하고 좋아요
                    disLikeCount -= 1;
                    layout.movieinfoTvDislikecount.setText(String.valueOf(disLikeCount));
                    layout.movieinfoIbtnDislikeup.setBackgroundResource(R.drawable.selector_thumbs_down);
                    disLikeState = false;
                }
                likeCount += 1;
                layout.movieinfoTvLikecount.setText(String.valueOf(likeCount));
                layout.movieinfoIbtnLikeup.setBackgroundResource(R.drawable.ic_thumb_up_selected);
                likeUp = true;   //좋아요 싫어요 적용여부에 따른 서버 전송 및 저장을 위해 사용되는 플래그
                likeCancel = false;
                dislikeUp = false;
                dislikeCancel = false;

            }
            likeState = !likeState;
        } else { //인터넷 연결 안되있을 경우
            onToastMessage("인터넷이 끊켜있습니다.");
        }
    }

    //싫어요클릭
    public void onDislikeClick(View view) {
        if (NetworkStatusHelper.getConnectivityStatus(Objects.requireNonNull(getContext()))) { //인터넷 연결 되있을 경우
            if (disLikeState) { //싫어요 취소
                disLikeCount -= 1;
                layout.movieinfoTvDislikecount.setText(String.valueOf(disLikeCount));
                layout.movieinfoIbtnDislikeup.setBackgroundResource(R.drawable.selector_thumbs_down);
                likeUp = false;   //좋아요 싫어요 적용여부에 따른 서버 전송 및 저장을 위해 사용되는 플래그
                likeCancel = false;
                dislikeUp = false;
                dislikeCancel = true;
            } else { //싫어요
                if (likeState) { //좋아요 눌러져있던 경우 취소하고 싫어요
                    likeCount -= 1;
                    layout.movieinfoTvLikecount.setText(String.valueOf(likeCount));
                    layout.movieinfoIbtnLikeup.setBackgroundResource(R.drawable.selector_thumbs_up);
                    likeState = false;
                }
                disLikeCount += 1;
                layout.movieinfoTvDislikecount.setText(String.valueOf(disLikeCount));
                layout.movieinfoIbtnDislikeup.setBackgroundResource(R.drawable.ic_thumb_down_selected);
                likeUp = false;   //좋아요 싫어요 적용여부에 따른 서버 전송 및 저장을 위해 사용되는 플래그
                likeCancel = false;
                dislikeUp = true;
                dislikeCancel = false;
            }
            disLikeState = !disLikeState;
        } else { //인터넷 연결 안되있을 경우
            onToastMessage("인터넷이 끊켜있습니다.");
        }
    }


    //댓글모두보기 버튼 클릭
    public void onAllViewClick(View view) {
        Intent totalCommentIntent = new Intent(getActivity(), CommentTotalActivity.class);
        totalCommentIntent.putExtra(MOVIEINFO_EXTRA, movieInfo); //영화 정보전달(뷰 세팅과 id값 하는데 사용)
        startActivityForResult(totalCommentIntent, TOTAL_REQUEST);
        onToastMessage("모두보기");
    }

    //작성하기 버튼 클릭
    public void onWriteClick(View view) {
        onToastMessage("작성하기");
        Intent writeIntent = new Intent(getActivity(), CommentWriteActivity.class);
        writeIntent.putExtra(MOVIEINFO_EXTRA, movieInfo);
        startActivityForResult(writeIntent, WRITE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WRITE_REQUEST && resultCode == getActivity().RESULT_OK) {
            presenter.requestCommentList(getContext(), movieId);
        } else if (requestCode == TOTAL_REQUEST && resultCode == getActivity().RESULT_OK) { //댓글전체보기 결과
            presenter.requestCommentList(getContext(), movieId);
        }
    }


    @Override
    //TODO::좋아요/싫어요를 버튼 누를시 그때 그때 보내면, 버튼 연타 등에서 오동작이 발생될 확률이 높습니다. 이런 경우는 변수에 값만 들고 있다가, 화면을 벗어날때 서버로 올리는 방법이 훨씬 안전합니다.
    public void onPause() {
        super.onPause();
        if (likeState || disLikeState) { //둘 중 하나 눌러진 상태일 경우
            presenter.requestMovieLike(movieId, likeCancel, likeUp, dislikeUp, dislikeCancel);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainActivity.toolbar.setTitle("영화 목록");
    }

    @Override
    public void onToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetCommentListResult(List<Comment> commentList) {
        commentArrayList.clear();
        commentAdapter.clear();
        commentArrayList.addAll(commentList);
        commentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetMovieInfoResult(MovieInfo movieInfo) {
        this.movieInfo = movieInfo;
        initView(); //뷰초기화
    }

    @Override
    public void onGetCommentRecommendResult(Comment comment) {
        comment.setRecommend(comment.getRecommend() + 1);
        commentAdapter.notifyDataSetChanged();
    }
}
