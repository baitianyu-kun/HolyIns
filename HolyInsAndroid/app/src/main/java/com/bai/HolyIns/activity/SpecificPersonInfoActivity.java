package com.bai.HolyIns.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.bai.HolyIns.R;
import com.bai.HolyIns.fragment.PersonInfoFragment;
import com.bai.HolyIns.fragment.SpecificPersonInfoFragment;
import com.bai.HolyIns.pojo.UserInfo;

/*
点击头像进去之后的个人信息页面
 */
public class SpecificPersonInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        UserInfo now_user_info = (UserInfo) intent.getSerializableExtra("now_user_info");
        UserInfo this_user_info = (UserInfo) intent.getSerializableExtra("this_user_info");

        //在setContentView之前
        //状态栏中的文字颜色和图标颜色，需要android系统6.0以上，而且目前只有一种可以修改（一种是深色，一种是浅色即白色）
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //修改为深色，因为我们把状态栏的背景色修改为主题色白色，默认的文字及图标颜色为白色，导致看不到了。
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_specific_person_info);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        SpecificPersonInfoFragment specificPersonInfoFragment = SpecificPersonInfoFragment.newInstance(now_user_info, this_user_info);
        ft.add(R.id.specific_person_info_fragment_layout, specificPersonInfoFragment);
        ft.show(specificPersonInfoFragment);
        ft.commit();
    }
}