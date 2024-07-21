package com.bai.HolyIns.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.bai.HolyIns.R;
import com.bai.HolyIns.adapter.CommentPageAdapter;
import com.bai.HolyIns.pojo.Comment;
import com.bai.HolyIns.pojo.Photo;
import com.bai.HolyIns.pojo.UserInfo;
import com.bai.HolyIns.service.ContentService;
import com.bai.HolyIns.state.ActivityStatus;
import com.bai.HolyIns.state.ThreadState;
import com.bai.HolyIns.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentActivity extends AppCompatActivity {
    private ListView comment_list;
    private Button commentpage_comment_send_btn;
    private EditText commentpage_comment_et;
    private ContentService contentService = new ContentService();
    private List<Comment> comments;
    private CommentPageAdapter commentPageAdapter;
    private UserInfo now_user_info;
    private Photo now_photo;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            if (msg.what == ThreadState.COMMENT) {
                if (bundle.getString("comment_status").equals(ActivityStatus.STRING_COMMENT_SUCCESS)) {
                    ToastUtils.show_ordinary_toast(getApplicationContext(), "评论成功", Toast.LENGTH_SHORT);
                    commentpage_comment_et.setText("");
                } else {
                    ToastUtils.show_ordinary_toast(getApplicationContext(), "评论失败", Toast.LENGTH_SHORT);
                }
            } else if (msg.what == ThreadState.GET_COMMENT_BY_PHOTO_ID) {
                if (bundle.getString("get_comment_by_photo_id_status").equals(ActivityStatus.STRING_GET_COMMENT_BY_PHOTO_ID_FAILED)) {
                    ToastUtils.show_ordinary_toast(getApplicationContext(), "拉取评论失败", Toast.LENGTH_SHORT);
                } else {
                    comments = JSONObject.parseArray(bundle.getString("get_comment_by_photo_id_status"), Comment.class);
                    commentPageAdapter = new CommentPageAdapter(comments, getApplicationContext(), now_user_info);
                    comment_list.setAdapter(commentPageAdapter);
                }
            }
            return true;
        }
    });

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("lifecycle", "ondestroy");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在setContentView之前
        //状态栏中的文字颜色和图标颜色，需要android系统6.0以上，而且目前只有一种可以修改（一种是深色，一种是浅色即白色）
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //修改为深色，因为我们把状态栏的背景色修改为主题色白色，默认的文字及图标颜色为白色，导致看不到了。
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_comment);
        comment_list = findViewById(R.id.commentpage_comment_list);
        commentpage_comment_send_btn = findViewById(R.id.commentpage_comment_send_btn);
        commentpage_comment_et = findViewById(R.id.commentpage_comment_et);
        //拉取数据
        Intent intent = getIntent();
        now_user_info = new UserInfo();
        now_user_info.setUser_id(intent.getIntExtra("now_user_id", 0));
        now_photo = new Photo();
        now_photo.setPhoto_id(intent.getIntExtra("photo_id", 0));
        //拉取评论
        contentService.getCommentByPhotoID(now_photo.getPhoto_id(), handler);
        commentpage_comment_send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment comment = new Comment();
                comment.setComment_text(commentpage_comment_et.getText().toString());
                comment.setComment_time(new Date());
                comment.setComment_user_id(now_user_info.getUser_id());
                comment.setPhoto_id(now_photo.getPhoto_id());
                //这两个需要判空，因为没有comment的话，进入这个页面，发送的时候更新comments和commentPageAdapter会为null
                if (comments == null) {
                    comments = new ArrayList<>();
                }
                if (commentPageAdapter == null) {
                    commentPageAdapter = new CommentPageAdapter(comments, getApplicationContext(), now_user_info);
                    comment_list.setAdapter(commentPageAdapter);
                }
                //更新评论列表
                comments.add(comment);
                commentPageAdapter.notifyDataSetChanged();
                contentService.Comment(comment, handler);
            }
        });
    }
}