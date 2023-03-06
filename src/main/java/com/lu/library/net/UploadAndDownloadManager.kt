package com.lu.library.net

import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream

object UploadAndDownloadManager {

    suspend fun downloader(path: File, url:String, callback: ((p:Int, current:Long, total:Long)->Unit)?=null)= withContext(
        Dispatchers.IO){
        val baseUrl = url.substring(0,url.lastIndexOf("/")+1)
        val name = url.substring(url.lastIndexOf("/")+1)
//        DebugLog.d(baseUrl)
//        DebugLog.d(name)
        val responseBody = RetrofitManager.getInstance(baseUrl).create(UploadAndDownloadApi::class.java).download(name)
        val total = responseBody.contentLength()
        var current = 0L
        responseBody.byteStream().buffered(1024).use {inputStream->
            BufferedOutputStream(FileOutputStream(path)).use { outputStream->
                var len = 0
                var p = 0
                val bytes = ByteArray(1024)
                while ((len)!=-1){
                    len = inputStream.read(bytes)
                    outputStream.write(bytes)
                    current+=len
                    val temp = (total/current).toInt()
                    if(p!=temp) {
                        p = temp
                        callback?.invoke(p,current,total)
//                        DebugLog.d("current:$current,total:$total,p:${total / current}")
                        if(current == total){
                            LogUtils.d("下载完成..${path.absolutePath}")
                        }
                    }
                }
                outputStream.flush()
            }

        }

    }
}