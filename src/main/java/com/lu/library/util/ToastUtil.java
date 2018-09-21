package com.lu.library.util;

import android.content.Context;
import android.widget.Toast;

import com.lu.library.LibContext;


/**
 * Created by zhouzj on 2018/7/26.
 */

public class ToastUtil {

    private static Toast mToast = null;
    private static long mLastTime = 0L;

    /**
     * 防止Toast重复弹出
     *
     * @param context  上下文
     * @param resid    字符资源
     * @param duration 时长
     */
    public static void showToast(final Context context,final int resid,final int duration) {
       showToast(context,context.getResources().getString(resid),duration);

    }
    /**
     * 防止Toast重复弹出
     *
     * @param context  上下文
     * @param message    字符资源
     * @param duration 时长
     */
    public static void showToast(final Context context,final String message,final int duration) {

        ThreadUtil.runOnMainThread(new Runnable(){
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(context, message, duration);
                }
                    mToast.setText(message);
                    mToast.setDuration(duration);

                if (System.currentTimeMillis() - mLastTime > 1000) {
                    mToast.show();
                    mLastTime = System.currentTimeMillis();
                }
            }
        });

    }
    public static void showToast(Context context, int res) {
        if (context == null) {
            return;
        }
        String msg = context.getString(res);
        showToast(context, msg,Toast.LENGTH_SHORT);
    }
    public static void showToast(Context context, String msg) {
        if (context == null) {
            return;
        }
        showToast(context, msg,Toast.LENGTH_SHORT);
    }

    /**
     * 显示一个toast
     *
     * @param msg
     */
    public static void showToast(final String msg) {
        showToast(LibContext.getAppContext(),msg);
    }

    public static void showToast(int res) {
        showToast(LibContext.getAppContext(),res);
    }
}
