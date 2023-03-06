package com.lu.library.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseBindingActivity<VB: ViewDataBinding>:BaseActivity() {
    protected  lateinit var  binding :VB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,getLayoutId())
//        setContentView(binding.root)
        binding.lifecycleOwner = this
        initData()

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
    override fun initData() {
    }
    abstract fun getLayoutId():Int
}
