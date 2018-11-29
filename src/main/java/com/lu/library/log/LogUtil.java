package com.lu.library.log;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhouzj on 2018/7/30.
 */

public class LogUtil {
    private static String className;
    private static String methodName;
    private static int lineNumber;

    private static final int JSON_INDENT = 4;
    public static final String APP_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/veryfit2.2";
    public static final String LOG_PATH = APP_ROOT_PATH + "/log";
    public static final String GOOGLEFIT_PATH = LOG_PATH + "/googlefit/";
    private static SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat myLogSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final String login_log="login_log.txt";
    /**
     * 只是在控制台打印日志，不会保存到文件
     */
    public static void d(String tag, String text){
        Log.d(tag, text);
    }

    /**
     * 打印并且保存到日志
     * @param message
     * @param dirPath
     */
    public static void dAndSave(String message,String dirPath){

        getMethodNames(new Throwable().getStackTrace(),1);
        String log=createLog(message);
        LogService.p(dirPath, "dAndSave", log);
        Log.i(className, log);

    }
    /**
     * 只是在控制台打印日志，不会保存到文件
     * d级别的日志有些手机在logcat里面显示不出来，
     * 故打印的日志改成i级别
     */
    public static void d2(String message) {
        getMethodNames(new Throwable().getStackTrace(),2);
        Log.i(className, createLog(message));
    }
    /**
     * 只是在控制台打印日志，不会保存到文件
     * d级别的日志有些手机在logcat里面显示不出来，
     * 故打印的日志改成i级别
     */
    public static void d(String message) {
        getMethodNames(new Throwable().getStackTrace(),1);
        Log.i(className, createLog(message));
    }
    /**
     * 只是在控制台打印日志，不会保存到文件
     * d级别的日志有些手机在logcat里面显示不出来，
     * 故打印的日志改成i级别
     */
    public static void w(String message) {
        getMethodNames(new Throwable().getStackTrace(),1);
        Log.w(className, createLog(message));
    }
    public static void d(Object o) {
        String message=null;
       if (o==null){
           message="message is null";
       }else{
           message=o.toString();
       }
        getMethodNames(new Throwable().getStackTrace(),1);
        Log.i(className, createLog(message));
    }
    private static String createLog(String log) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("(");
        buffer.append(className);
        buffer.append(":");
        buffer.append(lineNumber);
        buffer.append(")");
        buffer.append("#");
        buffer.append(methodName);
        buffer.append("]");
        buffer.append(printIfJson(log));
        return buffer.toString();
    }
    public static String printIfJson(String msg) {

        String message;
        StringBuffer stringBuffer=new StringBuffer();
        try {
            int index=-1;
            if(msg.contains("{")){
                index=msg.indexOf("{");
            }else if (msg.contains("[")){
                index=msg.indexOf("{");
            }

            if (index!=-1){
                stringBuffer.append(msg.substring(0,index));
                msg=msg.substring(index);
            }

            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(JSON_INDENT);
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(JSON_INDENT);
            } else {
                message = msg;
            }
        } catch (Exception e) {
            message = msg;
        }
        stringBuffer.append(message);
        return stringBuffer.toString();
    }
    private static void getMethodNames(StackTraceElement[] sElements,int index) {
        className = sElements[index].getFileName();
        methodName = sElements[index].getMethodName();
        lineNumber = sElements[index].getLineNumber();
    }


    /**2017/8/10李游添加googlefit日志文件
     * 文件目录在/sdcard/veryfit2.2/log/googlefit
     * 一次googlefit文件
     * @param msg
     */
    public  static void writeGoogleFitLogInfotoFile(String msg){
        writeGoogleFitLogInfotoFile(null,msg);
    }

    /**2017/8/10李游添加googlefit日志文件
     * 文件目录在/sdcard/veryfit2.2/log/googlefit
     * 一次googlefit文件
     * @param msg
     */
    public  static void writeGoogleFitLogInfotoFile(String dirPath,String msg) {
        File file = new File(GOOGLEFIT_PATH, "googlefit_"+logfile.format(new Date())+".txt");
        writeMsgToFile(GOOGLEFIT_PATH,file.getName(),msg,true,true);
    }

    /**
     *
     * @param dirPath 文件存储路径 如果为空,则再/sdcard/veryfit2.2目录下
     * @param fileName 文件名
     * @param msg 存储的日志
     * @param append  是否接着文件写 true是 false直接覆盖
     * @param isDeleteLastFile  是否删除最近文件 true是 false
     */
    private static void writeMsgToFile(String dirPath,String fileName,String msg,boolean append,boolean isDeleteLastFile){
        if (TextUtils.isEmpty(fileName)){
            return;
        }
        if (TextUtils.isEmpty(msg)){
            return;
        }
        File dir=new File(APP_ROOT_PATH);
        if (!TextUtils.isEmpty(dirPath)){
            dir=new File(dirPath);
        }
        if(!dir.exists()){
            dir.mkdirs();
        }
        if (!TextUtils.isEmpty(dirPath)&&isDeleteLastFile){
            deleteOldFile(dir.getAbsolutePath());
        }
        File file=new File(dir,fileName);
        BufferedWriter bufferedWriter = null;
        try {
            if (bufferedWriter == null) {
                bufferedWriter = new BufferedWriter(new FileWriter(file, append));
            }
            msg="["+myLogSdf.format(new Date())+"]"+msg;
            bufferedWriter.write(msg);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            closeWrite(bufferedWriter);
        }
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
                    LogUtil.d("file1.getAbsolutePath():"
                            + file1.getAbsolutePath());
                    LogUtil.d("file1.delete():" + file1.delete());
                }

            }
        }
    }

    public static void closeWrite(BufferedWriter bufferedWriter){
        if (bufferedWriter != null) {
            try
            {
                bufferedWriter.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
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
     * 记录登录状态的日志
     * 在/veryfit2.2/log/目录下
     */
    public static void login_log(String msg){
        writeLogDug(msg,login_log,true);
    }

    /**
     * 存储在/veryfit2.2/目录下的日志
     */
    private static void writeLogDug(String msg,String fileName,boolean append ){
        writeMsgToFile(null,fileName,msg,append,false);
    }

}
