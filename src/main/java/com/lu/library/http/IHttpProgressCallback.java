package com.lu.library.http;

/**
 * Created by lyw.
 *
 * @author: lyw
 * @package: com.id.app.comm.lib.http
 * @description: ${TODO}{ 带进度条的回调}
 * @date: 2018/9/19 0019
 */
public interface IHttpProgressCallback {
    void onProgress(int progress);
    void onDownloadFileSize(long size);
    void onError(HttpException e);
}
