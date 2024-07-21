package com.bai.HolyIns.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bai.HolyIns.R;
import com.bai.HolyIns.activity.CommentActivity;
import com.bai.HolyIns.activity.LoginActivity;
import com.bai.HolyIns.activity.PersonInfoChangeActivity;
import com.bai.HolyIns.activity.SubscribersActivity;
import com.bai.HolyIns.pojo.UserInfo;
import com.bai.HolyIns.service.BaseService;
import com.bai.HolyIns.service.ContentService;
import com.bai.HolyIns.service.ImageService;
import com.bai.HolyIns.state.ActivityStatus;
import com.bai.HolyIns.state.ThreadState;
import com.bai.HolyIns.utils.SPUtils;
import com.bai.HolyIns.utils.ToastUtils;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PersonInfoFragment extends Fragment {
    //widgets
    private TabLayout mTableLayout;
    private ViewPager mViewPager;
    private MyAdapter adapter;
    private List<String> mTitle;
    private List<Fragment> mFragment;
    private TextView person_page_name;
    private ImageView person_page_head_pic;
    private TextView person_page_subscribers_number;
    private ImageButton person_page_options_btn;
    private ImageButton person_page_log_out_btn;
    //other
    private UserInfo now_user_info;
    public static String updated_head_pic_path;
    //param
    private static final String ARG_Now_UserInfo = "Now_User_Info";
    //service
    private ImageService imageService = new ImageService();
    private ContentService contentService = new ContentService();
    private BaseService baseService = new BaseService();
    //handler
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            if (msg.what == ThreadState.HEAD_PIC) {
                //个人信息页面的头像
                Bitmap bitmap = bundle.getParcelable("bitmap");
                person_page_head_pic.setImageBitmap(bitmap);
            } else if (msg.what == ThreadState.GET_SUBSCRIBE) {
                List<Integer> subscribed_user_list = JSON.parseArray(bundle.getString("get_subscribe_status"), Integer.class);
                //设置关注的人数
                person_page_subscribers_number.setText("关注的人:" + subscribed_user_list.size());
                //如果一个人都没关注的话不应该跳转到那个界面，否则就会把系统中的所有人都查出来
                if (subscribed_user_list.size() == 0) {
                    ToastUtils.show_ordinary_toast(getContext(), "还没有关注任何人哦", Toast.LENGTH_SHORT);
                } else {
                    //设置关注人的点击跳转到另一个界面
                    person_page_subscribers_number.setOnClickListener(v -> {
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


    public PersonInfoFragment() {
        // Required empty public constructor
    }

    public static PersonInfoFragment newInstance(UserInfo now_userInfo) {
        PersonInfoFragment fragment = new PersonInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_Now_UserInfo, now_userInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            now_user_info = (UserInfo) getArguments().getSerializable(ARG_Now_UserInfo);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getInfo();
        person_page_options_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), PersonInfoChangeActivity.class);
            intent.putExtra("now_user_info", now_user_info);
            startActivity(intent);
            //把我结束了，然后修改信息完后重进MainActivity并取得新的信息
            //getActivity().finish();
        });
        person_page_log_out_btn.setOnClickListener(v -> {
            //退出登录就把本地的session删除掉
            SPUtils.remove(getContext(), "session_id");
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

    }


    public void getInfo() {   //如果从那个修改信息界面使用static变量传回来的新的头像地址不是null的话，就让这个页面使用新的头像地址
        if (updated_head_pic_path != null) {
            now_user_info.setHeadPicturePath(updated_head_pic_path);
        }
        imageService.HeadPicGet(handler, now_user_info.getHeadPicturePath());
        //设置姓名和头像
        person_page_name.setText(now_user_info.getName());
        //System.out.println("=================path="+now_user_info.getHeadPicturePath());
        //获取关注的人
        contentService.getSubscribe(now_user_info.getUser_id(), handler);
    }


    @Override
    public void onResume() {
        super.onResume();
        person_page_head_pic.setImageResource(R.mipmap.default_photo);
        Log.i("lifecyclessss", "onresume");
        getInfo();
    }

    @Override
    public void onPause() {
        Log.i("lifecyclessss", "onpause");
        super.onPause();
    }

    //隐藏的时候重新加载关注的人的信息，以做到实时刷新
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.i("lifecyclessss", "onhidden");
        //获取关注的人
        contentService.getSubscribe(now_user_info.getUser_id(), handler);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /*now_user_info.setUser_id(1);
        now_user_info.setName("bai");
        now_user_info.setAccount("111");
        now_user_info.setHeadPicturePath("E:/holyins/headpic/defaultheadpic.jpg");
        now_user_info.setSex("男");
        now_user_info.setE_mail("1748383809@qq.com");
        now_user_info.setRegisterTime(new Date());*/

        View view = inflater.inflate(R.layout.fragment_person_info, container, false);
        person_page_head_pic = view.findViewById(R.id.person_page_head_pic);
        person_page_name = view.findViewById(R.id.person_page_name);
        mTableLayout = view.findViewById(R.id.person_page_tab);
        mViewPager = view.findViewById(R.id.person_page_pager);
        person_page_subscribers_number = view.findViewById(R.id.person_page_subscribers_number);
        person_page_options_btn = view.findViewById(R.id.person_page_options_btn);
        person_page_log_out_btn = view.findViewById(R.id.person_page_log_out_btn);

        //标题栏数组
        mTitle = new ArrayList<>();
        mTitle.add("我的帖子");
        mTitle.add("我的喜欢");
        mTitle.add("我的转发");
        mTitle.add("详细信息");
        //fragment集合
        mFragment = new ArrayList<>();
        mFragment.add(PersonPostFragment.newInstance(now_user_info, ActivityStatus.FIND_OWN_POST));
        mFragment.add(PersonPostFragment.newInstance(now_user_info, ActivityStatus.FIND_OWN_LIKE_POST));
        mFragment.add(PersonPostFragment.newInstance(now_user_info, ActivityStatus.FIND_OWN_FORWARDING_POST));
        mFragment.add(PersonInfoDetailsFragment.newInstance(now_user_info));
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
            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
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