package com.bai.HolyIns.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.bai.HolyIns.R;
import com.bai.HolyIns.activity.CommentActivity;
import com.bai.HolyIns.pojo.Comment;
import com.bai.HolyIns.pojo.ForwardUserPost;
import com.bai.HolyIns.pojo.Forwarding;
import com.bai.HolyIns.pojo.UserInfo;
import com.bai.HolyIns.pojo.UserPost;
import com.bai.HolyIns.service.BaseService;
import com.bai.HolyIns.service.ContentService;
import com.bai.HolyIns.service.ImageService;
import com.bai.HolyIns.state.ActivityStatus;
import com.bai.HolyIns.state.ThreadState;
import com.bai.HolyIns.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PersonOwnForwardsPostAdapter extends BaseAdapter {
    private List<ForwardUserPost> posts;
    private Context context;
    private UserInfo now_user_info;
    private ImageService imageService = new ImageService();
    private BaseService baseService = new BaseService();
    private ContentService contentService = new ContentService();
    private HashMap<Integer, View> viewHashMap = new HashMap<>();

    public PersonOwnForwardsPostAdapter(List<ForwardUserPost> posts, Context context, UserInfo now_user_info) {
        this.posts = posts;
        this.context = context;
        this.now_user_info = now_user_info;
        Collections.reverse(posts);
    }

    static class ViewHolder {
        //转发人信息
        private ImageView person_forward_post_head_pic;
        private TextView person_forward_post_user_name, person_forward_post_text;
        //被转发人信息
        private ImageView person_forwarded_post_head_pic;
        private TextView person_forwarded_post_user_name;
        private ImageView person_forwarded_item_photo;
        private TextView person_forwarded_post_item_first_comment_name, person_forwarded_post_item_first_comment;
        private ImageButton person_forwarded_item_comment_image_btn;
        private TextView person_forwarded_item_photo_description;
        private TextView person_forward_time;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ForwardUserPost forwardUserPost = posts.get(position);
        UserPost currentPost = forwardUserPost.getUserPost();
        Forwarding currentForward = forwardUserPost.getForwarding();

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_person_forward_post, parent, false);
            //转发人
            viewHolder.person_forward_post_head_pic = convertView.findViewById(R.id.person_forward_post_head_pic);
            viewHolder.person_forward_post_user_name = convertView.findViewById(R.id.person_forward_post_user_name);
            viewHolder.person_forward_post_text = convertView.findViewById(R.id.person_forward_post_text);
            viewHolder.person_forward_time = convertView.findViewById(R.id.person_forward_time);
            //被转发的帖子
            viewHolder.person_forwarded_post_head_pic = convertView.findViewById(R.id.person_forwarded_post_head_pic);
            viewHolder.person_forwarded_post_user_name = convertView.findViewById(R.id.person_forwarded_post_user_name);
            viewHolder.person_forwarded_item_photo = convertView.findViewById(R.id.person_forwarded_item_photo);
            viewHolder.person_forwarded_post_item_first_comment_name = convertView.findViewById(R.id.person_forwarded_post_item_first_comment_name);
            viewHolder.person_forwarded_post_item_first_comment = convertView.findViewById(R.id.person_forwarded_post_item_first_comment);
            viewHolder.person_forwarded_item_comment_image_btn = convertView.findViewById(R.id.person_forwarded_item_comment_image_btn);
            viewHolder.person_forwarded_item_photo_description = convertView.findViewById(R.id.person_forwarded_item_photo_description);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //加载评论用户信息
        Handler get_user_info_handler = new Handler(msg -> {
            Bundle bundle = msg.getData();
            if (msg.what == ThreadState.USER_INFO) {
                UserInfo userInfo = JSON.parseObject(bundle.getString("user_info"), UserInfo.class);
                viewHolder.person_forwarded_post_item_first_comment_name.setText(userInfo.getName());
            }
            return true;
        });
        //加载完成之后再回调
        Handler handler = new Handler(msg -> {
            Bundle bundle = msg.getData();
            if (msg.what == ThreadState.PHOTO) {
                Bitmap bitmap = bundle.getParcelable("bitmap");
                viewHolder.person_forwarded_item_photo.setImageBitmap(bitmap);
            } else if (msg.what == ThreadState.HEAD_PIC) {
                Bitmap bitmap = bundle.getParcelable("bitmap");
                viewHolder.person_forwarded_post_head_pic.setImageBitmap(bitmap);
            } else if (msg.what == ThreadState.NOW_USER_HEAD_PIC) {
                Bitmap bitmap = bundle.getParcelable("bitmap");
                viewHolder.person_forward_post_head_pic.setImageBitmap(bitmap);
            } else if (msg.what == ThreadState.COMMENT) {
                if (bundle.getString("comment_status").equals(ActivityStatus.STRING_COMMENT_SUCCESS)) {
                    ToastUtils.show_ordinary_toast(context, "评论成功", Toast.LENGTH_SHORT);
                } else {
                    ToastUtils.show_ordinary_toast(context, "评论失败", Toast.LENGTH_SHORT);
                }
            } else if (msg.what == ThreadState.GET_COMMENT) {
                if (bundle.getString("get_comment_status").equals(ActivityStatus.STRING_GET_COMMENT_FAILED)) {
                    ToastUtils.show_ordinary_toast(context, "拉取评论失败", Toast.LENGTH_SHORT);
                } else {
                    List<Comment> comments = JSONObject.parseObject(bundle.getString("get_comment_status"), new TypeReference<ArrayList<Comment>>() {
                    });
                    viewHolder.person_forwarded_post_item_first_comment.setText(comments.get(0).getComment_text());
                    baseService.UserInfoGet(get_user_info_handler, comments.get(0).getComment_user_id());
                }
            }
            return true;
        });
        //获取当前用户的头像
        imageService.HeadPicGetNowUser(handler, now_user_info.getHeadPicturePath());
        //获取发帖者头像
        imageService.HeadPicGet(handler, currentPost.getUserInfo().getHeadPicturePath());
        //开始加载被评论人图片
        imageService.PhotoGet(handler, currentPost.getPhoto().getPhoto_path());
        //获取评论第一个人的信息
        if (currentPost.getComment_id().size() != 0) {
            contentService.getComment(currentPost.getComment_id(), handler);
        } else {
            viewHolder.person_forwarded_post_item_first_comment.setText("无");
            viewHolder.person_forwarded_post_item_first_comment_name.setText("无");
        }
        //转发时间
        viewHolder.person_forward_time.setText(currentForward.getForward_time().toString());
        //当前用户
        viewHolder.person_forward_post_user_name.setText(now_user_info.getName());
        viewHolder.person_forward_post_text.setText(currentForward.getForward_text());
        //被转发的人
        viewHolder.person_forwarded_post_user_name.setText(currentPost.getUserInfo().getName());
        viewHolder.person_forwarded_item_photo_description.setText(currentPost.getPhoto().getPhoto_description());
        //评论监听
        viewHolder.person_forwarded_item_comment_image_btn.setOnClickListener(v -> {
            if (currentPost.getComment_id().size() != 0) {
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("now_user_id", now_user_info.getUser_id());
                intent.putExtra("photo_id", currentPost.getPhoto_id());
                context.startActivity(intent);
            } else {
                ToastUtils.show_ordinary_toast(context, "来添加第一条评论吧", Toast.LENGTH_SHORT);
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("now_user_id", now_user_info.getUser_id());
                intent.putExtra("photo_id", currentPost.getPhoto_id());
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
