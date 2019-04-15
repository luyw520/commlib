package com.lu.library.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.lu.library.LibContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyw.
 *
 * @author: lyw
 * @package: com.id.app.comm.lib.utils
 * @description: ${TODO}{ 类注释}
 * @date: 2018/9/21 0021
 */
public class PermissionUtil {

    public static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public void setRequsetResult(RequsetResult requsetResult) {
        this.requsetResult = requsetResult;
    }

    private RequsetResult requsetResult;
    /**
     * 检查是否有权限
     * @param permission
     * @return
     */
    @TargetApi(value = Build.VERSION_CODES.M)
    public static List<String> findDeniedPermissions(String... permission){
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return new ArrayList<>();
        }
        List<String> denyPermissions = new ArrayList<>();
        for(String value : permission){
            if(ContextCompat.checkSelfPermission(LibContext.getAppContext(),value) != PackageManager.PERMISSION_GRANTED){
                denyPermissions.add(value);
            }
        }
        return denyPermissions;
    }
    /**
     * Return whether the app can draw on top of other apps.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isGrantedDrawOverlays() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AppOpsManager aom = (AppOpsManager) Utils.getApp().getSystemService(Context.APP_OPS_SERVICE);
            if (aom == null) return false;
            int mode = aom.checkOpNoThrow(
                    "android:system_alert_window",
                    android.os.Process.myUid(),
                    Utils.getApp().getPackageName()
            );
            return mode == AppOpsManager.MODE_ALLOWED || mode == AppOpsManager.MODE_IGNORED;
        }
        return Settings.canDrawOverlays(Utils.getApp());
    }
    /**
     * 检测权限，如果返回true,有权限 false 无权限
     * @param permission 权限
     * @return 是否有权限
     */
    public static boolean checkSelfPermission(String... permission){
        if ((findDeniedPermissions(permission)).isEmpty()){
            return true;
        }
        return false;
    }
    @TargetApi(Build.VERSION_CODES.M)
    private static void startWriteSettingsActivity(final Activity activity, final int requestCode) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        intent.setData(Uri.parse("package:" + Utils.getApp().getPackageName()));
        if (!isIntentAvailable(intent)) {
            launchAppDetailsSettings();
            return;
        }
        activity.startActivityForResult(intent, requestCode);
    }
    /**
     * 打开应用具体设置
     * Launch the application's details settings.
     */
    public static void launchAppDetailsSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + Utils.getApp().getPackageName()));
        if (!isIntentAvailable(intent)) return;
        Utils.getApp().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
    private static boolean isIntentAvailable(final Intent intent) {
        return Utils.getApp()
                .getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
                .size() > 0;
    }
    /**
     * 申请权限
     * @param object
     * @param requestCode
     * @param permissions
     */
    @TargetApi(value = Build.VERSION_CODES.M)
    public static void requestPermissions(Object object, int requestCode, String... permissions){
        if(!isOverMarshmallow()) {

            return;
        }
        Activity activity=null;
        if (object instanceof  Activity){
            activity= (Activity) object;
        }else if(object instanceof Fragment){
            activity= ((Fragment)object).getActivity();
        }
        List<String> deniedPermissions = findDeniedPermissions(permissions);

        if(deniedPermissions.size() > 0){
            if(object instanceof Activity){
                ((Activity)object).requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
            } else if(object instanceof Fragment){
                ((Fragment)object).requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
            } else {
                throw new IllegalArgumentException(object.getClass().getName() + " is not supported");
            }

        }
    }
    public  void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults){
        List<String> deniedPermissions = new ArrayList<>();
        for(int i=0; i<grantResults.length; i++){
            if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                deniedPermissions.add(permissions[i]);
            }
        }
        if(deniedPermissions.size() > 0){
            requsetResult.requestPermissionsFail(requestCode);
        } else {
            requsetResult.requestPermissionsSuccess(requestCode);
        }
    }
    public interface RequsetResult{
        void requestPermissionsSuccess(int requestCode);
        void requestPermissionsFail(int requestCode);
    }
}
