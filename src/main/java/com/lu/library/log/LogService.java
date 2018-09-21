package com.lu.library.log;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;

import com.lu.library.LibContext;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: zhouzj
 * @date: 2017/11/2 15:11
 * 日志输出工具，
 * !!!!!!!!!注意：只有P，E级别的输出，才会保存到文件!!!!!!!!!!
 */
public class LogService {
    private static final String FILE_NAME_PATTERN = "yyyyMMdd";
    private static final String FILE_TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss.SSSZ";
    private static final String LINE_SEP = System.getProperty("line.separator");
    private static final String TAG = "[IDO_APP] LogService";
    private static final String LOG_FILE_PREFIX_NAME = ".log";

    private ConcurrentLinkedQueue<String[]> mLogQueue = new ConcurrentLinkedQueue<>();
    private volatile boolean mIsStopLog = false;
    private Thread mLogThread;
    private Lock mLock = new ReentrantLock();
    private Condition mCondition = mLock.newCondition();

    private static LogService instance = new LogService();
    private static boolean isPermissionOk = false;

    private static void checkPermission(){
        int writeP = ActivityCompat.checkSelfPermission(LibContext.getAppContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readP = ActivityCompat.checkSelfPermission(LibContext.getAppContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (writeP == PackageManager.PERMISSION_GRANTED && readP == PackageManager.PERMISSION_GRANTED){
            isPermissionOk = true;
        }else {
            isPermissionOk = false;
            Log.e(TAG, "not allowed permission[WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE], LogTool is disabled!");
        }
    }

    public static void init(){
        Log.i(TAG, "init...");
        checkPermission();

        if (isPermissionOk){
            instance.start();
        }

    }

    public static void destroy(){
        Log.i(TAG, "destroy...");
        if (isPermissionOk) {
            instance.stop();
        }
    }


    private LogService(){

    }

    private Runnable mLooperRunnable = new Runnable() {

        public void run() {

            deleteOutDateLog();
            while (!mIsStopLog){
                mLock.lock();
                String[] logMsg;
                try {
                    if (mLogQueue.isEmpty()) {
                        mCondition.await();
                    }
                    if (mIsStopLog){
                        break;
                    }

                    logMsg = mLogQueue.poll();
                    String logDir = logMsg[0];
                    String log = logMsg[1];
                    if (createLogFileDir(logDir)) {
                        writeToFile(logDir, log);
                    }else {
                        Log.e(TAG, "createLogFileDir failed:" + logDir);
                    }

                } catch(InterruptedException e) {
                    Log.e(TAG, e.getMessage(), e);
                    Thread.currentThread().interrupt();
                } finally {
                    mLock.unlock();
                }

            }

            Log.i(TAG, "exit loop ok!");

        }
    };

    private  boolean createLogFileDir(String dir) {
        File file = new File(dir);
        if (!file.exists()){
            return file.mkdirs();
        }

        return true;
    }



    private void start() {
        mIsStopLog = false;
        if(mLogThread == null) {
            mLogThread = new Thread(mLooperRunnable);
        }
        if(!mLogThread.isAlive()) {
            mLogThread.start();
        }
    }

    private void stop() {
        mIsStopLog = true;
        if((mLogThread == null) || (!mLogThread.isAlive())) {
            return;
        }
        mLock.lock();
        mCondition.signal();
        mLock.unlock();
        mLogQueue.clear();
        mLogThread = null;
    }


    public static void e(String dirPath, String pTag, String pMessage) {
        instance.writeLogToBuffer(dirPath, "E", pTag, pMessage);
    }

    public static void p(String dirPath, String pTag, String pMessage) {
        instance.writeLogToBuffer(dirPath, "P", pTag, pMessage);
    }

    private void writeLogToBuffer(String dirPath, String logLevel, String tag, String text) {
        if (!isPermissionOk){
            checkPermission();
            return;
        }
        if((mLogThread == null) || (!mLogThread.isAlive()) || (mIsStopLog)) {
            start();
        }
        if(TextUtils.isEmpty(getLogPathSdcardDir()) || TextUtils.isEmpty(dirPath)) {
            Log.e(TAG, "getLogPathSdcardDir or dirPath is null");
            return;
        }

        if((!TextUtils.isEmpty(tag)) && (tag.length() < 20)) {
            for(int i = tag.length(); i < 20; i ++) {
                tag = tag + " ";
            }
        }

        StringBuilder builder = new StringBuilder();
        String needWriteMessage =
                builder.append(logLevel)
                        .append("    [").append(getLogTimeString()).append("]    ")
                        .append("    [").append(tag).append("]    ")
                        .append(text)
                        .append(LINE_SEP)
                        .toString();

        //0:文件路径 //1:日志信息
        String[] logMsg = new String[]{dirPath, needWriteMessage};

        mLock.lock();
        mLogQueue.add(logMsg);
        mCondition.signal();
        mLock.unlock();
    }

    private String getLogPathSdcardDir() {
        return null;
    }

    private void writeToFile(String dirPath, String log) {
        BufferedWriter bw = null;
        String fullPath = dirPath + File.separator + getFileName();
        File file = new File(fullPath);
        boolean isFileExist = true;
        if (!file.exists()){
            try {
                isFileExist = file.createNewFile();
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            }
        }
        if (!isFileExist){
            Log.e(TAG, "create log file failed!");
            return;
        }

        try {
            bw = new BufferedWriter(new FileWriter(file, true));
            bw.write(log);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            }
        }
    }



    private void deleteOutDateLog() {
        List<String> logFilePathList = new ArrayList<>();
        if (logFilePathList == null || logFilePathList.size() == 0){
            return;
        }

        for (String dirPath : logFilePathList) {

            File dir = new File(dirPath);
            if (!dir.exists()) {
                return;
            }
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.isDirectory()) {
                    continue;
                }

                Date beforeDate = getDateBefore();
                if (file.getName().endsWith(LOG_FILE_PREFIX_NAME)) {
                    String logDateStr = file.getName().replace(LOG_FILE_PREFIX_NAME, "");
                    Date logCalendar = getFileDateByStr(logDateStr);
                    if (logCalendar.before(beforeDate)) {
                        file.delete();
                    }
                }
            }
        }

    }

    private Date getDateBefore() {
        Calendar now = Calendar.getInstance();

//        now.set(Calendar.DAY_OF_MONTH, (now.get(Calendar.DAY_OF_MONTH) - IdoApp.getLogFileSaveDays()));
        now.set(Calendar.HOUR, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        return now.getTime();
    }

    private synchronized Date getFileDateByStr(String dateStr) {
        Date date = Calendar.getInstance().getTime();
        synchronized(LogService.class) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FILE_NAME_PATTERN, Locale.getDefault());
                date = simpleDateFormat.parse(dateStr);
            } catch(ParseException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
        return date;
    }

    private synchronized String getFileName(){
        Date date = Calendar.getInstance().getTime();
        synchronized(LogService.class) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FILE_NAME_PATTERN, Locale.getDefault());
            return simpleDateFormat.format(date) + LOG_FILE_PREFIX_NAME;

        }
    }

    private synchronized String getLogTimeString(){
        Date date = Calendar.getInstance().getTime();
        synchronized(LogService.class) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FILE_TIMESTAMP_PATTERN, Locale.getDefault());
            return simpleDateFormat.format(date);

        }
    }

}


