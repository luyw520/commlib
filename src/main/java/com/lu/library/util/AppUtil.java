package com.lu.library.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.lu.library.LibContext;
import com.lu.library.log.LogUtil;

import java.util.List;
import java.util.Locale;

/**
 * @author: lyw
 * @description: ${TODO}{获取APP信息的辅助类}
 * @date: 2016/11/22 15:33
 */
public class AppUtil {
    public static int getAppMaxMemory(){
        Runtime rt=Runtime.getRuntime();
        long maxMemory=rt.maxMemory();
//        log.i());
//        tvMsg.setText("maxMemory:"+Long.toString(maxMemory/(1024*1024))+"MB");
        int mb= (int) (maxMemory/(1024*1024));
        return mb;
    }
    /**
     * 返回Manifest指定meta-data值
     * @param context 全局context
     * @param key meta-data key
     * @return
     *      成功-value
     *      失败-""
     */
    public static String getMetaData(Context context, String key) {
        ApplicationInfo app_info = null;
        try {
            app_info = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if(app_info == null || app_info.metaData == null) {
                return "";
            } else {
                Object obj = app_info.metaData.get(key);
                if(obj != null) {
                    return obj.toString();
                } else {
                    return "";
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    /**
     * 获取版本号
     * @param context 全局context
     * @return versoin code
     */
    public static int getVersionCode(Context context) {
        int version = 1;
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = packInfo.versionCode;
        } catch (Exception e) {
        }

        return version;
    }

    public static String getLanguage(){
        Locale locale = LibContext.getAppContext().getResources().getConfiguration().locale;
        return locale.getLanguage();
    }
    /**
     * 判断整个app项目是否在用户操作界面
     *b
     */
    public static boolean appIsRunning(Context context){
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = am.getRunningAppProcesses();
        String MY_PKG_NAME =context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(MY_PKG_NAME)) {
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    LogUtil.d( "处于后台" + appProcess.processName);
                    return false;
                } else {

                    LogUtil.d("处于前台" + appProcess.processName);
                    return true;
                }
            }
        }
        return true;

    }
    /**
     * 根据包名获取程序图标
     *
     * @param context
     * @param packname 应用包名
     * @return
     */
    public static Drawable getAppIconByPackageName(Context context, String packname) {
        try {
            //包管理操作管理类
            PackageManager pm = context.getPackageManager();
            //获取到应用信息
            ApplicationInfo info = pm.getApplicationInfo(packname, 0);
            return info.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String getPhoneInfo(Context context){
        TelephonyManager mTm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(",type:"+ Build.MODEL);
        stringBuffer.append(",品牌:"+ Build.MANUFACTURER);
        stringBuffer.append(",设备名称:"+ Build.MANUFACTURER+Build.DEVICE);
        stringBuffer.append(",SDK_INT:"+ Build.VERSION.SDK_INT);
        stringBuffer.append(",RELEASE:"+ Build.VERSION.RELEASE);
        stringBuffer.append(",app版本:"+getVersionName(context));
        return stringBuffer.toString();
    }
    /**
     * 根据包名判断是否安装了应用
     *
     * @param context
     * @param pageageName
     * @return
     */
    public static boolean isInstallAppByPackageName(Context context, String pageageName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pageageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取pid进程
     * @param cxt
     * @param pid
     * @return
     */
    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    /**
     * 是否安装了fackbook
     *
     * @param context
     * @return
     */
    public static boolean isInstallFackbook(Context context) {
        return isInstallAppByPackageName(context, "com.facebook.katana");
    }

    /**
     * 是否安装了qq
     *
     * @param context
     * @return
     */
    public static boolean isInstallQQ(Context context) {
        return isInstallAppByPackageName(context, "com.tencent.mqq");
    }

    /**
     * 是否安装了qq2012
     *
     * @param context
     * @return
     */
    public static boolean isInstallQQ2012(Context context) {
        return isInstallAppByPackageName(context, "com.tencent.mobileqq");
    }

    /**
     * 是否安装了91熊猫看书
     *
     * @param context
     * @return
     */
    public static boolean isInstall91ReadBook(Context context) {
        return isInstallAppByPackageName(context, "com.nd.android.pandareader");
    }

    /**
     * 是否安装了微信
     *
     * @param context
     * @return
     */
    public static boolean isInstallWeChat(Context context) {
        return isInstallAppByPackageName(context, "com.tencent.mm");
    }

    /**
     * 是否安装了chrome浏览器
     *
     * @param context
     * @return
     */
    public static boolean isInstallChrome(Context context) {
        return isInstallAppByPackageName(context, "com.Android.chrome");
    }

    /**
     * 是否安装了VeryFit
     *
     * @param context
     * @return
     */
    public static boolean isInstallVeryFit(Context context) {
        return isInstallAppByPackageName(context, "com.veryfit2hr.second");
    }

}
