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
import com.bai.HolyIns.service.BaseService;
import com.bai.HolyIns.service.ImageService;
import com.bai.HolyIns.service.SearchService;
import com.bai.HolyIns.state.ThreadState;

import java.util.List;

public class SearchUserAdapter extends BaseAdapter {

    private List<UserInfo> userInfos;
    private Context context;
    private UserInfo now_user_info;

    //service
    private ImageService imageService = new ImageService();

    public SearchUserAdapter(List<UserInfo> userInfos, Context context, UserInfo now_user_info) {
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
        private ImageView search_user_page_head_pic;
        private TextView search_user_page_name, search_user_page_account, search_user_page_email;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserInfo currentUser = userInfos.get(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_search_user_item, parent, false);
            viewHolder.search_user_page_account = convertView.findViewById(R.id.search_user_page_account);
            viewHolder.search_user_page_email = convertView.findViewById(R.id.search_user_page_email);
            viewHolder.search_user_page_head_pic = convertView.findViewById(R.id.search_user_page_head_pic);
            viewHolder.search_user_page_name = convertView.findViewById(R.id.search_user_page_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置搜索结果的头像
        Handler handler = new Handler(msg -> {
            Bundle bundle = msg.getData();
            if (msg.what== ThreadState.HEAD_PIC){
                //设置头像
                viewHolder.search_user_page_head_pic.setImageBitmap(bundle.getParcelable("bitmap"));
            }
            return true;
        });
        //拉取头像
        imageService.HeadPicGet(handler,userInfos.get(position).getHeadPicturePath());
        //设置用户名、账号和邮件
        viewHolder.search_user_page_name.setText(userInfos.get(position).getName());
        viewHolder.search_user_page_email.setText(userInfos.get(position).getE_mail());
        viewHolder.search_user_page_account.setText(userInfos.get(position).getAccount());
        //头像监听
        viewHolder.search_user_page_head_pic.setOnClickListener(v -> {
            Intent intent=new Intent(context,SpecificPersonInfoActivity.class);
            intent.putExtra("now_user_info",now_user_info);
            intent.putExtra("this_user_info",currentUser);
            context.startActivity(intent);
        });
        return convertView;
    }
}
