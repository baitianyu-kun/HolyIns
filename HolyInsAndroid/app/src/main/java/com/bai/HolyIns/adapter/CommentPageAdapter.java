package com.bai.HolyIns.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bai.HolyIns.R;
import com.bai.HolyIns.pojo.Comment;
import com.bai.HolyIns.pojo.HashTag;
import com.bai.HolyIns.pojo.UserInfo;
import com.bai.HolyIns.service.BaseService;
import com.bai.HolyIns.service.ContentService;
import com.bai.HolyIns.service.ImageService;
import com.bai.HolyIns.state.ThreadState;
import com.bai.HolyIns.utils.ToastUtils;

import java.util.Date;
import java.util.List;

/*
发送评论都在CommentActivity中
 */
public class CommentPageAdapter extends BaseAdapter {
    private List<Comment> commentList;
    private Context context;
    private UserInfo now_user_info;
    private BaseService baseService = new BaseService();
    private ImageService imageService = new ImageService();
    private ContentService contentService = new ContentService();

    public CommentPageAdapter(List<Comment> commentList, Context context, UserInfo now_user_info) {
        this.commentList = commentList;
        this.context = context;
        this.now_user_info = now_user_info;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        private ImageView comment_page_head_pic;
        private TextView comment_page_comment_content;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comment currentComment = commentList.get(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment_page, parent, false);
            viewHolder.comment_page_comment_content = convertView.findViewById(R.id.commentpage_comment_content);
            viewHolder.comment_page_head_pic = convertView.findViewById(R.id.comment_page_head_pic);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置头像
        Handler handler = new Handler(msg -> {
            Bundle bundle = msg.getData();
            if (msg.what == ThreadState.HEAD_PIC) {
                viewHolder.comment_page_head_pic.setImageBitmap(bundle.getParcelable("bitmap"));
            }
            return true;
        });
        //设置用户信息,比较特殊先单独写一个handler吧
        Handler handler_get_user_info = new Handler(msg -> {
            Bundle bundle = msg.getData();
            if (msg.what == ThreadState.USER_INFO) {
                UserInfo userInfo = JSON.parseObject(bundle.getString("user_info"), UserInfo.class);
                //得到评论用户的信息之后再去加载其头像
                imageService.HeadPicGet(handler, userInfo.getHeadPicturePath());
            }
            return true;
        });
        //拉取用户信息
        baseService.UserInfoGet(handler_get_user_info, currentComment.getComment_user_id());
        viewHolder.comment_page_comment_content.setText(currentComment.getComment_text());
        return convertView;
    }
}
