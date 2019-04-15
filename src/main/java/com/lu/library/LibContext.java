package com.lu.library;

import android.content.Context;
import android.os.StrictMode;

import com.facebook.stetho.Stetho;
import com.lu.library.logger.TxtFormatStrategy;
import com.lu.library.monitor.BlockDetectByPrinter;
import com.lu.library.util.CrashUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * Created by lyw.
 */
public class LibContext {
    static LibContext libContext=new LibContext();
    private Context context;
    public static LibContext getInstance(){
        return libContext;
    }
    public static Context getAppContext(){
        return libContext.context;
    }
    public void init(Context context){
        this.context=context.getApplicationContext();
        BlockDetectByPrinter.start();
        initFacebook();
        init7_0_Camera();
        initLogger();
    }
    public String getLogPath(){
        String packageName=context.getPackageName();
        String name=packageName.substring(packageName.indexOf(".")+1,packageName.lastIndexOf("."));
        return name;

    }
    private void initLogger() {
        //DEBUG版本才打控制台log
//        if (BuildConfig.DEBUG) {
            Logger.addLogAdapter(new AndroidLogAdapter(PrettyFormatStrategy.newBuilder().
                    tag(getLogPath()).build()));
//        }
        //把log存到本地
        Logger.addLogAdapter(new DiskLogAdapter(TxtFormatStrategy.newBuilder().
                tag(getLogPath()).build(context.getPackageName(),  getLogPath())));
    }

    /**
     * 初始化捕获日志辅助类
     */
    public void initCrashHandler(String dir) {
//        LogUtil.d("BuildConfig.DEBUG:"+BuildConfig.DEBUG);
        //正式版才采用crash
        if (!BuildConfig.DEBUG){
            CrashUtil.getInstance().init(context);
            CrashUtil.getInstance().setCrashDir(dir);
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
