package com.bai.HolyIns.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.bai.HolyIns.R;
import com.bai.HolyIns.adapter.SubscribersAdapter;
import com.bai.HolyIns.pojo.UserInfo;
import com.bai.HolyIns.service.BaseService;
import com.bai.HolyIns.state.ThreadState;

import java.util.ArrayList;
import java.util.List;

public class SubscribersActivity extends AppCompatActivity {
    //widgets
    private ListView subscribers_list_view;
    //service
    private BaseService baseService=new BaseService();
    //todo,now_user_info需要往进传
    private UserInfo now_user_info = new UserInfo();
    private List<Integer> subscribed_user_list;
    private List<UserInfo> subscribed_user_info=new ArrayList<>();
    //adapter
    private SubscribersAdapter subscribersAdapter;
    //handler
    private Handler handler=new Handler(msg -> {
        Bundle bundle=msg.getData();
        if (msg.what== ThreadState.FIND_USER_BY_IDS){
            List<UserInfo> userInfos=JSON.parseArray(bundle.getString("user_infos"),UserInfo.class);
            subscribersAdapter=new SubscribersAdapter(userInfos,this,now_user_info);
            subscribers_list_view.setAdapter(subscribersAdapter);
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
        setContentView(R.layout.activity_subscribers);
        subscribers_list_view=findViewById(R.id.subscribers_list_view);
        now_user_info.setUser_id(1);

        Intent intent = getIntent();
        subscribed_user_list = intent.getIntegerArrayListExtra("subscribed_user_list");

        baseService.UserInfosGetByIDs(handler,subscribed_user_list);

    }
}