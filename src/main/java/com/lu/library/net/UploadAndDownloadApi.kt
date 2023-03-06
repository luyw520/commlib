package com.lu.library.net

import android.webkit.WebSettings
import com.blankj.utilcode.util.Utils
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * 文件下载
 */
interface UploadAndDownloadApi {


    @Streaming
    @GET
    suspend fun download(
        @Url url: String, @Header("User-Agent")
        agent: String = WebSettings.getDefaultUserAgent(Utils.getApp())
    ): ResponseBody
}