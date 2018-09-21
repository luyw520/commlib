package com.lu.library;

import android.content.Context;
import android.os.StrictMode;

import com.facebook.stetho.Stetho;
import com.lu.library.log.LogUtil;
import com.lu.library.monitor.BlockDetectByPrinter;
import com.lu.library.util.CrashHandlerUtil;

/**
 * Created by lyw.
 *
 * @author: lyw
 * @package: com.lu.library
 * @description: ${TODO}{ 类注释}
 * @date: 2018/9/21 0021
 */
public class LibContext {
    static LibContext libContext=new LibContext();
    private Context context;
    public static LibContext getInstance(){
        return libContext;
    }
    public static Context getAppContext(){
        return null;
    }
    public void init(Context context){
        this.context=context.getApplicationContext();
        BlockDetectByPrinter.start();
        initFacebook();
        init7_0_Camera();
    }
    /**
     * 初始化捕获日志辅助类
     */
    private void initCrashHandler() {
        LogUtil.d("BuildConfig.DEBUG:"+BuildConfig.DEBUG);
        //正式版才采用crash
        if (!BuildConfig.DEBUG){
            CrashHandlerUtil.getInstance().init(context);
            CrashHandlerUtil.getInstance().setCrashDir(Constant.CRASH_PATH);
        }

    }
    private void initFacebook() {
        Stetho.initializeWithDefaults(context);
    }
    private void init7_0_Camera() {
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }
}
