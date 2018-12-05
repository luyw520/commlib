package com.lu.library.http;

import android.content.Context;
import android.text.TextUtils;

import com.lu.library.log.LogUtil;
import com.lu.library.util.AsyncTaskUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.lu.library.Constant.SERVER_PATH;
import static com.lu.library.util.ThreadUtil.runOnMainThread;


/**
 * Created by lyw.
 *
 * @author: lyw
 * @package: com.id.app.comm.lib.http
 * @description: ${TODO}{ HTTP顶层父类}
 * @date: 2018/8/7 0007
 */
public abstract class AbstractHttpManager {

    protected Context mContext;
    /**
     * 超时时间10S
     */
    protected final static int TIME_OUT = 10;

    /**
     * 通用的API
     */
    // 正式环境
    public static final String API = "http://veryfitproapi.veryfitplus.com/";
    // 测试环境
//    public static final String API_TEST = "http://apitest.dongha.cn:84/";
    public static final String API_TEST = "http://proapitest.veryfitplus.com/";

    static boolean isTest = true;
    /**
     * 基URL
     */
    protected static String BASE_URL = isTest ? API_TEST : API;

    public AbstractHttpManager(Context context) {
        this.mContext = context.getApplicationContext();
    }

//    /**
//     * get 请求服务器数据，返回特定数据结构
//     * @param subUrl
//     * @param parameters
//     * @param callback
//     * @param <T>
//     */
//    public abstract <T> void getRequest(String subUrl,Map<String, Object> parameters,IHttpCallback<T> callback);

    /**
     * @param subUrl
     * @param parameters
     * @param callback
     * @param isGet
     */
    public abstract void getRequestString(String subUrl, Map<String, Object> parameters, IHttpCallback<String> callback, boolean isGet);

    /**
     * application/json; charset=utf-8 方式获取
     *
     * @param subUrl
     * @param parameters
     * @param callback
     * @param isGet
     */
    public abstract void getRequestJsonString(String subUrl, Map<String, Object> parameters, IHttpCallback<String> callback, boolean isGet);
//    public abstract <T> void postRequest(String subUrl,Map<String, Object> parameters,IHttpCallback<T> callback);


    public abstract void postRequestJsonString(String subUrl,String json,IHttpCallback<String> callback);

    /**
     * 下载文件
     *
     * @param url      url
     * @param dir      文件夹
     * @param fileName 文件名
     * @param callback 回调
     */
    public abstract void down(String url, String dir, String fileName, IHttpCallback<String> callback);

    public abstract void upload(String subUrl, String filePath, String name, Map<String, Object> parameters, IHttpCallback<String> callback);

    public abstract void upload(String subUrl, Map<String, String> filePathMap, Map<String, Object> parameters, IHttpCallback<String> callback);



//    public abstract <T> void unload(String url,String path,IHttpCallback<T> progressCallback);

    /**
     * 下载文件
     *
     * @param urlStr   url路径
     * @param filePath 文件路径
     * @param callback 回调
     */
    public void downFile(final String urlStr, final String filePath, final IHttpProgressCallback callback) {

        if (TextUtils.isEmpty(urlStr) || urlStr.equals("null")) {
            if (callback != null) {
                callback.onError(new HttpException("urlStr is null"));
            }
            return;
        }

        final String url = "http" + urlStr.substring(urlStr.indexOf(":"), urlStr.length());
        LogUtil.d("downLoad...........urlStr:" + urlStr);
        LogUtil.d("downLoad...........url:" + url);
        new AsyncTaskUtil(new AsyncTaskUtil.AsyncTaskCallBackAdapter() {
            @Override
            public Object doInBackground(String... arg0) {

                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).build();
                    final Call call = client.newCall(request);
                    Response response = call.execute();
                    if (response.isSuccessful()) {
                        ResponseBody body = response.body();
                        long size = body.contentLength();
                        long progress = 0;

                        InputStream is = body.byteStream();
                        OutputStream fos = new FileOutputStream(filePath);
                        int len;
                        byte[] b = new byte[1024];

                        while ((len = is.read(b)) != -1) {
                            fos.write(b, 0, len);
                            progress += len;
                            final int pro = Math.min(Math.round(100 * progress / (size * 1f)), 99);
                            final long curFileSize = progress;
                            runOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (callback != null) {
                                        callback.onProgress(pro);
                                        callback.onDownloadFileSize(curFileSize);
                                    }
                                }
                            });


                        }
                        fos.close();
                        runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                if (callback != null) {
                                    callback.onProgress(100);
                                }
                            }
                        });
                    }
                } catch (final Exception e) {
                    File file = new File(filePath);
                    file.delete();
                    e.printStackTrace();
                    runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {
                                callback.onError(new HttpException(e));
                            }
                        }
                    });

                }
                return super.doInBackground(arg0);
            }
        }).execute("");


    }

    /**
     * 基类请求方法
     *
     * @param request  请求
     * @param callback 回调方法
     */
    public void requestMethod(Request request, final IHttpCallback<String> callback) {

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.connectTimeout(5 * 60, TimeUnit.SECONDS);
        builder.readTimeout(5 * 60, TimeUnit.SECONDS);
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
//        新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtil.d("message = [" + message + "]");
            }
        });
        loggingInterceptor.setLevel(level);
        builder.addInterceptor(loggingInterceptor);
        OkHttpClient okHttpClient = builder.build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                LogUtil.d(e.toString());

                String log="requestMethod------------>onFailure:"+e.toString();
                LogUtil.dAndSave(log,SERVER_PATH);

                runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        // 拒绝访问服务器等错误
                        if (callback != null) {
                            callback.onFaild(new HttpException(e));
                        }
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        String log="requestMethod------------>onResponse:"+response.isSuccessful();
                        LogUtil.dAndSave(log,SERVER_PATH);
                        if (!response.isSuccessful()) {
                            if (callback != null) {
                                callback.onFaild(new HttpException(response.message()));
                            }
                            return;
                        }
                        if (callback != null) {
                            try {
                                callback.onSuccess((response.body().string()));
                            } catch (IOException e) {
                                e.printStackTrace();
                                callback.onFaild(new HttpException(e));
                            }
                        }

                    }
                });
            }

        });
    }
}
