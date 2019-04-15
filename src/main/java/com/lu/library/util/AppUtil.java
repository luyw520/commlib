package com.lu.library.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.RequiresApi;

import com.lu.library.LibContext;
import com.lu.library.log.DebugLog;
import com.lu.library.log.LogUtil;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

import static android.content.Context.POWER_SERVICE;
import static com.lu.library.util.IntentUtils.getInstallAppIntent;
import static com.lu.library.util.file.FileUtil.getFileByPath;
import static com.lu.library.util.file.FileUtil.isFileExists;
import static com.lu.library.util.string.ConvertUtil.bytes2HexString;
import static com.lu.library.util.string.StringUtil.isSpace;

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
     * 忽略优化电池，系统大于6.0以上
     *
     * @param context
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void ignoreBatteryOptimization(Context context) {

        PowerManager powerManager = (PowerManager) context.getSystemService(POWER_SERVICE);

        boolean hasIgnored = powerManager.isIgnoringBatteryOptimizations(context.getPackageName());
        //  判断当前APP是否有加入电池优化的白名单，如果没有，弹出加入电池优化的白名单的设置对话框。
        if (!hasIgnored) {
            Intent intent = null;
            if ("Huawei".equals(android.os.Build.BRAND)) {
                intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                // 设置ComponentName参数1:packagename参数2:Activity路径
                ComponentName cn = new ComponentName("com.android.settings", "com.android.com.settings.Settings@HighPowerApplicationsActivity");
                intent.setComponent(cn);
                context.startActivity(intent);
            } else {
                intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
            DebugLog.i(" getModel " + Build.MODEL + " getBrand " + android.os.Build.BRAND);
        }
    }

    /**
     * 判断服务是否开启
     */
    public static boolean isServiceRunning(Context context, String proServiceName) {
//        final String proServiceName = "com.veryfit2hr.second.ui.services.IntelligentNotificationService";
        ActivityManager manager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        List<ActivityManager.RunningServiceInfo> runningService = manager
                .getRunningServices(300);
        if (runningService == null) {
            return false;
        }
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName()
                    .equals(proServiceName)) {
                return true;
            }
        }
        return false;
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
     * Install the app.
     * <p>Target APIs greater than 25 must hold
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param filePath The path of file.
     */
    public static void installApp(final String filePath) {
        installApp(getFileByPath(filePath));
    }
    /**
     * Install the app.
     * <p>Target APIs greater than 25 must hold
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param file The file.
     */
    public static void installApp(final File file) {
        if (!isFileExists(file)) return;
        Utils.getApp().startActivity(getInstallAppIntent(file, true));
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
    /**
     * Return the application's signature.
     *
     * @return the application's signature
     */
    public static Signature[] getAppSignature() {
        return getAppSignature(Utils.getApp().getPackageName());
    }
    /**
     * Return the application's signature for SHA1 value.
     *
     * @return the application's signature for SHA1 value
     */
    public static String getAppSignatureSHA1() {
        return getAppSignatureSHA1(Utils.getApp().getPackageName());
    }

    /**
     * Return the application's signature for SHA1 value.
     *
     * @param packageName The name of the package.
     * @return the application's signature for SHA1 value
     */
    public static String getAppSignatureSHA1(final String packageName) {
        return getAppSignatureHash(packageName, "SHA1");
    }
    /**
     * Return the application's signature for SHA256 value.
     *
     * @return the application's signature for SHA256 value
     */
    public static String getAppSignatureSHA256() {
        return getAppSignatureSHA256(Utils.getApp().getPackageName());
    }

    /**
     * Return the application's signature for SHA256 value.
     *
     * @param packageName The name of the package.
     * @return the application's signature for SHA256 value
     */
    public static String getAppSignatureSHA256(final String packageName) {
        return getAppSignatureHash(packageName, "SHA256");
    }
    /**
     * Return the application's signature for MD5 value.
     *
     * @param packageName The name of the package.
     * @return the application's signature for MD5 value
     */
    public static String getAppSignatureMD5(final String packageName) {
        return getAppSignatureHash(packageName, "MD5");
    }

    private static String getAppSignatureHash(final String packageName, final String algorithm) {
        if (isSpace(packageName)) return "";
        Signature[] signature = getAppSignature(packageName);
        if (signature == null || signature.length <= 0) return "";
        return bytes2HexString(hashTemplate(signature[0].toByteArray(), algorithm))
                .replaceAll("(?<=[0-9A-F]{2})[0-9A-F]{2}", ":$0");
    }

    private static byte[] hashTemplate(final byte[] data, final String algorithm) {
        if (data == null || data.length <= 0) return null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Return the application's signature for MD5 value.
     *
     * @return the application's signature for MD5 value
     */
    public static String getAppSignatureMD5() {
        return getAppSignatureMD5(Utils.getApp().getPackageName());
    }

    /**
     * Return the application's signature.
     *
     * @param packageName The name of the package.
     * @return the application's signature
     */
    public static Signature[] getAppSignature(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            @SuppressLint("PackageManagerGetSignatures")
            PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return pi == null ? null : pi.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String getLanguage(){
        Locale locale = LibContext.getAppContext().getResources().getConfiguration().locale;
        return locale.getLanguage();
    }
    /**
     * Exit the application.
     */
    public static void exitApp() {
        List<Activity> activityList = Utils.getActivityList();
        for (int i = activityList.size() - 1; i >= 0; --i) {// remove from top
            Activity activity = activityList.get(i);
            // sActivityList remove the index activity at onActivityDestroyed
            activity.finish();
        }
        System.exit(0);
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
