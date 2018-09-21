package com.lu.library.log;

import android.util.Log;


/**
 * Created by zhouzj on 2018/7/30.
 */

public class MouldLogUtil {

    /**
     * 只是在控制台打印日志，不会保存到文件
     */
    public static void d(String tag, String text){
        Log.d(tag, text);
    }

    /**
     * 既打印在控制台，又保存到文件
     * @param dirPath 日志保存的路径
     * @param tag 标签
     * @param text 日志
     */
    public static void p(String dirPath, String tag, String text){
        Log.i(tag, text);
        LogService.p(dirPath, tag, text);
    }

    /**
     * 既打印在控制台，又保存到文件
     * @param dirPath 日志保存的路径
     * @param tag 标签
     * @param text 日志
     */
    public static void e(String dirPath, String tag, String text){
        Log.e(tag, text);
        LogService.e(dirPath, tag, text);
    }
}
