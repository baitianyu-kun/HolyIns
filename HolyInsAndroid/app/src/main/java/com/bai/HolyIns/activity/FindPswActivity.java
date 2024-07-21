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
import android.widget.TextView;
import android.widget.Toast;

import com.bai.HolyIns.R;
import com.bai.HolyIns.service.FindPswService;
import com.bai.HolyIns.state.ActivityStatus;
import com.bai.HolyIns.state.ThreadState;
import com.bai.HolyIns.utils.DialogUtils;
import com.bai.HolyIns.utils.HandlerUtils;
import com.bai.HolyIns.utils.ToastUtils;

public class FindPswActivity extends AppCompatActivity {
    //输入邮箱widgets
    private TextView find_psw_page_text;
    private EditText find_psw_page_input_validation_code_et;
    private Button find_psw_page_input_validation_code_sure;
    private EditText find_psw_page_input_email_et;
    private Button find_psw_page_input_email_sure;
    //输入新密码的widgets
    private EditText find_psw_page_input_new_password_et;
    private Button find_psw_input_new_password_sure;
    //dialog验证码发送加载界面
    private TextView loading_text;
    private View loading_view;
    private AlertDialog loading_dialog;
    //service
    private FindPswService findPswService = new FindPswService();
    //handler
    private Handler handler_change_psw = new Handler(msg -> {
        Bundle bundle = msg.getData();
        if (msg.what == ThreadState.FIND_PSW_CHANGE_PSW) {
            if (bundle.getString("find_psw_change_psw_status").equals(ActivityStatus.STRING_PASSWORD_CHANGE_SUCCESS)) {
                ToastUtils.show_ordinary_toast(this, "密码更改成功,即将回到登录页面", Toast.LENGTH_SHORT);
                loading_dialog.dismiss();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                ToastUtils.show_ordinary_toast(this, "密码更改失败", Toast.LENGTH_SHORT);
                loading_dialog.dismiss();
            }
        }
        return true;
    });
    private Handler handler = new Handler(msg -> {
        Bundle bundle = msg.getData();
        //倒计时handler
        if (msg.what == ThreadState.COUNT_DOWN) {
            if (bundle.getString("time").equals("0")) {
                find_psw_page_input_email_sure.setText("重新发送");
                find_psw_page_input_email_sure.setEnabled(true);
            } else {
                find_psw_page_input_email_sure.setText(bundle.getString("time"));
            }
        } else if (msg.what == ThreadState.FIND_PSW_SEND_VALIDATION_CODE) {
            if (bundle.getString("find_psw_send_validation_code_status").equals(ActivityStatus.STRING_MAIL_SEND_SUCCESS)) {
                ToastUtils.show_ordinary_toast(this, "验证码发送成功", Toast.LENGTH_SHORT);
                find_psw_page_input_email_sure.setEnabled(false);
                loading_dialog.dismiss();
            } else {
                ToastUtils.show_ordinary_toast(this, "验证码发送失败", Toast.LENGTH_SHORT);
                loading_dialog.dismiss();
            }
        } else if (msg.what == ThreadState.FIND_PSW_VALIDATE_CODE) {
            String result = bundle.getString("find_psw_validate_code_status");
            if (result.equals(ActivityStatus.STRING_VALIDATION_CODE_RIGHT)) {
                ToastUtils.show_ordinary_toast(this, "验证成功", Toast.LENGTH_SHORT);
                //把邮箱部分隐藏
                find_psw_page_input_validation_code_et.setVisibility(View.INVISIBLE);
                find_psw_page_input_validation_code_sure.setVisibility(View.INVISIBLE);
                find_psw_page_input_email_et.setVisibility(View.INVISIBLE);
                find_psw_page_input_email_sure.setVisibility(View.INVISIBLE);
                find_psw_page_text.setText("输入新的密码叭");
                //新密码那里可以显示
                find_psw_page_input_new_password_et.setVisibility(View.VISIBLE);
                find_psw_input_new_password_sure.setVisibility(View.VISIBLE);
                //改密码的按钮监听
                find_psw_input_new_password_sure.setOnClickListener(v -> {
                    String new_password = find_psw_page_input_new_password_et.getText().toString();
                    if (new_password.length() != 0) {
                        findPswService.changePsw(handler_change_psw, new_password, find_psw_page_input_email_et.getText().toString());
                        loading_dialog.show();
                    }
                });
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
        setContentView(R.layout.activity_find_psw);
        //初始化邮箱widgets
        find_psw_page_text = findViewById(R.id.find_psw_page_text);
        find_psw_page_input_validation_code_et = findViewById(R.id.find_psw_page_input_validation_code_et);
        find_psw_page_input_validation_code_sure = findViewById(R.id.find_psw_page_input_validation_code_sure);
        find_psw_page_input_email_et = findViewById(R.id.find_psw_page_input_email_et);
        find_psw_page_input_email_sure = findViewById(R.id.find_psw_page_input_email_sure);
        //输入新密码的widgets
        find_psw_page_input_new_password_et = findViewById(R.id.find_psw_page_input_new_password_et);
        find_psw_input_new_password_sure = findViewById(R.id.find_psw_input_new_password_sure);
        //验证码发送加载界面初始化
        loading_view = LayoutInflater.from(this).inflate(R.layout.loading_layout, null, false);
        loading_text = loading_view.findViewById(R.id.loading_text);
        loading_dialog = DialogUtils.show_custom_dialog_not_auto_show(loading_view, this, false);
        //一开始把新密码部分全隐藏了
        find_psw_page_input_new_password_et.setVisibility(View.INVISIBLE);
        find_psw_input_new_password_sure.setVisibility(View.INVISIBLE);
        //发送验证码监听
        find_psw_page_input_email_sure.setOnClickListener(v -> {
            String email = find_psw_page_input_email_et.getText().toString();
            if (email.length() != 0) {
                findPswService.sendValidationCode(handler, email);
                loading_text.setText("正在发送验证码");
                find_psw_page_text.setText("请输入验证码");
                loading_dialog.show();
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
            }
        });
        //验证 验证码监听
        find_psw_page_input_validation_code_sure.setOnClickListener(v -> {
            String validation_code = find_psw_page_input_validation_code_et.getText().toString();
            if (validation_code.length() != 0) {
                findPswService.validateCode(handler, validation_code);
            }
        });
    }
}