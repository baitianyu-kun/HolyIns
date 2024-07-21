package com.bai.HolyIns.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bai.HolyIns.R;
import com.bai.HolyIns.pojo.Photo;
import com.bai.HolyIns.pojo.UploadImageInfo;
import com.bai.HolyIns.pojo.UserInfo;
import com.bai.HolyIns.service.ImageService;
import com.bai.HolyIns.service.ReleaseService;
import com.bai.HolyIns.state.ActivityStatus;
import com.bai.HolyIns.state.NetWorkProperties;
import com.bai.HolyIns.state.ThreadState;
import com.bai.HolyIns.utils.DialogUtils;
import com.bai.HolyIns.utils.FileUtilsNew;
import com.bai.HolyIns.utils.ToastUtils;
import com.bai.HolyIns.utils.UploadUtil;

import java.io.File;
import java.util.Date;

import mehdi.sakout.dynamicbox.DynamicBox;

public class ReleasePostActivity extends AppCompatActivity {
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
    //widgets
    private Button release_page_release_btn;
    private EditText release_page_text;
    private ImageButton release_page_choose_album;
    // activity_request_code状态值，是从图库中选择还是从相机中拍摄过来
    private static final int CAMERA_TAKE = 1;
    private static final int CAMERA_SELECT = 2;
    //loading_layout
    View loading_view;
    private TextView loading_upload_text;
    private AlertDialog loading_dialog;
    //service
    private ImageService imageService = new ImageService();
    private ReleaseService releaseService = new ReleaseService();
    //other
    private UploadImageInfo uploadImageInfo;
    private UserInfo now_user_info;
    //handler
    private Handler handler = new Handler(msg -> {
        Bundle bundle = msg.getData();
        if (msg.what == ThreadState.UPLOAD_PHOTO) {
            uploadImageInfo = (UploadImageInfo) bundle.getSerializable("photo_upload_status");
            if (!uploadImageInfo.getImage_path().equals("null")) {
                loading_dialog.dismiss();
                ToastUtils.show_ordinary_toast(this, "图片上传成功", Toast.LENGTH_SHORT);
            }
        } else if (msg.what == ThreadState.RELEASE_POST) {
            if (bundle.getString("releasePost_status").equals(ActivityStatus.STRING_RELEASE_POST_SUCCESS)) {
                loading_dialog.dismiss();
                ToastUtils.show_ordinary_toast(this, "动态发布成功", Toast.LENGTH_SHORT);
                //返回主界面
                onBackPressed();
            } else {
                loading_dialog.dismiss();
                ToastUtils.show_ordinary_toast(this, "动态发布失败", Toast.LENGTH_SHORT);
            }
        }
        return true;
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //修改为深色，因为我们把状态栏的背景色修改为主题色白色，默认的文字及图标颜色为白色，导致看不到了。
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_release_post);
        //todo 初始化now_user_info
        Intent getUserInfo = getIntent();
        now_user_info = (UserInfo) getUserInfo.getSerializableExtra("now_user_info");
        //初始化控件
        release_page_choose_album = findViewById(R.id.release_page_choose_album);
        release_page_release_btn = findViewById(R.id.release_page_release_btn);
        release_page_text = findViewById(R.id.release_page_text);
        loading_view = LayoutInflater.from(this).inflate(R.layout.loading_upload_photo, null, false);
        loading_upload_text = loading_view.findViewById(R.id.loading_upload_text);
        loading_dialog = DialogUtils.show_custom_dialog_not_auto_show(loading_view, this, false);
        //验证权限
        verifyStoragePermissions(this);
        //选择照片添加监听
        release_page_choose_album.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, CAMERA_SELECT);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_SELECT) {
            Uri imgUri = data.getData();
            String path = FileUtilsNew.getPath(this, imgUri);
            File file = new File(path);
            imageService.PhotoUpload(file, handler);
            //选择成功之后将这个按钮设置为图片的样子
            release_page_choose_album.setImageURI(imgUri);
            //显示上传加载界面
            loading_upload_text.setText("正在上传");
            loading_dialog.show();

            release_page_release_btn.setOnClickListener(v -> {
                Photo photo = new Photo();
                //判断返回来的那个uploadinfo是否为空
                if (uploadImageInfo != null) {
                    photo.setPhoto_path(uploadImageInfo.getImage_path());
                    photo.setPhoto_size(String.valueOf(uploadImageInfo.getImage_size()));
                    photo.setDate_created(new Date());
                    photo.setDate_updated(new Date());
                    photo.setPhoto_description(release_page_text.getText().toString());
                    photo.setUser_id(now_user_info.getUser_id());
                    //开始发布
                    releaseService.releasePost(handler, photo);
                    loading_upload_text.setText("正在发布");
                    loading_dialog.show();
                } else {
                    ToastUtils.show_ordinary_toast(this, "动态发布出错", Toast.LENGTH_SHORT);
                }
            });
        }
    }
}