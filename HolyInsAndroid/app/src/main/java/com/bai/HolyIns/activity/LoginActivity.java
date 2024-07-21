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
import com.bai.HolyIns.pojo.LoginInfo;
import com.bai.HolyIns.service.LoginService;
import com.bai.HolyIns.state.ActivityStatus;
import com.bai.HolyIns.state.ThreadState;
import com.bai.HolyIns.utils.DialogUtils;
import com.bai.HolyIns.utils.HttpUtils;
import com.bai.HolyIns.utils.SPUtils;
import com.bai.HolyIns.utils.StreamUtils;
import com.bai.HolyIns.utils.ToastUtils;
import com.githang.statusbar.StatusBarCompat;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class LoginActivity extends AppCompatActivity {
    //widgets
    private TextView login_page_register_tv, login_page_forgetPsw_tv;
    private EditText login_page_account_et, login_page_psw_et;
    private Button login_page_login_btn;
    //loading widgets
    private TextView loading_text;
    private View loading_view;
    private AlertDialog loading_dialog;
    //service
    private LoginService loginService = new LoginService();
    //handler
    private Handler handler = new Handler(msg -> {
        Bundle bundle = msg.getData();
        if (msg.what == ThreadState.LOGIN) {
            String login_status = bundle.getString("login_status");
            if (login_status.equals(ActivityStatus.STRING_LOGIN_FAILED)) {
                ToastUtils.show_ordinary_toast(this, "登录失败,请检查账号密码", Toast.LENGTH_SHORT);
                //登录失败就把本地的session删除掉
                SPUtils.remove(this, "session_id");
                loading_dialog.dismiss();
            } else {
                ToastUtils.show_ordinary_toast(this, "登录成功", Toast.LENGTH_SHORT);
                //将session_id放到sharedPreference当中,如果使用session登陆的话返回的是string_login_success状态值，这种情况就不应该再把它放到sharedPreference当中,
                //只有使用账号密码登录时候返回的是session_id
                if (!login_status.equals(ActivityStatus.STRING_LOGIN_SUCCESS)) {
                    SPUtils.put(this, "session_id", login_status);
                }
                String account_or_email = login_page_account_et.getText().toString();
                //第一次登录时候，肯定这两个空不为空，所以把email和account放进来，下一次登录时候如果检测到什么也没输入的话，就从这里找
                if (account_or_email.length() != 0) {
                    if (account_or_email.contains("@")) {
                        SPUtils.put(this, "e_mail", account_or_email);
                    } else {
                        SPUtils.put(this, "account", account_or_email);
                    }
                    loading_dialog.dismiss();
                    //跳转到主界面
                    Intent intent = new Intent(this, MainActivity.class);
                    //将邮件或者账号传入到主界面用来获得其他该用户的信息
                    if (account_or_email.contains("@")) {
                        intent.putExtra("e_mail", account_or_email);
                    } else {
                        intent.putExtra("account", account_or_email);
                    }
                    startActivity(intent);
                    finish();
                } else {
                    loading_dialog.dismiss();
                    //跳转到主界面
                    Intent intent = new Intent(this, MainActivity.class);
                    //读取本地存储的账号和邮箱
                    String account_storage = (String) SPUtils.get(this, "account", "");
                    String email_storage = (String) SPUtils.get(this, "e_mail", "");
                    //看在sharedPreference当中存了哪个就把哪个传到主界面，（如果从sharedPreference当中取到的value为""的话，则就是没有存）
                    if (!account_storage.equals("")) {
                        intent.putExtra("account", account_storage);
                    }
                    if (!email_storage.equals("")) {
                        intent.putExtra("e_mail", email_storage);
                    }
                    startActivity(intent);
                    finish();
                }
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
        setContentView(R.layout.activity_login);
        //初始化控件
        login_page_register_tv = findViewById(R.id.login_page_register_tv);
        login_page_forgetPsw_tv = findViewById(R.id.login_page_forgetPsw_tv);
        login_page_account_et = findViewById(R.id.login_page_account_et);
        login_page_psw_et = findViewById(R.id.login_page_psw_et);
        login_page_login_btn = findViewById(R.id.login_page_login_btn);
        //初始化加载界面
        loading_view = LayoutInflater.from(this).inflate(R.layout.loading_layout, null, false);
        loading_text = loading_view.findViewById(R.id.loading_text);
        loading_text.setText("正在登录");
        loading_dialog = DialogUtils.show_custom_dialog_not_auto_show(loading_view, this, false);
        //免登录
        LoginInfo loginInfo_session = new LoginInfo();
        String session_id_storage = (String) SPUtils.get(this, "session_id", "");

        //如果从sharedPreference当中取得的id不为空的话则启用这个session_id去服务器执行免登录
        if (!session_id_storage.equals("")) {
            loginInfo_session.setSession_id(session_id_storage);
            loginService.Login(handler, loginInfo_session);
            loading_dialog.show();
        }

        //登录按钮设置监听
        login_page_login_btn.setOnClickListener(v -> {
            LoginInfo loginInfo = new LoginInfo();
            //id为空的话则使用账号密码重新登录
            String account_or_email = login_page_account_et.getText().toString();
            //里面包含@的话就是email，否则就是账号
            //这里不能loginInfo.setAccount(null),否则到了服务端之后解析出来account就不为null,就不应该设置它就行
            if (account_or_email.contains("@")) {
                loginInfo.setE_mail(account_or_email);
            } else {
                loginInfo.setAccount(account_or_email);
            }
            loginInfo.setPassword(login_page_psw_et.getText().toString());
            loginService.Login(handler, loginInfo);
            loading_dialog.show();
        });

        //进入找回密码
        login_page_forgetPsw_tv.setOnClickListener(v -> {
            Intent intent=new Intent(this,FindPswActivity.class);
            startActivity(intent);
        });

        //进入注册
        login_page_register_tv.setOnClickListener(v -> {
            Intent intent=new Intent(this,RegisterActivity.class);
            startActivity(intent);
        });
    }
}