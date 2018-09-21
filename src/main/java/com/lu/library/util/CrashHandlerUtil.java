package com.lu.library.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import com.lu.library.LibContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 奔溃日志铺货辅助类
 */
public class CrashHandlerUtil implements Thread.UncaughtExceptionHandler {

	public static final String TAG = "CrashHandlerUtil";
	//CrashHandler实例
	private static CrashHandlerUtil INSTANCE = new CrashHandlerUtil();
	public String path;
	//系统默认的UncaughtException处理类
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	//程序的Context对象
	private Context mContext;
	//用来存储设备信息和异常信息
	private Map<String, String> infos = new HashMap<String, String>();
	//用于格式化日期,作为日志文件名的一部分
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

	/** 保证只有一个CrashHandler实例 */
	private CrashHandlerUtil() {
	}

	/** 获取CrashHandler实例 ,单例模式 */
	public static CrashHandlerUtil getInstance() {
		return INSTANCE;
	}

	/**
	 * 初始化
	 *
	 * @param context
	 */
	public void init(Context context) {
		mContext = context;
		//获取系统默认的UncaughtException处理器
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		//设置该CrashHandler为程序的默认处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	private String dir;

	/**
	 * 设置保存奔溃信息日志目录
	 * @param crashDir
	 */
	public void setCrashDir(String crashDir){
		dir=crashDir;
	}
	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			//如果用户没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				Log.e(TAG, "error : ", e);
			}
			//退出程序
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		}
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
	 *
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false.
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}
		//使用Toast来显示异常信息
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				Looper.loop();
			}
		}.start();
		//收集设备参数信息
		collectDeviceInfo(mContext);
		//保存日志文件
		saveCrashInfo2File(ex);
		return true;
	}

	/**
	 * 收集设备参数信息
	 * @param ctx
	 */
	public void collectDeviceInfo(Context ctx) {
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null" : pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		} catch (PackageManager.NameNotFoundException e) {
			Log.e(TAG, "an error occured when collect package info", e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
				Log.d(TAG, field.getName() + " : " + field.get(null));
			} catch (Exception e) {
				Log.e(TAG, "an error occured when collect crash info", e);
			}
		}
	}

	/**
	 * 保存错误信息到文件中
	 *
	 * @param ex
	 * @return	返回文件名称,便于将文件传送到服务器
	 */
	private String saveCrashInfo2File(Throwable ex) {
		if (ContextCompat.checkSelfPermission(LibContext.getAppContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
			return  "";
		}
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : infos.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key + "=" + value + "\n");
		}

		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);
		try {
//			long timestamp = System.currentTimeMillis();
			String time = formatter.format(new Date());
			String fileName = "crash_log_"+time +".txt";

			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//				String path = Constant.CRASH_PATH;
				if (TextUtils.isEmpty(dir)){
					dir=Environment.getExternalStorageDirectory().getAbsolutePath();
				}else{
					deleteOldFile(dir);
				}
				File dirFile = new File(dir);
				if (!dirFile.exists()) {
					dirFile.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(new File(dir,fileName));
				fos.write(sb.toString().getBytes());
				fos.close();
			}
			return fileName;
		} catch (Exception e) {
			Log.e(TAG, "an error occured while writing url...", e);
		}
		return null;
	}
	/**
	 * 计算给定日期距离当前日期多少天
	 *
	 * @param oDate
	 * @return
	 */
	public static int daysOfTwo(Date oDate) {
		Calendar aCalendar = Calendar.getInstance();

		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);

		aCalendar.setTime(oDate);

		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

		return day1 - day2;

	}

	/**
	 * 计算给定日期距离当前日期多少天
	 *
	 * @param oDate
	 * @return
	 */
	public static int daysOfTwo(long oDate) {
		return daysOfTwo(new Date(oDate));

	}
	/**
	 * 删除超过10天的日志
	 *
	 * @param dir
	 */
	private static void deleteOldFile(String dir) {

		File file2 = new File(dir);
		if (!file2.exists()) {
			return;
		}
		File[] files = file2.listFiles();
		if (files != null && file2.length()>10) {
			for (int j = 0; j < files.length; j++) {
				File file1 = files[j];
				if (daysOfTwo(file1.lastModified()) > 10) {
//					DebugLog.d("file1.getAbsolutePath():"
//							+ file1.getAbsolutePath());
					file1.delete();
				}

			}
		}
	}

}