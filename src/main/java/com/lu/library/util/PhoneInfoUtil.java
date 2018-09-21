package com.lu.library.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.id.app.comm.lib.IdoApp;

import java.io.File;

/**
 * Created by zhouzj on 2018/7/26.
 */

public class PhoneInfoUtil {


    /**
     * 获取设备密度
     * @param context 全局context
     * @return 设备dpi
     */
    public static int getDeviceDpi(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.densityDpi;
    }

    /**
     * 获取设备宽 高 单位像素
     * @param context 全局context
     * @return int[]
     *      [0] 设备宽(像素)
     *      [1] 设备高(像素)
     */
    public static int[] getDeviceSize(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return new int[]{dm.widthPixels, dm.heightPixels};
    }

    /**
     * 根据手机的分辨率从从dp转成为px(像素)
     * @param context 全局context
     * @param dpValue dp值
     * @return px像素值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     * @param context 全局context
     * @param pxValue px像素值
     * @return dp值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取手机设备id 需要READ_PHONE_STATE权限
     * @param context 全局context
     * @return device id
     */
    @SuppressLint("MissingPermission")
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 获取手机sim卡id 需要READ_PHONE_STATE权限
     * @param context 全局context
     * @return sim id
     */
    @SuppressLint("MissingPermission")
    public static String getSubscriberId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSubscriberId();
    }

    /**
     * 判断是否平板设备
     * @param context 全局context
     * @return true:平板,false:手机
     */
    public static boolean isTabletDevice(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 判断sd卡是否存在
     * @return true:存在；false：不存在
     */
    public static boolean isSdcardExisting() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取手机内部存储剩余空间 单位byte
     * @return
     */
    @SuppressWarnings("deprecation")
    public static long getAvailableInternalStorageSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());

        if(Build.VERSION.SDK_INT >= 18) {
            return stat.getAvailableBytes();
        } else {
            return (long) stat.getAvailableBlocks() * stat.getBlockSize();
        }
    }

    /**
     * 获取手机内部总存储空间 单位byte
     * @return
     */
    @SuppressWarnings("deprecation")
    public static long getTotalInternalStorageSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());

        if(Build.VERSION.SDK_INT >= 18) {
            return stat.getTotalBytes();
        } else {
            return (long) stat.getBlockCount() * stat.getBlockSize();
        }
    }

    /**
     * 获取SDCARD剩余存储空间 单位byte
     * @return
     */
    public static long getAvailableExternalStorageSize() {
        if (isSdcardExisting()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());

            if(Build.VERSION.SDK_INT >= 18) {
                return stat.getAvailableBytes();
            } else {
                return (long) stat.getAvailableBlocks() * stat.getBlockSize();
            }
        } else {
            return 0L;
        }
    }

    /**
     * 获取SDCARD总的存储空间 单位byte
     * @return
     */
    public static long getTotalExternalStorageSize() {
        if (isSdcardExisting()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());

            if(Build.VERSION.SDK_INT >= 18) {
                return stat.getTotalBytes();
            } else {
                return (long) stat.getBlockCount() * stat.getBlockSize();
            }
        } else {
            return 0;
        }
    }

    /**
     * 是否打开GPS
     *
     * @return
     */
    public static boolean isGPSOpen() {

        LocationManager locationManager = ((LocationManager) IdoApp.getAppContext().getSystemService(Context.LOCATION_SERVICE));
        return  locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

    }

    public static boolean isBluetoothOpen(){
        return BluetoothAdapter.getDefaultAdapter().isEnabled();
    }

    public static boolean hasBluetoothPermission(){
        return ActivityCompat.checkSelfPermission(IdoApp.getAppContext(), Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasBluetoothAdminPermission(){
        return ActivityCompat.checkSelfPermission(IdoApp.getAppContext(), Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasLocationPermission(){
        return ActivityCompat.checkSelfPermission(IdoApp.getAppContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 获取当前系统语言
     *
     * @return
     */
    public static String getLanguage() {
        return IdoApp.getAppContext().getResources().getConfiguration().locale.getLanguage();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersionCode() {
        return Build.VERSION.RELEASE;
    }

    public static int getSystemVersion() {
        return Build.VERSION.SDK_INT;
    }
}
