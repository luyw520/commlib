package com.lu.library.task;


import com.lu.library.log.LogUtil;

import java.util.ArrayDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lyw.
 *
 * @author: lyw
 * @package: com.ido.veryfitpro.common.task
 * @description: ${TODO}{ 串行的任务}
 * @date: 2018/9/18 0018
 */
public class AsyncTask {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    // We want at least 2 threads and at most 4 threads in the core pool,
    // preferring to have 1 less than the CPU count to avoid saturating
    // the CPU with background work
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE_SECONDS = 30;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
        }
    };

    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>(128);

    /**
     * An {@link Executor} that can be used to execute tasks in parallel.
     */
    public static final Executor THREAD_POOL_EXECUTOR;

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                sPoolWorkQueue, sThreadFactory);
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        THREAD_POOL_EXECUTOR = threadPoolExecutor;
    }

    /**
     * An {@link Executor} that executes tasks one at a time in serial
     * order.  This serialization is global to a particular process.
     */
    public static final Executor SERIAL_EXECUTOR = new SerialExecutor();


    private static volatile Executor sDefaultExecutor = SERIAL_EXECUTOR;
    private static final int TASK_TIME_OUT=10*0000;
    private static class SerialExecutor implements Executor {
        final ArrayDeque<Runnable> mTasks = new ArrayDeque<Runnable>();
        Runnable mActive;

        public synchronized void execute(final Runnable r) {

            mTasks.offer(new Runnable() {
                public void run() {
                    try {
                        r.run();
                    } finally {

//                        LogUtil.d(Thread.currentThread().getName()+",........."+mTasks.size());
                        //最后一个不用等待了....
                        if (mTasks.isEmpty()){
                           return;
                        }
                        if (r instanceof RunTask){
                            RunTask runTask= (RunTask) r;
//                            LogUtil.d(Thread.currentThread().getName()+",........."+runTask.mCount.get());
                           for(;;){
//                               LogUtil.d(Thread.currentThread().getName()+",wait........."+runTask.mCount.get());
                               if (runTask.isExeRunFinish())
                                   break;
                           }
                        }

                        scheduleNext();

                    }
                }
            });
//            LogUtil.d("add Runnable....mTasks:"+mTasks.size());
            if (mActive == null) {
                scheduleNext();
            }
        }

        protected synchronized void scheduleNext() {
            if ((mActive = mTasks.poll()) != null) {
                LogUtil.d("execute Runnable....");
                THREAD_POOL_EXECUTOR.execute(mActive);
            }
        }
    }
    public <Params> AsyncTask execute(RunTask<Params> runTask,Params params){
        runTask.onPreExecute(params);
        sDefaultExecutor.execute(runTask);
        return this;
    }
}
