package com.lu.library.http;


import com.lu.library.util.GsonUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class IHttpCallbackAdater<T> implements IHttpCallback<String>{
        IHttpCallback<T> callback;
        public IHttpCallbackAdater(IHttpCallback<T> callback){
            this.callback=callback;
        }
        @Override
        public void onSuccess(String t) {
            ParameterizedType type=(ParameterizedType) getClass().getGenericSuperclass();
            Type[] types=(type).getActualTypeArguments();
            Class<T> entityClass = (Class<T>) types[0];
            try {
                if (entityClass==String.class){
                    callback.onSuccess((T)t);
                }else{
                    callback.onSuccess(GsonUtil.fromJson(t,entityClass));
                }

            }  catch (Exception e) {
                callback.onFaild(new HttpException(e.getCause()));
                e.printStackTrace();
            }
        }

        @Override
        public void onFaild(HttpException e) {
            if (callback != null) {
                callback.onFaild(new HttpException(e.getCause()));
            }
        }
    }