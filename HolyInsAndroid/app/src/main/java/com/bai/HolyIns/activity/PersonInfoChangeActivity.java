package com.bai.HolyIns.activity;

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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bai.HolyIns.R;
import com.bai.HolyIns.fragment.PersonInfoFragment;
import com.bai.HolyIns.pojo.Photo;
import com.bai.HolyIns.pojo.UploadImageInfo;
import com.bai.HolyIns.pojo.UserInfo;
import com.bai.HolyIns.service.ImageService;
import com.bai.HolyIns.service.PersonInfoService;
import com.bai.HolyIns.service.RegisterService;
import com.bai.HolyIns.state.ActivityStatus;
import com.bai.HolyIns.state.ThreadState;
import com.bai.HolyIns.utils.DialogUtils;
import com.bai.HolyIns.utils.FileUtilsNew;
import com.bai.HolyIns.utils.SPUtils;
import com.bai.HolyIns.utils.ToastUtils;

import java.io.File;
import java.util.Date;

public class PersonInfoChangeActivity extends AppCompatActivity {
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
    private ImageView person_info_change_page_head_pic;
    private EditText person_info_change_page_name;
    private RadioButton person_info_change_page_boy_radio_btn, person_info_change_page_girl_radio_btn;
    private Button person_info_change_page_log_out_btn, person_info_change_page_sure_btn;
    //dialog加载界面
    private TextView loading_text;
    private View loading_view;
    private AlertDialog loading_dialog;
    // activity_request_code状态值，是从图库中选择还是从相机中拍摄过来
    private static final int CAMERA_TAKE = 1;
    private static final int CAMERA_SELECT = 2;
    //other
    private UploadImageInfo uploadImageInfo;
    private UserInfo now_user_info;
    //service
    private PersonInfoService personInfoService = new PersonInfoService();
    private ImageService imageService = new ImageService();
    //handler
    private Handler handler = new Handler(msg -> {
        Bundle bundle = msg.getData();
        if (msg.what == ThreadState.UPLOAD_HEAD_PIC) {
            uploadImageInfo = (UploadImageInfo) bundle.getSerializable("head_pic_upload_status");
            if (!uploadImageInfo.getImage_path().equals("null")) {
                loading_dialog.dismiss();
                ToastUtils.show_ordinary_toast(this, "头像上传成功", Toast.LENGTH_SHORT);
                PersonInfoFragment.updated_head_pic_path=uploadImageInfo.getImage_path();
            } else {
                loading_dialog.dismiss();
                ToastUtils.show_ordinary_toast(this, "头像上传失败", Toast.LENGTH_SHORT);
            }
        } else if (msg.what == ThreadState.CHANGE_PERSON_INFO) {
            if (bundle.getString("register_change_person_info_status").equals(ActivityStatus.STRING_PERSON_INFO_CHANGE_SUCCESS)) {
                loading_dialog.dismiss();
                ToastUtils.show_ordinary_toast(this, "个人信息修改成功", Toast.LENGTH_SHORT);

                /*Intent intent = new Intent(this, MainActivity.class);
                //重开mainActivity，达到刷新的目的
                intent.putExtra("e_mail", now_user_info.getE_mail());
                intent.putExtra("account", now_user_info.getAccount());
                startActivity(intent);
                finish();*/
            } else {
                loading_dialog.dismiss();
                ToastUtils.show_ordinary_toast(this, "个人信息修改失败", Toast.LENGTH_SHORT);
            }
        } else if (msg.what == ThreadState.HEAD_PIC) {
            //用户的头像
            Bitmap bitmap = bundle.getParcelable("bitmap");
            person_info_change_page_head_pic.setImageBitmap(bitmap);
        }
        return true;
    });

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //todo
            /*Intent intent = new Intent(this, MainActivity.class);
            //重开mainActivity，达到刷新的目的
            intent.putExtra("e_mail", now_user_info.getE_mail());
            intent.putExtra("account", now_user_info.getAccount());
            startActivity(intent);
            finish();*/
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        verifyStoragePermissions(this);
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //修改为深色，因为我们把状态栏的背景色修改为主题色白色，默认的文字及图标颜色为白色，导致看不到了。
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_person_info_change);
        //从前面获取信息,不能直接把头像也传过来，否则会报错，intent传输的东西不能大于40KB
        Intent getUserInfo = getIntent();
        now_user_info = (UserInfo) getUserInfo.getSerializableExtra("now_user_info");
        //获取头像
        imageService.HeadPicGet(handler, now_user_info.getHeadPicturePath());
        //控件初始化
        person_info_change_page_head_pic = findViewById(R.id.person_info_change_page_head_pic);
        person_info_change_page_name = findViewById(R.id.person_info_change_page_name);
        person_info_change_page_boy_radio_btn = findViewById(R.id.person_info_change_page_boy_radio_btn);
        person_info_change_page_girl_radio_btn = findViewById(R.id.person_info_change_page_girl_radio_btn);
        person_info_change_page_log_out_btn = findViewById(R.id.person_info_change_page_log_out_btn);
        person_info_change_page_sure_btn = findViewById(R.id.person_info_change_page_sure_btn);
        //加载界面初始化
        loading_view = LayoutInflater.from(this).inflate(R.layout.loading_layout, null, false);
        loading_text = loading_view.findViewById(R.id.loading_text);
        loading_dialog = DialogUtils.show_custom_dialog_not_auto_show(loading_view, this, false);
        //设置当前已经在的信息
        person_info_change_page_name.setText(now_user_info.getName());
        if (now_user_info.getSex().equals("男孩子")) {
            person_info_change_page_boy_radio_btn.setChecked(true);
        }
        if (now_user_info.getSex().equals("女孩子")) {
            person_info_change_page_girl_radio_btn.setChecked(true);
        }
        //给头像添加监听，点击之后可以跳转到相册中更换头像
        person_info_change_page_head_pic.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, CAMERA_SELECT);
        });
        person_info_change_page_sure_btn.setOnClickListener(v -> {
            UserInfo new_user_info = new UserInfo();
            if (person_info_change_page_boy_radio_btn.isChecked()) {
                new_user_info.setSex("男孩子");
            }
            if (person_info_change_page_girl_radio_btn.isChecked()) {
                new_user_info.setSex("女孩子");
            }
            new_user_info.setName(person_info_change_page_name.getText().toString());
            new_user_info.setUser_id(now_user_info.getUser_id());
            //这条是为了改登陆表用的
            new_user_info.setAccount(now_user_info.getAccount());
            //如果我上传了头像的话则使用新返回了的头像的path和size，否则还使用以前的那个
            if (uploadImageInfo != null) {
                new_user_info.setHeadPicturePath(uploadImageInfo.getImage_path());
                new_user_info.setHeadPictureSize(String.valueOf(uploadImageInfo.getImage_size()));
            } else {
                new_user_info.setHeadPicturePath(now_user_info.getHeadPicturePath());
                new_user_info.setHeadPictureSize(now_user_info.getHeadPictureSize());
            }
            personInfoService.changePersonInfo(handler, new_user_info);
            loading_text.setText("正在修改信息");
            loading_dialog.show();
        });
        //退出登录
        person_info_change_page_log_out_btn.setOnClickListener(v -> {
            //退出登录就把本地的session删除掉
            SPUtils.remove(this, "session_id");
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果是从相册中选择照片的话
        if (requestCode == CAMERA_SELECT) {
            Uri imgUri = data.getData();
            String path = FileUtilsNew.getPath(this, imgUri);
            File file = new File(path);
            imageService.HeadPicUpload(file, handler);
            //开始上传之后，把当前界面的头像设置为这张图片
            person_info_change_page_head_pic.setImageURI(imgUri);
            loading_text.setText("正在上传图片");
            loading_dialog.show();
        }
    }
}