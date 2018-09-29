package com.lu.library.task;

import android.os.Handler;
import android.os.Looper;

public class MainThread {



//    private static Handler handler=new Handler(Looper.getMainLooper());
    public static void runOnMainThread(Runnable runnable) {
        Handler handler=new Handler(Looper.getMainLooper());
        if(!(Thread.currentThread() == Looper.getMainLooper().getThread())){
            handler.post(runnable);
        }else{
          runnable.run();
        }
    }
    public static void delayTask(Runnable task,int delay){
        Handler handler=new Handler(Looper.getMainLooper());
        handler.postDelayed(task,delay);
    }
}