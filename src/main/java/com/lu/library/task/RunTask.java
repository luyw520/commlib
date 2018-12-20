package com.lu.library.task;


import com.lu.library.log.LogUtil;
import com.lu.library.util.ThreadUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lyw.
 *
 */
public class RunTask<T> implements Runnable,RunnableComplete {


    public AtomicInteger mCount = new AtomicInteger(0);
    private T data;
    public RunTask(){

    }
    public RunTask(T data){
        this.data=data;
    }
    public void setData(T data){
        this.data=data;
    }
    @Override
    public void run() {
        ThreadUtil.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                onPreExecute(data);
            }
        });
    }
    public void onPreExecute(T t){

    }
    public final boolean isExeRunFinish(){
        return mCount.compareAndSet(1,1);
    }
    /**
     * 标志完成了任务，继续下一个任务
     */
    @Override
    public final void runFinish(){
        LogUtil.d("runFinish ....");
        mCount.incrementAndGet();
    }
}
