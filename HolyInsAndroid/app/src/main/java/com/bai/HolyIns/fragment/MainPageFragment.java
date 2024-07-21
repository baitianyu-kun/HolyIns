package com.bai.HolyIns.fragment;

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
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bai.HolyIns.R;
import com.bai.HolyIns.adapter.PagePostAdapter;
import com.bai.HolyIns.pojo.UserInfo;
import com.bai.HolyIns.pojo.UserPost;
import com.bai.HolyIns.service.ContentService;
import com.bai.HolyIns.state.NetWorkProperties;
import com.bai.HolyIns.state.ThreadState;
import com.bai.HolyIns.utils.HttpUtils;
import com.bai.HolyIns.utils.StreamUtils;
import com.bai.HolyIns.utils.ToastUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.List;

public class MainPageFragment extends Fragment {
    //service
    private ContentService contentService = new ContentService();
    //widgets
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private PagePostAdapter pagePostAdapter;
    private UserInfo now_userInfo;
    //Handler
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            if (msg.what == ThreadState.GET_SUBSCRIBE) {
                List<Integer> subscribed_user = JSON.parseArray(bundle.getString("get_subscribe_status"), Integer.class);
                contentService.getSubscribedPost(handler, subscribed_user);
            } else if (msg.what == ThreadState.GET_SUBSCRIBED_USER_POST) {
                List<UserPost> userPosts = JSON.parseArray(bundle.getString("get_subscribed_post_status"), UserPost.class);
                pagePostAdapter = new PagePostAdapter(userPosts, getContext(), now_userInfo);
                listView.setAdapter(pagePostAdapter);
                //加载完成之后取消刷新
                stopRefreshPage();
            }
            return true;
        }
    });

    //param
    private static final String ARG_Now_UserInfo = "Now_User_Info";


    public MainPageFragment() {
    }

    public static MainPageFragment newInstance(UserInfo now_userInfo) {
        MainPageFragment fragment = new MainPageFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_Now_UserInfo, now_userInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            now_userInfo= (UserInfo) getArguments().getSerializable(ARG_Now_UserInfo);
        }
    }

    //刚打开app、或切换界面后回来时开始刷新
    public void refreshAtBegin() {
        swipeRefreshLayout.setRefreshing(true);
    }

    public void refreshPage() {
        //获取关注的人
        contentService.getSubscribe(now_userInfo.getUser_id(), handler);
    }

    public void stopRefreshPage() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshAtBegin();
        //加载列表
        refreshPage();
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(() -> {
            refreshPage();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);
        swipeRefreshLayout = view.findViewById(R.id.main_page_swipe_refresh);
        listView = view.findViewById(R.id.main_page_listview);
        return view;
    }
}