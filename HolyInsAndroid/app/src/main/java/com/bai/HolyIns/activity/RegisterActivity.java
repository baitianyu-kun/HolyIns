package com.bai.HolyIns.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bai.HolyIns.R;
import com.bai.HolyIns.pojo.LoginInfo;
import com.bai.HolyIns.pojo.UserInfo;
import com.bai.HolyIns.service.RegisterService;
import com.bai.HolyIns.state.ActivityStatus;
import com.bai.HolyIns.state.StorageState;
import com.bai.HolyIns.state.ThreadState;
import com.bai.HolyIns.utils.DialogUtils;
import com.bai.HolyIns.utils.HandlerUtils;
import com.bai.HolyIns.utils.IntegerUtils;
import com.bai.HolyIns.utils.ToastUtils;

import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    //验证码部分widgets
    private EditText register_page_input_email_et, register_page_input_validation_code_et;
    private Button register_page_input_email_sure, register_page_input_validation_code_sure;
    private TextView register_page_text;
    //注册部分widgets
    private EditText register_page_input_name_et;
    private EditText register_page_input_psw;
    private RadioButton register_page_boy_radio_btn;
    private RadioButton register_page_girl_radio_btn;
    private Button register_page_register_btn;
    //service
    private RegisterService registerService = new RegisterService();
    //dialog验证码发送加载界面
    private TextView loading_text;
    private View loading_view;
    private AlertDialog loading_dialog;
    //handler
    private Handler handler_register = new Handler(msg -> {
        Bundle bundle = msg.getData();
        if (msg.what == ThreadState.REGISTER_REGISTER) {
            if (bundle.getString("register_register_status").equals(ActivityStatus.STRING_REGISTER_SUCCESS)) {
                ToastUtils.show_ordinary_toast(this, "注册成功,即将跳转到登录界面", Toast.LENGTH_SHORT);
                loading_dialog.dismiss();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                ToastUtils.show_ordinary_toast(this, "注册失败", Toast.LENGTH_SHORT);
                loading_dialog.dismiss();
            }
        }
        return true;
    });
    private Handler handler = new Handler(msg -> {
        Bundle bundle = msg.getData();
        //倒计时handler
        if (msg.what == ThreadState.REGISTER_SEND_VALIDATION_CODE) {
            if (bundle.getString("register_send_validation_code_status").equals(ActivityStatus.STRING_MAIL_SEND_SUCCESS)) {
                ToastUtils.show_ordinary_toast(this, "验证码发送成功", Toast.LENGTH_SHORT);
                register_page_input_email_sure.setEnabled(false);
                loading_dialog.dismiss();
            } else {
                ToastUtils.show_ordinary_toast(this, "验证码发送失败", Toast.LENGTH_SHORT);
                loading_dialog.dismiss();
            }
        } else if (msg.what == ThreadState.COUNT_DOWN) {
            if (bundle.getString("time").equals("0")) {
                register_page_input_email_sure.setText("重新发送");
                register_page_input_email_sure.setEnabled(true);
            } else {
                register_page_input_email_sure.setText(bundle.getString("time"));
            }
        } else if (msg.what == ThreadState.REGISTER_VALIDATE_CODE) {
            String result = bundle.getString("register_validate_code_status");
            if (result.equals(ActivityStatus.STRING_VALIDATION_CODE_RIGHT)) {
                ToastUtils.show_ordinary_toast(this, "验证成功", Toast.LENGTH_SHORT);
                //把那几个都设置为消失，然后用户名和密码搞出来
                register_page_input_email_et.setVisibility(View.INVISIBLE);
                register_page_input_validation_code_et.setVisibility(View.INVISIBLE);
                register_page_input_email_sure.setVisibility(View.INVISIBLE);
                register_page_input_validation_code_sure.setVisibility(View.INVISIBLE);
                register_page_text.setText("填一下信息吧");
                //注册那块设置为可显示
                register_page_input_name_et.setVisibility(View.VISIBLE);
                register_page_input_psw.setVisibility(View.VISIBLE);
                register_page_register_btn.setVisibility(View.VISIBLE);
                register_page_boy_radio_btn.setVisibility(View.VISIBLE);
                register_page_girl_radio_btn.setVisibility(View.VISIBLE);
                //注册按钮添加监听
                register_page_register_btn.setOnClickListener(v -> {
                    UserInfo userInfo = new UserInfo();
                    LoginInfo loginInfo = new LoginInfo();
                    String account = IntegerUtils.GenerateSixAccount();
                    String email = register_page_input_email_et.getText().toString();
                    if (email.length() != 0) {
                        userInfo.setAccount(account);
                        userInfo.setE_mail(email);
                        userInfo.setRegisterTime(new Date());
                        if (register_page_boy_radio_btn.isChecked()) {
                            userInfo.setSex("男孩子");
                        }
                        if (register_page_girl_radio_btn.isChecked()) {
                            userInfo.setSex("女孩子");
                        }
                        userInfo.setName(register_page_input_name_et.getText().toString());
                        //todo
                        userInfo.setHeadPictureSize("50");
                        userInfo.setHeadPicturePath(StorageState.DEFAULT_HEAD_PIC);
                        loginInfo.setPassword(register_page_input_psw.getText().toString());
                        loginInfo.setAccount(account);
                        loginInfo.setE_mail(email);
                        loginInfo.setName(register_page_input_name_et.getText().toString());
                        registerService.register(handler_register, userInfo, loginInfo);
                        //注册加载界面
                        loading_text.setText("正在注册...请稍后");
                        loading_dialog.show();
                    }
                });

            } else if (result.equals(ActivityStatus.STRING_VALIDATION_CODE_DATED)) {
                ToastUtils.show_ordinary_toast(this, "验证码已经过期", Toast.LENGTH_SHORT);
            } else if (result.equals(ActivityStatus.STRING_VALIDATION_CODE_ERROR)) {
                ToastUtils.show_ordinary_toast(this, "验证码错误", Toast.LENGTH_SHORT);
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
        setContentView(R.layout.activity_register);
        //初始化验证码部分控件
        register_page_input_email_et = findViewById(R.id.register_page_input_email_et);
        register_page_input_validation_code_et = findViewById(R.id.register_page_input_validation_code_et);
        register_page_input_email_sure = findViewById(R.id.register_page_input_email_sure);
        register_page_input_validation_code_sure = findViewById(R.id.register_page_input_validation_code_sure);
        register_page_text = findViewById(R.id.register_page_text);
        //初始化注册控件
        register_page_input_name_et = findViewById(R.id.register_page_input_name_et);
        register_page_input_psw = findViewById(R.id.register_page_input_psw);
        register_page_register_btn = findViewById(R.id.register_page_register_btn);
        register_page_boy_radio_btn = findViewById(R.id.register_page_boy_radio_btn);
        register_page_girl_radio_btn = findViewById(R.id.register_page_girl_radio_btn);
        //验证码发送加载界面初始化
        loading_view = LayoutInflater.from(this).inflate(R.layout.loading_layout, null, false);
        loading_text = loading_view.findViewById(R.id.loading_text);
        loading_dialog = DialogUtils.show_custom_dialog_not_auto_show(loading_view, this, false);
        //一开始先把注册信息的都隐藏了
        register_page_input_name_et.setVisibility(View.INVISIBLE);
        register_page_input_psw.setVisibility(View.INVISIBLE);
        register_page_register_btn.setVisibility(View.INVISIBLE);
        register_page_boy_radio_btn.setVisibility(View.INVISIBLE);
        register_page_girl_radio_btn.setVisibility(View.INVISIBLE);
        //发送验证码监听
        register_page_input_email_sure.setOnClickListener(v -> {
            String email = register_page_input_email_et.getText().toString();
            if (email.length() != 0) {
                registerService.sendValidationCode(handler, email);
                loading_text.setText("正在发送验证码");
                register_page_text.setText("请输入验证码");
                loading_dialog.show();
            }
            //给那个发送按钮开始验证码倒计时展示
            new Thread() {
                int time = 70;

                @Override
                public void run() {
                    while (time > 0) {
                        time--;
                        HandlerUtils.sendStringMessage("time", String.valueOf(time), handler, ThreadState.COUNT_DOWN);
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        });
        //验证 验证码监听
        register_page_input_validation_code_sure.setOnClickListener(v -> {
            String validation_code = register_page_input_validation_code_et.getText().toString();
            if (validation_code.length() != 0) {
                registerService.validateCode(handler, validation_code);
            }
        });
    }
}