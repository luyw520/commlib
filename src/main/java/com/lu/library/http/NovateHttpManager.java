package com.lu.library.http;

import android.content.Context;

import com.lu.library.log.LogUtil;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;
import com.tamic.novate.callback.RxStringCallback;
import com.tamic.novate.download.DownLoadCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.lu.library.Constant.SERVER_PATH;


/**
 * Created by lyw.
 *
 * @author: lyw
 * @package: com.id.app.comm.lib.http
 * @description: ${TODO}{ 基于Novate库的网络处理类}
 * @date: 2018/8/7 0007
 */
public class NovateHttpManager extends AbstractHttpManager {

    private Novate novate;

    public NovateHttpManager(Context context) {
        super(context);
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtil.d("message = [" + message + "]");
            }
        });
        loggingInterceptor.setLevel(level);
        Novate.Builder builder = new Novate.Builder(context).baseUrl(BASE_URL).addInterceptor(loggingInterceptor);
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        novate = builder.build();
    }

    public NovateHttpManager(Context context, String baseUrl) {
        super(context);
        Novate.Builder builder = new Novate.Builder(context).baseUrl(baseUrl);
        novate = builder.build();
    }


//    @Override
//    public <T> void getRequest(String subUrl, Map<String, Object> parameters, final IHttpCallback<T> callback) {
//        if (parameters == null) {
//            parameters = new HashMap<>();
//        }
//        novate.rxGet(subUrl, parameters, new RxStringCallbackWrapper<T>(callback){});
//
//    }

    @Override
    public void getRequestString(String subUrl, Map<String, Object> parameters, IHttpCallback<String> callback, boolean isGet) {
        if (parameters == null) {
            parameters = new HashMap<>();
        }
//        LogUtil.d(subUrl);
//        LogUtil.d(parameters);
        String log = "getRequestString------------>subUrl:" + subUrl + ",isGet:" + isGet + ",parameter " + (parameters == null ? " null " : parameters.toString());
        LogUtil.dAndSave(log, SERVER_PATH);
        if (isGet) {
            novate.rxGet(subUrl, parameters, new RxStringCallbackWrapper(callback) {
            });
        } else {
            novate.rxPost(subUrl, parameters, new RxStringCallbackWrapper(callback) {
            });
        }
    }

    @Override
    public void getRequestJsonString(String subUrl, Map<String, Object> parameters, IHttpCallback<String> callback, boolean isGet) {
        JSONObject jsonObject = new JSONObject();
//        LogUtil.d(subUrl);
//        LogUtil.d(parameters);

        String log = "getRequestJsonString------------>subUrl:" + subUrl + ",isGet:" + isGet + ",parameter " + (parameters == null ? " null " : parameters.toString());
        LogUtil.dAndSave(log, SERVER_PATH);

        if (parameters != null) {
            try {
                for (String key : parameters.keySet()) {
                    jsonObject.put(key, parameters.get(key));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        novate.rxJson(subUrl, jsonObject.toString(), new RxStringCallbackWrapper(callback) {
        });
    }

    @Override
    public void postRequestJsonString(String subUrl, String json, IHttpCallback<String> callback) {
        novate.rxJson(subUrl, json, new RxStringCallbackWrapper(callback) {
        });
    }


    @Override
    public void down(String url, String dir, String fileName, final IHttpCallback<String> progressCallback) {
        File file = new File(dir);
//        LogUtil.d("dir:" + );
        file.mkdirs();
        if (!fileName.contains("/")) {
            fileName = "/" + fileName;
        }
        String log = "down------------>url:" + url + ",dir:" + dir + ",fileName " + fileName;
        LogUtil.dAndSave(log, SERVER_PATH);


        novate.download(null, url, dir, fileName, new DownLoadCallBack() {
            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                String log = "down------------onError>" + e.toString();
                LogUtil.dAndSave(log, SERVER_PATH);
                if (progressCallback != null) {
                    progressCallback.onFaild(new HttpException(e));
                }
            }

            @Override
            public void onSucess(String key, String path, String name, long fileSize) {
//                LogUtil.d("key:" + key);
//                LogUtil.d("path:" + path);
//                LogUtil.d("name:" + name);
//                LogUtil.d("fileSize:" + fileSize);

                String log = "down------------onSucess> key:" + key + ",path:" + path + ",name:" + name + ",filesize:" + fileSize;
                LogUtil.dAndSave(log, SERVER_PATH);
                if (progressCallback != null) {
                    progressCallback.onSuccess(path + File.separator + name);
                }
            }

            @Override
            public void onProgress(String key, int progress, long fileSizeDownloaded, long totalSize) {
                super.onProgress(key, progress, fileSizeDownloaded, totalSize);
                if (progressCallback != null) {
                    if (progressCallback instanceof IHttpDownloadCallback) {
                        ((IHttpDownloadCallback) progressCallback).onProgress(progress);
                    }
                }
            }
        });
    }

    @Override
    public void upload(String subUrl, String filePath, String name, Map<String, Object> parameters, IHttpCallback<String> callback) {
        File file = new File(filePath);
        if (!file.exists()) {
            callback.onFaild(new HttpException("文件不存在"));
            return;
        }
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        if (parameters != null) {
            for (String key : parameters.keySet()) {
                builder.addFormDataPart(key, parameters.get(key).toString());
            }
        }
        String log = "upload------------>subUrl:" + subUrl + ",filePath:" + filePath + ",name:" + name + ",parameter " + (parameters == null ? " null " : parameters.toString());
        LogUtil.dAndSave(log, SERVER_PATH);

//        builder.addFormDataPart("userId", userId);
        builder.addFormDataPart(name, file.getName(), RequestBody.create(MediaType.parse("image/png; charset=utf-8"), file));
        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url(BASE_URL + subUrl)
                .post(body)
                .build();
        requestMethod(request, callback);
    }

    @Override
    public void upload(String subUrl, Map<String, String> filePathMap, Map<String, Object> parameters, IHttpCallback<String> callback) {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        if (parameters != null) {
            for (String key : parameters.keySet()) {
                builder.addFormDataPart(key, parameters.get(key).toString());
            }
        }
        if (filePathMap != null) {
            for (String key : filePathMap.keySet()) {
                String filePath = filePathMap.get(key).toString();
                File file = new File(filePath);
                if (!file.exists()) {
                    callback.onFaild(new HttpException("文件不存在"));
                    continue;
                }
                builder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse("image/png; charset=utf-8"), file));
            }
        }
        String log = "upload------------>subUrl:" + subUrl + ",filePathMap:" + (filePathMap == null ? " filePathMap is null " : filePathMap.toString()) + ",parameter " + (parameters == null ? " null " : parameters.toString());
        LogUtil.dAndSave(log, SERVER_PATH);

        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url(BASE_URL + subUrl)
                .post(body)
                .build();
        requestMethod(request, callback);
    }


    static class RxStringCallbackWrapper extends RxStringCallback {
        IHttpCallback<String> callback;

        public RxStringCallbackWrapper(IHttpCallback<String> callback) {
            this.callback = callback;
        }

        @Override
        public void onNext(Object tag, String response) {
            String log = "onNext------------>" + response;
            LogUtil.dAndSave(log, SERVER_PATH);
            if (callback != null) {
                callback.onSuccess(response);
            }
        }

        @Override
        public void onError(Object tag, Throwable e) {
            e.printStackTrace();
            String log = "onError------------>" + e.toString();
            LogUtil.dAndSave(log, SERVER_PATH);
            if (callback != null) {
                callback.onFaild(new HttpException(e.getCause()));
            }
        }

        @Override
        public void onCancel(Object tag, Throwable e) {
            e.printStackTrace();
            String log = "onCancel------------>" + e.toString();
            LogUtil.dAndSave(log, SERVER_PATH);
            if (callback != null) {
                callback.onFaild(new HttpException(e.getCause()));
            }
        }
    }

}
