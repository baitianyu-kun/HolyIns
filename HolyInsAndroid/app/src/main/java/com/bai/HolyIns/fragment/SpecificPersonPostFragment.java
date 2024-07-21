package com.bai.HolyIns.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSON;
import com.bai.HolyIns.R;
import com.bai.HolyIns.adapter.PersonOwnForwardsPostAdapter;
import com.bai.HolyIns.adapter.PersonOwnLikePostAdapter;
import com.bai.HolyIns.adapter.SpecificPersonOwnPostAdapter;
import com.bai.HolyIns.pojo.ForwardUserPost;
import com.bai.HolyIns.pojo.LikeUserPost;
import com.bai.HolyIns.pojo.UserInfo;
import com.bai.HolyIns.pojo.UserPost;
import com.bai.HolyIns.service.PersonInfoService;
import com.bai.HolyIns.state.ActivityStatus;
import com.bai.HolyIns.state.ThreadState;

import java.util.List;

import mehdi.sakout.dynamicbox.DynamicBox;

/*
这个是SpecificPersonInfoFragment中的那下面的三个子界面、他的帖子、他的喜欢、他的转发
 */
public class SpecificPersonPostFragment extends Fragment {
    //widgets
    private ListView person_post_list;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView person_post_recycleview;
    //加载界面
    private DynamicBox box;
    //arg_param
    private static final String ARG_This_User_Info = "This_User_Info";
    private static final String ARG_Person_Post_Type = "Person_Post_Type";
    private static final String ARG_Now_User_Info="Now_User_Info";
    //param
    private String mPerson_Post_Type;
    private UserInfo mThisUserInfo;
    private UserInfo mNowUserInfo;
    //service
    private PersonInfoService personInfoService = new PersonInfoService();
    //adapter
    private SpecificPersonOwnPostAdapter personOwnPostAdapter;
    private PersonOwnLikePostAdapter personOwnLikePostAdapter;
    private PersonOwnForwardsPostAdapter personOwnForwardsPostAdapter;
    //handler
    Handler handler = new Handler(msg -> {
        Bundle bundle = msg.getData();
        if (msg.what == ThreadState.FIND_OWN_POST) {
            List<UserPost> userPosts = JSON.parseArray(bundle.getString("findOwnPost_status"), UserPost.class);
            personOwnPostAdapter = new SpecificPersonOwnPostAdapter(userPosts, getContext(), mNowUserInfo);

            personOwnPostAdapter.setOnRefreshPageClickListener(() -> {
                refreshAtBegin();
                refreshPage();
            });

            person_post_list.setAdapter(personOwnPostAdapter);
            //person_post_recycleview.setAdapter(personOwnPostAdapter);
            stopRefreshPage();
        } else if (msg.what == ThreadState.FIND_OWN_LIKE_POST) {
            List<LikeUserPost> userPosts = JSON.parseArray(bundle.getString("findOwnLikePost_status"), LikeUserPost.class);
            personOwnLikePostAdapter = new PersonOwnLikePostAdapter(userPosts, getContext(), mThisUserInfo);
            person_post_list.setAdapter(personOwnLikePostAdapter);
            stopRefreshPage();
        } else if (msg.what == ThreadState.FIND_OWN_FORWARDING_POST) {
            List<ForwardUserPost> userPosts = JSON.parseArray(bundle.getString("findOwnForwardingPost_status"), ForwardUserPost.class);
            personOwnForwardsPostAdapter = new PersonOwnForwardsPostAdapter(userPosts, getContext(), mThisUserInfo);
            person_post_list.setAdapter(personOwnForwardsPostAdapter);
            stopRefreshPage();
        }
        return true;
    });

    @Override
    public void onResume() {
        super.onResume();
        Log.i("PersonPostFragment", "Onresume");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("============personpost" + "ondestroy");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.i("PersonPostFragment", "Onhidden");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("PersonPostFragment", "Onpause");
    }

    public SpecificPersonPostFragment() {
        // Required empty public constructor
    }

    public static SpecificPersonPostFragment newInstance(UserInfo thisUserInfo, UserInfo nowUserInfo,String Person_Post_Type) {
        SpecificPersonPostFragment fragment = new SpecificPersonPostFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_This_User_Info, thisUserInfo);
        args.putString(ARG_Person_Post_Type, Person_Post_Type);
        args.putSerializable(ARG_Now_User_Info, nowUserInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mThisUserInfo = (UserInfo) getArguments().getSerializable(ARG_This_User_Info);
            mPerson_Post_Type = getArguments().getString(ARG_Person_Post_Type);
            mNowUserInfo= (UserInfo) getArguments().getSerializable(ARG_Now_User_Info);
        }
    }

    //刚打开app、或切换界面后回来时开始刷新
    public void refreshAtBegin() {
        swipeRefreshLayout.setRefreshing(true);
    }

    public void refreshPage() {
        //获取帖子
        if (mPerson_Post_Type.equals(ActivityStatus.FIND_OWN_POST)) {
            personInfoService.findOwnPost(mThisUserInfo.getUser_id(), handler);
        } else if (mPerson_Post_Type.equals(ActivityStatus.FIND_OWN_LIKE_POST)) {
            personInfoService.findOwnLikePost(mThisUserInfo.getUser_id(), handler);
        } else if (mPerson_Post_Type.equals(ActivityStatus.FIND_OWN_FORWARDING_POST)) {
            personInfoService.findOwnForwardingPost(mThisUserInfo.getUser_id(), handler);
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