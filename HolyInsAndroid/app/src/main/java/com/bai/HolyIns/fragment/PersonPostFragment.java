package com.bai.HolyIns.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bai.HolyIns.R;
import com.bai.HolyIns.adapter.PagePostAdapter;
import com.bai.HolyIns.adapter.PersonOwnForwardPostAdapter;
import com.bai.HolyIns.adapter.PersonOwnForwardsPostAdapter;
import com.bai.HolyIns.adapter.PersonOwnLikePostAdapter;
import com.bai.HolyIns.adapter.PersonOwnPostAdapter;
import com.bai.HolyIns.adapter.PersonPostItemAdapter;
import com.bai.HolyIns.pojo.ForwardPhotoID;
import com.bai.HolyIns.pojo.ForwardUserPost;
import com.bai.HolyIns.pojo.HashTag;
import com.bai.HolyIns.pojo.LikePhotoID;
import com.bai.HolyIns.pojo.LikeUserPost;
import com.bai.HolyIns.pojo.UserInfo;
import com.bai.HolyIns.pojo.UserPost;
import com.bai.HolyIns.service.ImageService;
import com.bai.HolyIns.service.PersonInfoService;
import com.bai.HolyIns.state.ActivityStatus;
import com.bai.HolyIns.state.ThreadState;

import java.util.List;

import mehdi.sakout.dynamicbox.DynamicBox;

/*
这个是personInfoFragment中的那下面的三个子界面、我的帖子、我的喜欢、我的转发
 */
public class PersonPostFragment extends Fragment {
    //widgets
    private ListView person_post_list;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView person_post_recycleview;
    //加载界面
    private DynamicBox box;
    //arg_param
    private static final String ARG_Now_User_Info = "Now_User_Info";
    private static final String ARG_Person_Post_Type = "Person_Post_Type";
    //param
    private String mPerson_Post_Type;
    private UserInfo mNowUserInfo;
    //service
    private PersonInfoService personInfoService = new PersonInfoService();
    //adapter
    private PersonOwnPostAdapter personOwnPostAdapter;
    private PersonOwnLikePostAdapter personOwnLikePostAdapter;
    private PersonOwnForwardsPostAdapter personOwnForwardsPostAdapter;
    //handler
    Handler handler = new Handler(msg -> {
        Bundle bundle = msg.getData();
        if (msg.what == ThreadState.FIND_OWN_POST) {
            List<UserPost> userPosts = JSON.parseArray(bundle.getString("findOwnPost_status"), UserPost.class);
            personOwnPostAdapter = new PersonOwnPostAdapter(userPosts, getContext(), mNowUserInfo);

            //在adapter中点击删除之后在fragment中刷新列表
            personOwnPostAdapter.setOnRefreshPageClickListener(() -> {
                refreshAtBegin();
                refreshPage();
            });

            person_post_list.setAdapter(personOwnPostAdapter);
            //person_post_recycleview.setAdapter(personOwnPostAdapter);
            stopRefreshPage();
        } else if (msg.what == ThreadState.FIND_OWN_LIKE_POST) {
            List<LikeUserPost> userPosts = JSON.parseArray(bundle.getString("findOwnLikePost_status"), LikeUserPost.class);
            personOwnLikePostAdapter = new PersonOwnLikePostAdapter(userPosts, getContext(), mNowUserInfo);
            person_post_list.setAdapter(personOwnLikePostAdapter);
            stopRefreshPage();
        } else if (msg.what == ThreadState.FIND_OWN_FORWARDING_POST) {
            List<ForwardUserPost> userPosts = JSON.parseArray(bundle.getString("findOwnForwardingPost_status"), ForwardUserPost.class);
            personOwnForwardsPostAdapter = new PersonOwnForwardsPostAdapter(userPosts, getContext(), mNowUserInfo);
            person_post_list.setAdapter(personOwnForwardsPostAdapter);
            stopRefreshPage();
        }
        return true;
    });

    @Override
    public void onResume() {
        super.onResume();
        Log.i("PersonPostFragment","Onresume");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("============personpost"+"ondestroy");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.i("PersonPostFragment","Onhidden");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("PersonPostFragment","Onpause");
    }

    public PersonPostFragment() {
        // Required empty public constructor
    }

    public static PersonPostFragment newInstance(UserInfo NowUserInfo, String Person_Post_Type) {
        PersonPostFragment fragment = new PersonPostFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_Now_User_Info, NowUserInfo);
        args.putString(ARG_Person_Post_Type, Person_Post_Type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNowUserInfo = (UserInfo) getArguments().getSerializable(ARG_Now_User_Info);
            mPerson_Post_Type = getArguments().getString(ARG_Person_Post_Type);
        }
    }

    //刚打开app、或切换界面后回来时开始刷新
    public void refreshAtBegin() {
        swipeRefreshLayout.setRefreshing(true);
    }

    public void refreshPage() {
        //获取帖子
        if (mPerson_Post_Type.equals(ActivityStatus.FIND_OWN_POST)) {
            personInfoService.findOwnPost(mNowUserInfo.getUser_id(), handler);
        } else if (mPerson_Post_Type.equals(ActivityStatus.FIND_OWN_LIKE_POST)) {
            personInfoService.findOwnLikePost(mNowUserInfo.getUser_id(), handler);
        } else if (mPerson_Post_Type.equals(ActivityStatus.FIND_OWN_FORWARDING_POST)) {
            personInfoService.findOwnForwardingPost(mNowUserInfo.getUser_id(), handler);
        }
    }

    public void stopRefreshPage() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshAtBegin();
        refreshPage();



        swipeRefreshLayout.setOnRefreshListener(() -> {
            refreshPage();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_post, container, false);
        person_post_list = view.findViewById(R.id.person_post_list);
        swipeRefreshLayout = view.findViewById(R.id.person_post_swipe_refresh);
        return view;
    }
}