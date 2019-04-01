package com.lu.library.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lu.library.log.DebugLog;
import com.lu.library.log.LogUtil;
import com.lu.library.util.EventBusHelper;
import com.lu.library.util.HookClickListenerHelper;
import com.lu.library.util.ObjectUtil;
import com.lu.library.util.PermissionUtil;
import com.lu.library.util.ToastUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * Created by lyw.
 *
 * @author: lyw
 * @package: com.ido.veryfitpro.base
 * @description: ${TODO}{ 所有Fragment的超类}
 * @date: 2018/7/16 0016
 *
 *子类如果有多个泛型，则第一个泛型必须是BasePresenter的子类
 **/
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements PermissionUtil.RequsetResult,IBaseView{
    protected FragmentActivity mActivity;
    protected View mRootView;
    protected P mPersenter;
    public BaseFragment(){
        super();
    }
    protected boolean isHidden=false;
    private PermissionUtil permissionUtil;
    public String tag;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity= (FragmentActivity) context;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isHidden=hidden;
        DebugLog.d("onVisiable:"+toString());
        if (!hidden){
            onVisiable();
        }else{
            oninVisiable();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.d("onStop...."+toString());
//        isHidden=true;
        if (isHidden){
            oninVisiable();
        }
    }
    public boolean checkSelfPermission(String... permissions){
        return PermissionUtil.checkSelfPermission(permissions);
    }
    public void requestPermissions(int requestCode,String... permissions){
        PermissionUtil.requestPermissions(this,requestCode,permissions);
    }
    /**
     * 不可见时回调
     */
    public void oninVisiable(){
        DebugLog.d("isHidden:"+isHidden+",oninVisiable:"+toString());
    }
    @Override
    public void onResume() {
        super.onResume();
        DebugLog.d("isHidden:"+isHidden+",onResume:"+toString());
        if (!isHidden){
            onVisiable();
        }

    }
    public void requestPermissionsSuccess(int requestCode){

    };
    public void requestPermissionsFail(int requestCode){};

    /**
     * 当Fragment可见的时候回调此方法
     * 在ViewPager里面可能会失效,ViewPager嵌套时看如下方法
     * @see #setUserVisibleHint(boolean)
     */
    protected void onVisiable(){};
    /**
     * 初始化P类型的对象
     */
    private void initPresenter() {
        mPersenter=autoCreatePresenter();
        if (mPersenter!=null){
            DebugLog.d("attachView "+toString());
            try {
                mPersenter.attachView((IBaseView) this);
            }catch (Exception e){

            }

        }

    }
    /**
     * 生成P类型的一个实例
     * @return P类型的一个实例
     */
    public  P autoCreatePresenter() {
        return ObjectUtil.getParameterizedType(getClass());
    };
    protected abstract int getLayoutResID();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView =  inflater.inflate(getLayoutResID(), container, false);
        ButterKnife.bind(this,mRootView);
        EventBusHelper.register(this);
        initPresenter();
        permissionUtil=new PermissionUtil();
        permissionUtil.setRequsetResult(this);
        tag=getClass().getSimpleName();
        LogUtil.d("tag:"+tag+",onCreateView:"+toString());
        initView();
        initData();
        HookClickListenerHelper.hookViewGroup((ViewGroup) mRootView);
        mRootView.setClickable(true);
        return mRootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionUtil.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Subscribe(threadMode= ThreadMode.MAIN)
    public  void handleMessageInner(BaseMessage message){
        handleMessage(message);
    }

    /**
     * 子类如需处理EventBus发送的消息，重写此方法
     * @param message 消息内容
     */
    protected void handleMessage(BaseMessage message) {
//        DebugLog.d(message.toString()+","+toString());
    }
    /**
     * 初始化数据工作
     */
    public abstract void initData();

    /**
     * 如果不能用ButterKnife绑定的控件，可以在这里做初始化工作
     */
    protected void initView(){};

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBusHelper.unregister(this);
        if (mPersenter!=null){
            mPersenter.detachView();
        }
    }
    protected void startService(Class service){
        mActivity.startService(new Intent(mActivity,service));
    }
    /**
     * 跳转到Activity
     * @param clazz Activity类
     */
    protected void startActivity(Class clazz) {
        Intent intent = new Intent(mActivity, clazz);
        mActivity.startActivity(intent);
    }
    /**
     * 跳转到Activity，携带参数
     * @param clazz Activity类
     * @param bundle 数据
     */
    protected void startActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(mActivity, clazz);
        intent.putExtras(bundle);
        mActivity.startActivity(intent);
    }
    /**
     * startActivityForResult方式跳转到Activity
     * @param clazz Activity类
     */
    protected void startActivityForResult(Class clazz, int requestCode) {
        Intent intent = new Intent(mActivity, clazz);
        mActivity.startActivityForResult(intent, requestCode);
    }
    /**
     * startActivityForResult方式跳转到Activity
     * @param clazz Activity类
     * @param bundle 数据
     */
    protected void startActivityForResult(Class clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(mActivity, clazz);
        intent.putExtras(bundle);
        mActivity.startActivityForResult(intent, requestCode);
    }

    protected void showToast(String msg) {
        ToastUtil.showToast(getContext(), msg);
    }

    protected void showToast(int msg) {
//        ToastUtil.showToast(IdoApp.getAppContext(), msg);
        ToastUtil.showToast(getContext(), getString(msg));
    }

}