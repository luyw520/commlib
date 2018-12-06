package com.lu.library.base;

import com.lu.library.log.LogUtil;
import com.lu.library.util.ReflectUtil;
import com.lu.library.util.ThreadUtil;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2018/3/29 0029.
 * V 表示绑定的View 类型,必须实现IBaseView接口
 */

public abstract class BasePresenter<V extends IBaseView> {


    protected WeakReference<V> mWeak;


    /**
     *绑定的View接口
     * @param v
     */
    public void attachView(V v){
        mWeak=new WeakReference<>(v);
    }

    /**
     * 当前View是否存在，或者没被销毁
     * @return
     */
    protected boolean isAttachView(){
        return mWeak!=null&&mWeak.get()!=null;
    }

    /**
     * 解绑当前View
     * @return
     */
    public void detachView(){
        if (mWeak!=null){
            mWeak.clear();
            mWeak=null;
        }
    }
    /**
     * mWeak.get()对象调用方法
     * @param methodName 方法名
     * @param paramsClass 方法类型
     * @param args 方法参数
     * @return
     */
    public Object callBack(String methodName,Class<?>[] paramsClass,Object... args){
        if (mWeak==null){
            LogUtil.d("mWeak is null");
            return null;
        }
        if (mWeak.get()==null){
            LogUtil.d("mWeak.get() is null");
            return null;
        }
        Object o=mWeak.get();
        return ReflectUtil.invokeMethod(o,methodName,paramsClass,args);
    }

    /**
     * mWeak.get()对象调用方法
     * @param methodName 方法名
     * @param args 方法参数
     * @return
     */
    public Object callBack(final String methodName,final Object... args){
        if (mWeak==null){
            LogUtil.d("mWeak is null");
            return null;
        }
        if (mWeak.get()==null){
            LogUtil.d("mWeak.get() is null");
            return null;
        }
        final Object o=mWeak.get();
        ThreadUtil.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                ReflectUtil.invokeMethod(o,methodName,args);
            }
        });
//        return ReflectUtil.invokeMethod(o,methodName,args);
        return null;
    }
}
