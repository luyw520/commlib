package com.lu.library.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;

import com.lu.library.LibContext;
import com.lu.library.log.LogUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

/**
 * @author: lyw
 * @description: ${TODO}{获取APP信息的辅助类}
 * @date: 2016/11/22 15:33
 */
public class AppUtil {

    public static boolean isCustomization = false;  //如果是定制项目则改为true
    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context
     * @return true 表示开启
     */
    public static boolean isGpsOpen(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }

        return false;
    }

    /**
     * 强制帮用户打开GPS
     *
     * @param context
     */
    public static void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否是中国地区
     *
     * @return
     */
    public static boolean isInChina() {
        return Locale.getDefault().getCountry().equals("CN");
    }

    public static int getAppMaxMemory(){
        Runtime rt=Runtime.getRuntime();
        long maxMemory=rt.maxMemory();
//        log.i());
//        tvMsg.setText("maxMemory:"+Long.toString(maxMemory/(1024*1024))+"MB");
        int mb= (int) (maxMemory/(1024*1024));
        return mb;
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
    @SuppressLint("MissingPermission")
    public static String getPhoneInfo(Context context){
        TelephonyManager mTm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("IMEI:"+mTm.getDeviceId());
        stringBuffer.append(",type:"+ Build.MODEL);
        stringBuffer.append(",SDK_INT:"+ Build.VERSION.SDK_INT);
        stringBuffer.append(",RELEASE:"+ Build.VERSION.RELEASE);
        stringBuffer.append(",number:"+mTm.getLine1Number());
        return stringBuffer.toString();
    }
    public static String getPhoneLogInfo(Context context){
        TelephonyManager mTm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("品牌:"+ Build.MANUFACTURER);
        stringBuffer.append("\n设备名称:"+ Build.MANUFACTURER+Build.DEVICE);
        stringBuffer.append("\n型号:"+ Build.MODEL);
        stringBuffer.append("\n版本号:"+ Build.DISPLAY);
        stringBuffer.append("\n处理器:"+getCpuInfo()[0]);
        stringBuffer.append("\n运行内存:"+getAvailMemory(context));
        String[] size=getSDTotalSize();
        stringBuffer.append("\n手机存储: 可用空间:"+size[1]+",总容量:"+size[0]+",已使用空间:"+size[2]);
        DisplayMetrics displayMetrics=context.getResources().getDisplayMetrics();
        stringBuffer.append("\n分辨率:"+displayMetrics.widthPixels+"*"+displayMetrics.heightPixels);
        stringBuffer.append("\nandroid版本:"+Build.VERSION.SDK_INT);
        stringBuffer.append("\napp版本:"+getVersionName(context));
//        stringBuffer.append(",number:"+mTm.getLine1Number());

        LogUtil.d(""+Build.BOARD);
        LogUtil.d(Build.BOOTLOADER);
        LogUtil.d(Build.BRAND);
        LogUtil.d(Build.DEVICE);
        LogUtil.d(Build.DISPLAY);
        LogUtil.d(Build.FINGERPRINT);
        LogUtil.d(Build.HARDWARE);
        LogUtil.d(Build.ID);
        LogUtil.d(Build.MANUFACTURER);
        LogUtil.d(Build.MODEL);
        LogUtil.d(Build.PRODUCT);
        LogUtil.d(Build.SERIAL);
        LogUtil.d(Build.TAGS);
        LogUtil.d(Build.USER);
        LogUtil.d(Build.CPU_ABI);
        LogUtil.d(""+Build.VERSION.SDK_INT);
        LogUtil.d(""+getCpuInfo()[0]+","+getCpuInfo()[1]);
        LogUtil.d(stringBuffer.toString());

        return stringBuffer.toString();
    }

    private static String[] getCpuInfo() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String[] cpuInfo = {"", ""};  //1-cpu型号  //2-cpu频率
        String[] arrayOfString;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++) {
                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
            }
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            cpuInfo[1] += arrayOfString[2];
            localBufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtil.d("cpuinfo:" + cpuInfo[0] + " " + cpuInfo[1]);
        return cpuInfo;
    }


    /**
     * 根据路径获取内存状态
     * @return
     */
    private static String getMemoryInfo(Context context) {
        // 获得手机内部存储控件的状态
        File dataFileDir = Environment.getDataDirectory();
        // 获得一个磁盘状态对象
        StatFs stat = new StatFs(dataFileDir.getPath());

        long blockSize = stat.getBlockSize();   // 获得一个扇区的大小

        long totalBlocks = stat.getBlockCount();    // 获得扇区的总数

        long availableBlocks = stat.getAvailableBlocks();   // 获得可用的扇区数量

        // 总空间
        String totalMemory =  Formatter.formatFileSize(context, totalBlocks * blockSize);
        // 可用空间
        String availableMemory = Formatter.formatFileSize(context, availableBlocks * blockSize);

        return "总空间: " + totalMemory + "\n可用空间: " + availableMemory;
    }
    //  总容量，未使用，已使用
    private  static String[] getSDTotalSize(){
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        long size = sf.getBlockSize();//SD卡的单位大小
        long total = sf.getBlockCount();//总数量
        long available = sf.getAvailableBlocks();//可使用的数量
        DecimalFormat df = new DecimalFormat();
        df.setGroupingSize(3);//每3位分为一组
//总容量
        String totalSize = (size*total)/1024>=1024?df.format(((size*total)/1024)/1024)+"MB":df.format((size*total)/1024)+"KB";
//未使用量
        String avalilable = (size*available)/1024>=1024?df.format(((size*available)/1024)/1024)+"MB":df.format((size*available)/1024)+"KB";
//已使用量
        String usedSize = size*(total-available)/1024>=1024?df.format(((size*(total-available))/1024)/1024)+"MB":df.format(size*(total-available)/1024)+"KB";
        String[] totalSizeStr=new String[]{totalSize,avalilable,usedSize};
        return totalSizeStr;
    }
    /**
     * 获取手机内存大小
     *
     * @return
     */
    private static String getTotalMemory(Context context) {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                Log.i(str2, num + "\t");
            }

            initial_memory = Integer.valueOf(arrayOfString[1]).intValue()/1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();

        } catch (IOException e) {
        }
        LogUtil.d("initial_memory:"+initial_memory);
        return Formatter.formatFileSize(context, initial_memory);// Byte转换为KB或者MB，内存大小规格化
    }
    /**
     * //获得系统可用内存信息
     *
     * @return
     */
    private static String getAvailMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return Formatter.formatFileSize(context, mi.availMem);
    }
    /**
     * 获取手机IMEI号
     */
    public static String getIMEI(Context context) {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            return "";
        }
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        return imei;
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
