package com.lu.library.http;

/**
 * @author:yangm
 * @package:com.id.app.comm.lib.http
 * @desciption:${TODO}
 * @date:2018/11/23 0023
 */
public interface IHttpDownloadCallback<T> extends IHttpCallback<T> {
    void onProgress(int progress);
}
