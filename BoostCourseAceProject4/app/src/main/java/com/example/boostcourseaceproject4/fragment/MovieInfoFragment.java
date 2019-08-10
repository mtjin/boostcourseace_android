package com.example.boostcourseaceproject4.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.boostcourseaceproject4.model.Comment;
import com.example.boostcourseaceproject4.R;
import com.example.boostcourseaceproject4.activity.CommentTotalActivity;
import com.example.boostcourseaceproject4.activity.CommentWriteActivity;
import com.example.boostcourseaceproject4.adapter.CommentAdapter;
import com.example.boostcourseaceproject4.databinding.FragmentMovieInfoBinding;

import java.util.ArrayList;

//영화상세정보 프래그먼트 (지금은 군도만띄워지게 해놨지만 추후 프로젝트에서 영화에대한 정보가 나오면 번들로 값을 받아 해당영화를 띄워줄 계획이다.)
public class MovieInfoFragment extends Fragment {
    //TAG
    final static String TAG = "MovieInfoActivityT";
    //binding
    FragmentMovieInfoBinding layout;
    //value
    private boolean likeState = false;
    private boolean disLikeState = false;
    private int likeCount = 0;
    private int disLikeCount = 0;
    private ArrayList<Comment> commentList = new ArrayList<>();
    private CommentAdapter commentAdapter;
    //requestCode
    final static int WRITE_REQUEST = 11;
    final static int TOTAL_REQUEST = 12;
    //putExtra key
    final static String COMMENT_EXTRA = "COMMENT_EXTRA";
    final static String COMMENT_LIST_EXTRA = "COMMENT_LIST_EXTRA";
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
        //초기 좋아요 싫어요 개수 세팅
        likeCount = Integer.parseInt(layout.movieinfoTvLikecount.getText().toString().trim());
        disLikeCount = Integer.parseInt(layout.movieinfoTvDislikecount.getText().toString().trim());
        //리스트뷰 어댑터
        Comment comment = new Comment(R.drawable.ic_userprofile, 4, "진승언", "볼만했어요!", 0, "10");
        Comment comment1 = new Comment(R.drawable.ic_userprofile, 4, "진승언", "볼만했어요!", 0, "10");
        Comment comment2 = new Comment(R.drawable.ic_userprofile, 4, "진승언", "볼만했어요!", 0, "10");
        Comment comment3 = new Comment(R.drawable.ic_userprofile, 4, "진승언", "볼만했어요!", 0, "10");
        Comment comment4 = new Comment(R.drawable.ic_userprofile, 4, "진승언", "볼만했어요!", 0, "10");
        Comment comment5 = new Comment(R.drawable.ic_userprofile, 4, "진승언", "볼만했어요!", 0, "10");
        commentList.add(comment);
        commentList.add(comment1);
        commentList.add(comment2);
        commentList.add(comment3);
        commentList.add(comment4);
        commentList.add(comment5);
        commentAdapter = new CommentAdapter(commentList, getActivity());
        layout.movieinfoLivComments.setAdapter(commentAdapter);//리스트뷰 어댑터연결
        return layout.getRoot();
    }

    //좋아요클릭
    public void onLikeClick(View view) {
        if (likeState) {
            likeCount -= 1;
            layout.movieinfoTvLikecount.setText(String.valueOf(likeCount));
            layout.movieinfoIbtnLikeup.setBackgroundResource(R.drawable.selector_thumbs_up);
        } else {
            if (disLikeState) {
                disLikeCount -= 1;
                layout.movieinfoTvDislikecount.setText(String.valueOf(disLikeCount));
                layout.movieinfoIbtnDislikeup.setBackgroundResource(R.drawable.selector_thumbs_down);
                disLikeState = false;
            }
            likeCount += 1;
            layout.movieinfoTvLikecount.setText(String.valueOf(likeCount));
            layout.movieinfoIbtnLikeup.setBackgroundResource(R.drawable.ic_thumb_up_selected);
        }
        likeState = !likeState;
    }

    //싫어요클릭
    public void onDislikeClick(View view) {
        if (disLikeState) {
            disLikeCount -= 1;
            layout.movieinfoTvDislikecount.setText(String.valueOf(disLikeCount));
            layout.movieinfoIbtnDislikeup.setBackgroundResource(R.drawable.selector_thumbs_down);
        } else {
            if (likeState) {
                likeCount -= 1;
                layout.movieinfoTvLikecount.setText(String.valueOf(likeCount));
                layout.movieinfoIbtnLikeup.setBackgroundResource(R.drawable.selector_thumbs_up);
                likeState = false;
            }
            disLikeCount += 1;
            layout.movieinfoTvDislikecount.setText(String.valueOf(disLikeCount));
            layout.movieinfoIbtnDislikeup.setBackgroundResource(R.drawable.ic_thumb_down_selected);
        }
        disLikeState = !disLikeState;
    }


    //댓글모두보기 버튼 클릭
    public void onAllViewClick(View view) {
        Toast.makeText(getActivity(), "모두보기", Toast.LENGTH_SHORT).show();
        Intent totalCommentIntent = new Intent(getActivity(), CommentTotalActivity.class);
        totalCommentIntent.putExtra(COMMENT_LIST_EXTRA, commentList);
        startActivityForResult(totalCommentIntent, TOTAL_REQUEST);
        //    start(this, commentList);
    }

    //작성하기 버튼 클릭
    public void onWriteClick(View view) {
        Toast.makeText(getActivity(), "작성하기", Toast.LENGTH_SHORT).show();
        Intent writeIntent = new Intent(getActivity(), CommentWriteActivity.class);
        startActivityForResult(writeIntent, WRITE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WRITE_REQUEST && resultCode == getActivity().RESULT_OK) {
            Comment comment = (Comment) data.getSerializableExtra(COMMENT_EXTRA);
            if (comment != null) {
                commentList.add(comment);
                commentAdapter.notifyDataSetChanged();
            } else {
                Log.d(TAG, "작성하기 RESULT 실패");
            }
        } else if (requestCode == TOTAL_REQUEST && resultCode == getActivity().RESULT_OK) { //댓글전체보기 결과
            commentList.clear();
            commentAdapter.clear();
            commentList = (ArrayList<Comment>) data.getSerializableExtra(COMMENT_LIST_EXTRA);
            commentAdapter.addAll((ArrayList<Comment>) data.getSerializableExtra(COMMENT_LIST_EXTRA));
            commentAdapter.notifyDataSetChanged();
        }
    }
}
