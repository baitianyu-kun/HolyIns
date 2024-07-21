package com.bai.HolyIns.adapter;

import android.app.AlertDialog;
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
import android.widget.EditText;
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
import com.bai.HolyIns.pojo.ForwardPhotoID;
import com.bai.HolyIns.pojo.Forwarding;
import com.bai.HolyIns.pojo.LikePhotoID;
import com.bai.HolyIns.pojo.LikeUserPost;
import com.bai.HolyIns.pojo.Likes;
import com.bai.HolyIns.pojo.Subscribe;
import com.bai.HolyIns.pojo.UserInfo;
import com.bai.HolyIns.pojo.UserPost;
import com.bai.HolyIns.service.BaseService;
import com.bai.HolyIns.service.ContentService;
import com.bai.HolyIns.service.ImageService;
import com.bai.HolyIns.state.ActivityStatus;
import com.bai.HolyIns.state.ThreadState;
import com.bai.HolyIns.utils.DialogUtils;
import com.bai.HolyIns.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/*
这个是个人信息页面的下方的三个子页面，分别是：我的帖子，我的点赞的帖子，我的转发的帖子，这是那个adapter，就是比那个postpage多了一个时间的功能
 */
public class PersonPostItemAdapter extends BaseAdapter {
    private List<UserPost> posts;
    private List<ForwardPhotoID> forwardPhotoIDS;
    private List<LikePhotoID> likePhotoIDS;
    private Context context;//把上下文传进来，用来获取convertview的组件
    private UserInfo now_user_info;
    private ImageService imageService = new ImageService();
    private BaseService baseService = new BaseService();
    private ContentService contentService = new ContentService();
    //转发信息
    private Forwarding forwarding;
    //点赞信息
    private Likes likes;

