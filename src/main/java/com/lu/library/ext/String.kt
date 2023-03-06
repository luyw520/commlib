package com.lu.commons.ext

import com.alibaba.android.arouter.launcher.ARouter

fun String.navigation(){
    ARouter.getInstance().build(this).navigation()
}