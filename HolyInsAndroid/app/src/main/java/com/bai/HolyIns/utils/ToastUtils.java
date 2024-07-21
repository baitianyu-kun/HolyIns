package com.bai.HolyIns.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    public static void show_ordinary_toast(Context context,String content,int Type)
    {
        Toast toast= Toast.makeText(context,content,Type);
        toast.show();
    }
    public static void show_ordinary_toast(Context context,int content,int Type)
    {
        Toast toast= Toast.makeText(context,content,Type);
        toast.show();
    }
}
