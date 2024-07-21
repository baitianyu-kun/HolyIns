package com.bai.HolyIns.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bai.HolyIns.R;
import com.bai.HolyIns.activity.SubscribersActivity;
import com.bai.HolyIns.pojo.Subscribe;
import com.bai.HolyIns.pojo.UserInfo;
import com.bai.HolyIns.service.ContentService;
import com.bai.HolyIns.service.ImageService;
import com.bai.HolyIns.state.ActivityStatus;
import com.bai.HolyIns.state.ThreadState;
import com.bai.HolyIns.utils.ToastUtils;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SpecificPersonInfoFragment extends Fragment {
    //param
    private static String ARG_This_user_info = "This_user_info";
    private static String ARG_Now_user_info = "Now_user_info";
    //widgets
    private TabLayout mTableLayout;
    private ViewPager mViewPager;
    private MyAdapter adapter;
    private List<String> mTitle;
    private List<Fragment> mFragment;
    private TextView specific_person_page_name;
    private ImageView specific_person_page_head_pic;
    private Button specific_person_page_subscribe_btn;
    private TextView specific_person_page_subscribers_number;
    //other
    private UserInfo this_user_info = new UserInfo();
    private UserInfo now_user_info = new UserInfo();
    //service
    private ImageService imageService = new ImageService();
    private ContentService contentService = new ContentService();
    //handler
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            if (msg.what == ThreadState.HEAD_PIC) {
                //个人信息页面的头像
                Bitmap bitmap = bundle.getParcelable("bitmap");
                specific_person_page_head_pic.setImageBitmap(bitmap);
            } else if (msg.what == ThreadState.SUBSCRIBE_STATUS) {
                if (bundle.getString("getSubscribeStatus_status").equals(ActivityStatus.STRING_SUBSCRIBED)) {
                    ToastUtils.show_ordinary_toast(getContext(), "您已经关注了他", Toast.LENGTH_SHORT);
                    specific_person_page_subscribe_btn.setText("已关注");
                } else {
                    ToastUtils.show_ordinary_toast(getContext(), "您好像还没关注他", Toast.LENGTH_SHORT);
                    specific_person_page_subscribe_btn.setText("关注");
                }
            } else if (msg.what == ThreadState.SUBSCRIBE) {
                if (bundle.getString("subscribe_status").equals(ActivityStatus.STRING_SUBSCRIBE_SUCCESS)) {
                    ToastUtils.show_ordinary_toast(getContext(), "关注成功", Toast.LENGTH_SHORT);
                    specific_person_page_subscribe_btn.setText("已关注");
                } else {
                    ToastUtils.show_ordinary_toast(getContext(), "关注失败", Toast.LENGTH_SHORT);
                }
            } else if (msg.what == ThreadState.UN_SUBSCRIBE) {
                if (bundle.getString("unsubscribe_status").equals(ActivityStatus.STRING_UNSUBSCRIBE_SUCCESS)) {
                    ToastUtils.show_ordinary_toast(getContext(), "取消关注成功", Toast.LENGTH_SHORT);
                    specific_person_page_subscribe_btn.setText("关注");
                } else {
                    ToastUtils.show_ordinary_toast(getContext(), "刷新一下，可能已经取消了哦", Toast.LENGTH_SHORT);
                }
            } else if (msg.what == ThreadState.GET_SUBSCRIBE) {
                List<Integer> subscribed_user_list = JSON.parseArray(bundle.getString("get_subscribe_status"), Integer.class);
                //设置关注的人数
                specific_person_page_subscribers_number.setText("关注的人:" + subscribed_user_list.size());
                //如果一个人都没关注的话不应该跳转到那个界面，否则就会把系统中的所有人都查出来
                if (subscribed_user_list.size() == 0) {
                    ToastUtils.show_ordinary_toast(getContext(), "还没有关注任何人哦", Toast.LENGTH_SHORT);
                } else {
                    //设置关注人的点击跳转到另一个界面
                    specific_person_page_subscribers_number.setOnClickListener(v -> {
                        //将粉丝的所有id传到下一页面
                        Intent intent = new Intent(getContext(), SubscribersActivity.class);
                        intent.putExtra("subscribed_user_list", (Serializable) subscribed_user_list);
                        getContext().startActivity(intent);
                    });
                }
            }
            return true;
        }
    });


    public SpecificPersonInfoFragment() {
        // Required empty public constructor
    }

    public static SpecificPersonInfoFragment newInstance(UserInfo now_user_info, UserInfo this_user_info) {
        SpecificPersonInfoFragment fragment = new SpecificPersonInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_Now_user_info, now_user_info);
        args.putSerializable(ARG_This_user_info, this_user_info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            now_user_info = (UserInfo) getArguments().getSerializable(ARG_Now_user_info);
            this_user_info = (UserInfo) getArguments().getSerializable(ARG_This_user_info);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //设置姓名和头像
        specific_person_page_name.setText(this_user_info.getName());
        imageService.HeadPicGet(handler, this_user_info.getHeadPicturePath());
        //获取关注状态
        contentService.getSubscribeStatus(now_user_info.getUser_id(), this_user_info.getUser_id(), handler);
        //获取关注的人
        contentService.getSubscribe(now_user_info.getUser_id(), handler);
        //关注监听
        specific_person_page_subscribe_btn.setOnClickListener(v -> {
            //关注了就可以取消关注
            if (specific_person_page_subscribe_btn.getText().toString().equals("已关注")) {
                AlertDialog.Builder sureDialog = new AlertDialog.Builder(getContext());
                sureDialog.setMessage("确实要取消关注吗？")
                        .setTitle("取消关注");
                sureDialog.setPositiveButton("确定", (dialog, which) -> {
                    contentService.unSubscribe(now_user_info.getUser_id(), this_user_info.getUser_id(), handler);
                    specific_person_page_subscribe_btn.setText("关注");
                });
                sureDialog.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
                sureDialog.show();
            } else {
                //没关注要关注一下
                Subscribe subscribe = new Subscribe();
                subscribe.setNow_user_id(now_user_info.getUser_id());
                subscribe.setSubscribe_time(new Date());
                subscribe.setSubscribe_userid(this_user_info.getUser_id());
                contentService.Subscribe(subscribe, handler);
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /*this_user_info.setUser_id(1);
        this_user_info.setName("bai");
        this_user_info.setAccount("111");
        this_user_info.setHeadPicturePath("E:/holyins/headpic/defaultheadpic.jpg");
        this_user_info.setSex("男");
        this_user_info.setE_mail("1748383809@qq.com");
        this_user_info.setRegisterTime("20210129");

        now_user_info.setUser_id(1);*/

        View view = inflater.inflate(R.layout.fragment_specific_person_info, container, false);
        specific_person_page_head_pic = view.findViewById(R.id.specific_person_page_head_pic);
        specific_person_page_name = view.findViewById(R.id.specific_person_page_name);
        mTableLayout = view.findViewById(R.id.specific_person_page_tab);
        specific_person_page_subscribe_btn = view.findViewById(R.id.specific_person_page_subscribe_btn);
        mViewPager = view.findViewById(R.id.specific_person_page_pager);
        specific_person_page_subscribers_number = view.findViewById(R.id.specific_person_page_subscribers_number);

        //标题栏数组
        mTitle = new ArrayList<>();
        mTitle.add("他的帖子");
        mTitle.add("他的喜欢");
        mTitle.add("他的转发");
        mTitle.add("详细信息");
        //fragment集合
        mFragment = new ArrayList<>();
        mFragment.add(SpecificPersonPostFragment.newInstance(this_user_info,now_user_info, ActivityStatus.FIND_OWN_POST));
        mFragment.add(SpecificPersonPostFragment.newInstance(this_user_info, now_user_info,ActivityStatus.FIND_OWN_LIKE_POST));
        mFragment.add(SpecificPersonPostFragment.newInstance(this_user_info,now_user_info,ActivityStatus.FIND_OWN_FORWARDING_POST));
        mFragment.add(PersonInfoDetailsFragment.newInstance(this_user_info));
        //在activity中使用 getSupportFragmentManager(),这里是Fragment中使用如下方法
        adapter = new MyAdapter(getFragmentManager());
        mViewPager.setAdapter(adapter);
        //手动设置缓存就可以不重新加载数据了
        mViewPager.setOffscreenPageLimit(4);
        //将TabLayout和ViewPager绑定在一起，一个动另一个也会跟着动
        mTableLayout.setupWithViewPager(mViewPager);
        return view;
    }

    //创建Fragment的适配器
    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        //获得每个页面的下标
        @Override
        public Fragment getItem(int position) {
            return mFragment.get(position);
        }

        //获得List的大小
        @Override
        public int getCount() {
            return mFragment.size();
        }

        //获取title的下标
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle.get(position);
        }
    }
}