    public PersonPostItemAdapter(List<UserPost> posts, Context context, UserInfo now_user_info,Object type) {
        this.posts = posts;
        this.context = context;
        this.now_user_info = now_user_info;
        if (type!=null){
            //判断传入的是哪个就用哪个
            if (type instanceof Forwarding){
                this.forwarding=(Forwarding)type;
                System.out.println("forwarding="+forwarding.toString());
            }
            if (type instanceof Likes){
                this.likes=(Likes)type;
                System.out.println("likes="+likes.toString());
            }
        }
        Collections.reverse(posts);
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

    static class ViewHolder {
        private Button subscribe_btn;
        private ImageButton like_btn, comment_btn, forward_btn;
        public ImageView person_post_item_head_pic, person_post_item_photo;
        public TextView person_post_item_user_name, person_post_item_photo_description, person_post_item_first_comment_name, person_post_item_first_comment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //当前点赞的like_id;
        final int[] currentLikeID = new int[1];
        int ALREADY_LIKED = 999;
        int NOT_LIKED = 9990;
        final int[] LIKE_STATUS = new int[1];
        LIKE_STATUS[0] = NOT_LIKED;
        UserPost currentPost = posts.get(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_person_post, parent, false);
            viewHolder.subscribe_btn = convertView.findViewById(R.id.person_post_item_subscribe_btn);
            viewHolder.like_btn = convertView.findViewById(R.id.person_post_item_like_image_btn);
            viewHolder.comment_btn = convertView.findViewById(R.id.person_post_item_comment_image_btn);
            viewHolder.forward_btn = convertView.findViewById(R.id.person_post_item_forward_image_btn);
            viewHolder.person_post_item_head_pic = convertView.findViewById(R.id.person_post_item_head_pic);
            viewHolder.person_post_item_photo = convertView.findViewById(R.id.person_post_item_photo);
            viewHolder.person_post_item_user_name = convertView.findViewById(R.id.person_post_item_user_name);
            viewHolder.person_post_item_photo_description = convertView.findViewById(R.id.person_post_item_photo_description);
            viewHolder.person_post_item_first_comment = convertView.findViewById(R.id.person_post_item_first_comment);
            viewHolder.person_post_item_first_comment_name = convertView.findViewById(R.id.person_post_item_first_comment_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //加载评论用户信息
        Handler get_user_info_handler = new Handler(msg -> {
            Bundle bundle = msg.getData();
            if (msg.what == ThreadState.USER_INFO) {
                UserInfo userInfo = JSON.parseObject(bundle.getString("user_info"), UserInfo.class);
                viewHolder.person_post_item_first_comment_name.setText(userInfo.getName());
            }
            return true;
        });
        //加载完成之后再回调
        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                Bundle bundle = msg.getData();
                if (msg.what == ThreadState.PHOTO) {
                    Bitmap bitmap = bundle.getParcelable("bitmap");
                    viewHolder.person_post_item_photo.setImageBitmap(bitmap);
                } else if (msg.what == ThreadState.HEAD_PIC) {
                    Bitmap bitmap1 = bundle.getParcelable("bitmap");
                    viewHolder.person_post_item_head_pic.setImageBitmap(bitmap1);
                } else if (msg.what == ThreadState.SUBSCRIBE) {
                    if (bundle.getString("subscribe_status").equals(ActivityStatus.STRING_SUBSCRIBE_SUCCESS)) {
                        ToastUtils.show_ordinary_toast(context, "订阅成功", Toast.LENGTH_SHORT);
                        viewHolder.subscribe_btn.setText("已关注");
                    } else {
                        ToastUtils.show_ordinary_toast(context, "订阅失败", Toast.LENGTH_SHORT);
                    }
                } else if (msg.what == ThreadState.LIKE_USER_POST) {
                    if (bundle.getString("like_status").equals(ActivityStatus.STRING_LIKE_FAILED)) {
                        ToastUtils.show_ordinary_toast(context, "点赞失败", Toast.LENGTH_SHORT);
                    } else {
                        int get_like_id = Integer.parseInt(bundle.getString("like_status"));
                        currentLikeID[0] = get_like_id;
                        ToastUtils.show_ordinary_toast(context, "点赞成功", Toast.LENGTH_SHORT);
                        viewHolder.like_btn.setImageResource(R.mipmap.icon_already_like);
                    }
                } else if (msg.what == ThreadState.FORWARD) {
                    if (bundle.getString("forwarding_status").equals(ActivityStatus.STRING_FORWARD_SUCCESS)) {
                        ToastUtils.show_ordinary_toast(context, "转发成功", Toast.LENGTH_SHORT);
                    } else {
                        ToastUtils.show_ordinary_toast(context, "转发失败", Toast.LENGTH_SHORT);
                    }
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
                        //Log.i("mainpagepostadapter",bundle.getString("get_comment_status"));
                        viewHolder.person_post_item_first_comment.setText(comments.get(0).getComment_text());
                        baseService.UserInfoGet(get_user_info_handler, comments.get(0).getComment_user_id());
                    }
                } else if (msg.what == ThreadState.UN_SUBSCRIBE) {
                    if (bundle.getString("unsubscribe_status").equals(ActivityStatus.STRING_UNSUBSCRIBE_SUCCESS)) {
                        ToastUtils.show_ordinary_toast(context, "取消关注成功", Toast.LENGTH_SHORT);
                        viewHolder.subscribe_btn.setText("关注");
                    } else {
                        ToastUtils.show_ordinary_toast(context, "取消关注失败", Toast.LENGTH_SHORT);
                    }
                } else if (msg.what == ThreadState.CANCEL_LIKE) {
                    if (bundle.getString("cancel_like_status").equals(ActivityStatus.STRING_CANCEL_LIKE_SUCCESS)) {
                        ToastUtils.show_ordinary_toast(context, "取消点赞成功", Toast.LENGTH_SHORT);
                    } else {
                        ToastUtils.show_ordinary_toast(context, "取消点赞失败", Toast.LENGTH_SHORT);
                    }
                }
                return true;
            }
        });
        //先把所有的都置为没有点赞
        viewHolder.like_btn.setImageResource(R.mipmap.icon_like);
        //开始加载图片
        imageService.PhotoGet(handler, currentPost.getPhoto().getPhoto_path());
        //获取评论第一个人的信息
        //System.out.println("==============================" + currentPost.getComment_id());
        if (currentPost.getComment_id().size() != 0) {
            contentService.getComment(currentPost.getComment_id(), handler);
        }else {
            viewHolder.person_post_item_first_comment.setText("无");
            viewHolder.person_post_item_first_comment_name.setText("无");
        }
        //获取发帖者头像
        imageService.HeadPicGet(handler, currentPost.getUserInfo().getHeadPicturePath());
        //判断当前用户是否点赞过该帖子,就是看看当前照片点赞的人中是否有我自己
        for (Likes like : currentPost.getLikes()) {
            if (like.getLike_user_id() == now_user_info.getUser_id()) {
                LIKE_STATUS[0] = ALREADY_LIKED;
                //如果以前点赞过这个帖子就把当前的like_id设置为这张帖子
                currentLikeID[0] = like.getLike_id();
                viewHolder.like_btn.setImageResource(R.mipmap.icon_already_like);
            }
        }
        viewHolder.person_post_item_user_name.setText(currentPost.getUserInfo().getName());
        viewHolder.person_post_item_photo_description.setText(currentPost.getPhoto().getPhoto_description());
        //关注监听
        viewHolder.subscribe_btn.setText("已关注");
        viewHolder.subscribe_btn.setOnClickListener(v -> {
            if (viewHolder.subscribe_btn.getText().toString().equals("已关注")) {
                AlertDialog.Builder sureDialog = new AlertDialog.Builder(context);
                sureDialog.setMessage("确实要取消关注吗？")
                        .setTitle("取消关注");
                sureDialog.setPositiveButton("确定", (dialog, which) -> {
                    contentService.unSubscribe(now_user_info.getUser_id(), currentPost.getUserInfo().getUser_id(), handler);
                    viewHolder.subscribe_btn.setText("关注");
                });
                sureDialog.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
                sureDialog.show();
            } else {
                Subscribe subscribe = new Subscribe();
                subscribe.setNow_user_id(now_user_info.getUser_id());
                subscribe.setSubscribe_time(new Date());
                subscribe.setSubscribe_userid(currentPost.getUserInfo().getUser_id());
                contentService.Subscribe(subscribe, handler);
            }
        });
        //点赞监听
        viewHolder.like_btn.setOnClickListener(v -> {
            if (LIKE_STATUS[0] == NOT_LIKED) {
                Likes likes = new Likes();
                likes.setDate_created(new Date());
                likes.setDate_updated(new Date());
                likes.setLike_user_id(now_user_info.getUser_id());
                likes.setPhoto_id(currentPost.getPhoto_id());
                contentService.likeUserPost(likes, handler);
                LIKE_STATUS[0] = ALREADY_LIKED;
                viewHolder.like_btn.setImageResource(R.mipmap.icon_already_like);
            } else {
                contentService.cancelLikePost(currentLikeID[0], handler);
                LIKE_STATUS[0] = NOT_LIKED;
                viewHolder.like_btn.setImageResource(R.mipmap.icon_like);
            }
        });
        //转发监听
        viewHolder.forward_btn.setOnClickListener(v -> {
            View dialog_forwarding = View.inflate(context, R.layout.dialog_forwarding, null);
            Button send_forward_btn = dialog_forwarding.findViewById(R.id.main_page_forward_send_btn);
            EditText forward_et = dialog_forwarding.findViewById(R.id.main_page_forward_et);
            DialogUtils.show_custom_dialog(dialog_forwarding, context, true);
            send_forward_btn.setOnClickListener(v1 -> {
                Forwarding forwarding = new Forwarding();
                forwarding.setForward_text(forward_et.getText().toString());
                forwarding.setForward_time(new Date());
                forwarding.setForward_user_id(now_user_info.getUser_id());
                forwarding.setPhoto_id(currentPost.getPhoto_id());
                contentService.Forwarding(forwarding, handler);
            });
        });
        //评论监听
        viewHolder.comment_btn.setOnClickListener(v -> {
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
