package com.bai.HolyIns.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bai.HolyIns.R;
import com.bai.HolyIns.pojo.UserInfo;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchPageFragment extends Fragment {
    //widgets
    private TabLayout mTableLayout;
    private ViewPager mViewPager;
    //fragment适配器
    private MyAdapter adapter;
    //mtablelayout的标题
    private List<String> mTitle;
    //要创建fragment的集合
    private List<Fragment> mFragment;
    //now_user_info
    private UserInfo now_user_info;
    //param
    private static final String ARG_Now_UserInfo = "Now_User_Info";


    public SearchPageFragment() {
        // Required empty public constructor
    }

    public static SearchPageFragment newInstance(UserInfo now_userInfo) {
        SearchPageFragment fragment = new SearchPageFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_page, container, false);
        mTableLayout = view.findViewById(R.id.search_page_tab);
        mViewPager = view.findViewById(R.id.search_page_pager);
        //标题栏数组
        mTitle = new ArrayList<>();
        mTitle.add("搜索用户");
        mTitle.add("搜索帖子");
        //fragment集合
        mFragment = new ArrayList<>();
        mFragment.add(SearchUserFragment.newInstance(now_user_info));
        mFragment.add(SearchPostFragment.newInstance(now_user_info));
        //在activity中使用 getSupportFragmentManager(),这里是Fragment中使用如下方法
        adapter = new MyAdapter(getFragmentManager());
        mViewPager.setAdapter(adapter);
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

    @Override
    public void onPause() {
        super.onPause();
    }
}