package com.lu.library

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.lu.library.base.AbsInit
import com.tencent.mmkv.MMKV

class CommonInit: AbsInit {
    override fun init(app: Application) {
        LibContext.getInstance().init(app)
        MMKV.initialize(app)
        Utils.init(app)
    }
}