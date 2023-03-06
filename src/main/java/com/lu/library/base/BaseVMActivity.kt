package com.lu.library.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseVMActivity<VB: ViewDataBinding,VM:ViewModel> : BaseBindingActivity<VB>(){

    protected lateinit var viewModel: VM
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this,object:ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return modelClass.newInstance()
            }

        })[getViewModelClass()]
        super.onCreate(savedInstanceState)
        if (getVariableId()!=-1) {
            binding.setVariable(getVariableId(), viewModel)
        }
    }
    open fun getVariableId():Int = -1
    abstract fun getViewModelClass():Class<VM>

    @Deprecated("call newInstance")
   open fun getViewModelInstance():VM{
        val o =  object:ViewModel(){}
        return o as VM
    }
}