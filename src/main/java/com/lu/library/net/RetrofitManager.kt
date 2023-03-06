package com.lu.library.net

import com.blankj.utilcode.util.LogUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URLDecoder
import java.util.*

object RetrofitManager {
    private var retrofits = mutableMapOf<String, Retrofit>()
    var LOG_ENABLE = true
    private var defaultUrl = "http://www.baidu.com"
    val MAX_LOG_SIZE = 500

    private fun buildLogInterceptor():HttpLoggingInterceptor{
        return HttpLoggingInterceptor(
            logger = object:HttpLoggingInterceptor.Logger{
                override fun log(message: String) {
                    if (LOG_ENABLE) {
                        LogUtils.d( URLDecoder.decode(message))
                    }
                }
            }
        ).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }


    fun getInstance(baseUrl: String? = null): Retrofit {
        return retrofits[baseUrl] ?: kotlin.run {
            val httpClient = OkHttpClient.Builder().addInterceptor(buildLogInterceptor()).build()
            val builder = Retrofit.Builder()
                .baseUrl(baseUrl ?: defaultUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(httpClient)
            val retrofit = builder.build()
            retrofits[baseUrl ?: defaultUrl] = retrofit
            retrofit
        }

    }

    inline fun <reified T> create(baseUrl: String): T {
        return getInstance(baseUrl).create(T::class.java)
    }

    fun init(baseUrl: String) {

    }




}