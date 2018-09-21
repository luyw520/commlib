package com.lu.library.util;

import android.R.integer;
import android.os.AsyncTask;

public class AsyncTaskUtil {

    private IAsyncTaskCallBack iAsyncTaskCallBack;
    //	private Context context;
    private MyTask myTask;
    public AsyncTaskUtil() {

    }

    public AsyncTaskUtil(IAsyncTaskCallBack iAsyncTaskCallBack) {
//		this.context=context;
        this.iAsyncTaskCallBack=iAsyncTaskCallBack;
    }

    public AsyncTaskUtil setIAsyncTaskCallBack(IAsyncTaskCallBack iAsyncTaskCallBack) {
        this.iAsyncTaskCallBack = iAsyncTaskCallBack;
        return this;
    }

    public void execute(String... params) {
        myTask=new MyTask();
        myTask.execute(params);
    }
    public void cancel(boolean mayInterruptIfRunning){
        if (myTask!=null){
            myTask.cancel(mayInterruptIfRunning);
        }

    };
    class MyTask extends AsyncTask<String, integer, Object> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Object doInBackground(String... arg0) {
            return iAsyncTaskCallBack.doInBackground(arg0);
        }

        @Override
        protected void onPostExecute(Object result) {
            iAsyncTaskCallBack.onPostExecute(result);
        }

    }


    public interface IAsyncTaskCallBack {
        public Object doInBackground(String... arg0);

        public void onPostExecute(Object result);
    }
    public static class AsyncTaskCallBackAdapter implements IAsyncTaskCallBack{

        @Override
        public Object doInBackground(String... arg0) {
            return null;
        }

        @Override
        public void onPostExecute(Object result) {

        }
    }
}
