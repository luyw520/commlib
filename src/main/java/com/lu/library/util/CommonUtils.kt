package com.lu.library.util

import android.app.Application
import com.tencent.mmkv.MMKV

/**
 */
object CommonUtils {
    private var mApplication: Application? = null
    fun initApp(app: Application) {
        mApplication = app
        MMKV.initialize(app)
    }

    fun getApp(): Application {
       return mApplication!!
    }
}