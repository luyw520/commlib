package com.lu.library.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.lu.library.log.LogUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by lyw.
 *
 * @author: lyw
 * @package: com.ido.veryfitpro.util
 * @description: ${TODO}{ 类注释}
 * @date: 2018/8/16 0016
 */
public class HookClickListenerHelper {
    private final static int MAX_DELAY_TIME=500;

    /**
     * Activity里面使用
     * hook Activity里面所有的view的onClick方法
     * @param activity
     */
    public static void hookOnClickAuto(Activity activity){
      ViewGroup decorView= (ViewGroup) activity.getWindow().getDecorView();
      long startTime=System.currentTimeMillis();
      LogUtil.d("hookOnClickAuto start ");
      int childCount=decorView.getChildCount();
      for (int i=0;i<childCount;i++){
          View view=decorView.getChildAt(i);
          if (view instanceof ViewGroup){
              hookViewGroup((ViewGroup) view);
          }else{
              hookOnClick(view);
          }
      }
        LogUtil.d("hookOnClickAuto end,spend time : "+(System.currentTimeMillis()-startTime)+"ms");
    }

    /**
     * Fragment里面使用
     * hook ViewGroup里面所有的view的onClick方法,
     * @param viewGroup 根控件
     * @see #hookOnClick(View...)
     */
    public static void hookViewGroup(ViewGroup viewGroup){
        int childCount=viewGroup.getChildCount();
        for (int i=0;i<childCount;i++){
            View view=viewGroup.getChildAt(i);
            if (view instanceof ViewGroup){
                hookViewGroup((ViewGroup) view);
            }else{
                hookOnClick(view);
            }
        }
    }

    /**
     * 控制view的两次点击事件在500ms以上
     * @param views
     */
    public static void hookOnClick(View... views){
//        String fieldName="mListenerInfo";
        String clickFieldName="mOnClickListener";
//        String methodName="";
        for (int i=0;i<views.length;i++){
            View view=views[i];
            try {
                Method method= View.class.getDeclaredMethod("getListenerInfo",new Class[]{});
                method.setAccessible(true);

                //获取mListenerInfo对象
                Object mListenerInfo=method.invoke(view);
                if (mListenerInfo==null){
                    continue;
                }

                Field clickField=mListenerInfo.getClass().getDeclaredField(clickFieldName);
                clickField.setAccessible(true);

                //获取mListenerInfo里面的mOnClickListener对象
                View.OnClickListener clickListener= (View.OnClickListener) clickField.get(mListenerInfo);

                if (clickListener==null){
                    continue;
                }
                Object proxy=Proxy.newProxyInstance(view.getClass().getClassLoader(),new Class[]{View.OnClickListener.class} ,new ClickListenerInvocationHandler(clickListener));
                //替换
                clickField.set(mListenerInfo,proxy);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    static class ClickListenerInvocationHandler implements InvocationHandler{

        Object target;
        public ClickListenerInvocationHandler(View.OnClickListener clickListener){
            this.target=clickListener;
        }
        private long lastTime=0;
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


            long currentTime=System.currentTimeMillis();
            long dxTime=currentTime-lastTime;
            LogUtil.d("dxTime:"+dxTime);
            if (currentTime-lastTime<MAX_DELAY_TIME){
                LogUtil.d("两次点击小于500ms:"+dxTime);
                lastTime=currentTime;
                return null;
            }
            lastTime=currentTime;
            method.invoke(target,args);
            return null;
        }
    }
}
