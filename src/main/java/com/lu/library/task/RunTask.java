package com.lu.library.task;


import com.lu.library.log.LogUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lyw.
 *
 * @author: lyw
 * @package: com.ido.veryfitpro.common.task
 * @description: ${TODO}{ 类注释}
 * @date: 2018/9/18 0018
 */
public class RunTask<T> implements Runnable {


    public AtomicInteger mCount = new AtomicInteger(0);

    @Override
    public void run() {

    }
    public void onPreExecute(T t){

    }
    public final boolean isExeRunFinish(){
        return mCount.compareAndSet(1,1);
    }
    /**
     * 标志完成了任务，继续下一个任务
     */
    public final void runFinish(){
        LogUtil.d("runFinish ....");
        mCount.incrementAndGet();
    }
}
