package com.bai.HolyIns.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bai.HolyIns.R;
import com.bai.HolyIns.activity.SpecificPersonInfoActivity;
import com.bai.HolyIns.pojo.UserInfo;
import com.bai.HolyIns.service.ImageService;
import com.bai.HolyIns.state.ThreadState;

import java.util.List;

public class SubscribersAdapter extends BaseAdapter {
    private List<UserInfo> userInfos;
    private Context context;
    private UserInfo now_user_info;

    //service
    private ImageService imageService = new ImageService();

    public SubscribersAdapter(List<UserInfo> userInfos, Context context, UserInfo now_user_info) {
        this.userInfos = userInfos;
        this.context = context;
        this.now_user_info = now_user_info;
    }

    @Override
    public int getCount() {
        return userInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return userInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        private ImageView subscribers_page_head_pic;
        private TextView subscribers_page_name, subscribers_page_account, subscribers_page_email;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserInfo currentUser = userInfos.get(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_subscribers, parent, false);
            viewHolder.subscribers_page_account = convertView.findViewById(R.id.subscribers_page_account);
            viewHolder.subscribers_page_email = convertView.findViewById(R.id.subscribers_page_email);
            viewHolder.subscribers_page_head_pic = convertView.findViewById(R.id.subscribers_page_head_pic);
            viewHolder.subscribers_page_name = convertView.findViewById(R.id.subscribers_page_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置关注人的头像
        Handler handler = new Handler(msg -> {
            Bundle bundle = msg.getData();
            if (msg.what== ThreadState.HEAD_PIC){
                //设置头像
                viewHolder.subscribers_page_head_pic.setImageBitmap(bundle.getParcelable("bitmap"));

            }
            return true;
        });
        //拉取头像
        imageService.HeadPicGet(handler,userInfos.get(position).getHeadPicturePath());
        //设置用户名、账号和邮件
        viewHolder.subscribers_page_name.setText(currentUser.getName());
        viewHolder.subscribers_page_email.setText(currentUser.getE_mail());
        viewHolder.subscribers_page_account.setText(currentUser.getAccount());
        //设置头像的监听
        viewHolder.subscribers_page_head_pic.setOnClickListener(v -> {
            Intent intent=new Intent(context,SpecificPersonInfoActivity.class);
            intent.putExtra("now_user_info",now_user_info);
            intent.putExtra("this_user_info",currentUser);
            context.startActivity(intent);
        });
        return convertView;
    }
}
