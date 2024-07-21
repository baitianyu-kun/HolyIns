package com.bai.HolyIns.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

public class DialogUtils {
    public static void show_ordinary_dialog(String title, String Message, Context activity_context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity_context);
        dialog.setTitle(title);
        dialog.setMessage(Message);
        dialog.setNegativeButton("知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//关闭dialog
            }
        });
        dialog.show();
    }

    //获取自定义的dialog，并返回，然后在主界面中就能找到控件了
    public static AlertDialog show_custom_dialog(View view, Context context, Boolean setCancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        dialog.setView(view);
        dialog.setCancelable(setCancelable);//用来设置点击空白处是否退出该dialog
        dialog.show();
        return dialog;
    }


    public static AlertDialog show_custom_dialog_not_auto_show(View view, Context context, Boolean setCancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        dialog.setView(view);
        dialog.setCancelable(setCancelable);//用来设置点击空白处是否退出该dialog
        return dialog;
    }
}

