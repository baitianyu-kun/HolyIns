package com.bai.HolyIns.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.bai.HolyIns.R;
import com.bai.HolyIns.fragment.MainPageFragment;
import com.bai.HolyIns.fragment.PersonInfoFragment;
import com.bai.HolyIns.fragment.PostPageFragment;
import com.bai.HolyIns.fragment.RecommendFragment;
import com.bai.HolyIns.fragment.SearchPageFragment;
import com.bai.HolyIns.pojo.UserInfo;
import com.bai.HolyIns.service.BaseService;
import com.bai.HolyIns.state.ThreadState;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //widgets
    public BottomNavigationView bottomNavigationView;

    //normal variable
    private int lastIndex;
    private List<Fragment> mFragments;
    private UserInfo now_user_info = new UserInfo();
    //service
    private BaseService baseService = new BaseService();
    //handler
    private Handler handler = new Handler(msg -> {
        Bundle bundle = msg.getData();
        if (msg.what == ThreadState.FIND_USER_BY_ACCOUNT) {
            UserInfo userInfo = JSON.parseObject(bundle.getString("user_info_by_account"), UserInfo.class);
            now_user_info = userInfo;
            initFragment();
        } else if (msg.what == ThreadState.FIND_USER_BY_EMAIL) {
            UserInfo userInfo = JSON.parseObject(bundle.getString("user_info_by_e_mail"), UserInfo.class);
            now_user_info = userInfo;
            initFragment();
        }
        return true;
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在setContentView之前
        //状态栏中的文字颜色和图标颜色，需要android系统6.0以上，而且目前只有一种可以修改（一种是深色，一种是浅色即白色）
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //修改为深色，因为我们把状态栏的背景色修改为主题色白色，默认的文字及图标颜色为白色，导致看不到了。
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_main);
        //获取当前用户信息
        Intent intent = getIntent();
        if (intent.getStringExtra("e_mail") != null) {
            now_user_info.setE_mail(intent.getStringExtra("e_mail"));
            baseService.UserInfoGetByEmail(handler, now_user_info.getE_mail());
        } else if (intent.getStringExtra("account") != null) {
            now_user_info.setAccount(intent.getStringExtra("account"));
            baseService.UserInfoGetByAccount(handler, now_user_info.getAccount());
        }

        //initFragment();//初始化fragment
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_recommendpage:
                        ChangeFragment(0);
                        break;
                    case R.id.navigation_mainpage:
                        ChangeFragment(1);
                        break;
                    case R.id.navigation_searchpage:
                        ChangeFragment(2);
                        break;
                    case R.id.navigation_personinfo:
                        ChangeFragment(3);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void initFragment()//初始化fragment
    {
        mFragments = new ArrayList<>();
        mFragments.add(RecommendFragment.newInstance(now_user_info));
        mFragments.add(MainPageFragment.newInstance(now_user_info));
        mFragments.add(SearchPageFragment.newInstance(now_user_info));
        mFragments.add(PersonInfoFragment.newInstance(now_user_info));//传进去用户名
        // 初始化展示第一个Fragment
        ChangeFragment(0);
    }

    public void removeFragment(int position) {
        Fragment currentfragment = mFragments.get(position);
        getSupportFragmentManager().beginTransaction().remove(currentfragment).commit();
        mFragments.remove(position);//去掉首个fragment

    }

    public void addFragment(int position, Fragment fragment) {
        mFragments.add(position, fragment);//添加fragment到首个位置
    }

    public void ChangeFragment(int position)//更改fragment
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = mFragments.get(position);
        Fragment lastFragment = mFragments.get(lastIndex);
        lastIndex = position;//上一个fragment的位置
        ft.hide(lastFragment);
        if (!currentFragment.isAdded()) {//判断当前页面有没有被加进来
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
            ft.add(R.id.fragmentlayout, currentFragment);
        }
        ft.show(currentFragment);
        ft.commit();
    }
}