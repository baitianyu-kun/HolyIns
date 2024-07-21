package com.bai.HolyIns.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bai.HolyIns.R;
import com.bai.HolyIns.activity.ReleasePostActivity;
import com.bai.HolyIns.adapter.PagePostAdapter;
import com.bai.HolyIns.pojo.UserInfo;
import com.bai.HolyIns.pojo.UserPost;
import com.bai.HolyIns.service.ContentService;
import com.bai.HolyIns.state.ThreadState;
import com.bai.HolyIns.utils.ToastUtils;

import java.util.List;

import mehdi.sakout.dynamicbox.DynamicBox;

public class RecommendFragment extends Fragment {
    //service
    private ContentService contentService = new ContentService();
    //widgets
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private PagePostAdapter pagePostAdapter;
    private ImageButton recommend_page_release_post_btn;
    private UserInfo now_userInfo;
    //Handler
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            if (msg.what==ThreadState.GET_RECOMMEND_POST){
                List<UserPost> recommendPosts=JSON.parseArray(bundle.getString("get_recommend_post_status"),UserPost.class);
                pagePostAdapter = new PagePostAdapter(recommendPosts, getContext(), now_userInfo);
                listView.setAdapter(pagePostAdapter);
                stopRefreshPage();
            }
            return true;
        }
    });
    //param
    private static final String ARG_Now_UserInfo = "Now_User_Info";


    public RecommendFragment() {
        // Required empty public constructor
    }

    public static RecommendFragment newInstance(UserInfo now_userInfo) {
        RecommendFragment fragment = new RecommendFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_Now_UserInfo, now_userInfo);
        fragment.setArguments(args);
        return fragment;
    }

    //刚打开app、或切换界面后回来时开始刷新
    public void refreshAtBegin(){
        swipeRefreshLayout.setRefreshing(true);
    }

    public void refreshPage(){
        //获取推荐的帖子
        contentService.getRecommendPost(now_userInfo.getUser_id(),handler);
    }
    public void stopRefreshPage(){
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //在加载时候就开始刷新
        refreshAtBegin();
        refreshPage();
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshPage();
            }

        });
        //进入动态发表页面
        recommend_page_release_post_btn.setOnClickListener(v -> {
            Intent intent=new Intent(getContext(), ReleasePostActivity.class);
            intent.putExtra("now_user_info",now_userInfo);
            startActivity(intent);
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            now_userInfo = (UserInfo) getArguments().getSerializable(ARG_Now_UserInfo);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_recommend, container, false);
        swipeRefreshLayout=view.findViewById(R.id.recommend_page_swipe_refresh);
        listView=view.findViewById(R.id.recommend_page_listview);
        recommend_page_release_post_btn=view.findViewById(R.id.recommend_page_release_post_btn);
        return view;
    }
}