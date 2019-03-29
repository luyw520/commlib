package com.lu.library.base;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.baidu.mobstat.StatService;
import com.lu.library.R;
import com.lu.library.log.LogUtil;
import com.lu.library.util.EventBusHelper;
import com.lu.library.util.NetUtil;
import com.lu.library.util.ObjectUtil;
import com.lu.library.util.PermissionUtil;
import com.lu.library.util.ScreenUtil;
import com.lu.library.util.ToastUtil;
import com.lu.library.widget.CommonTitleBarHelper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * Created by lyw.
 *
 * 子类如果有多个泛型，则第一个泛型必须是BasePresenter的子类
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements PermissionUtil.RequsetResult,IBaseView {

    protected P mPersenter;
    protected final static int WRITE_EXTERNAL_STORAGE_REQUEST_CODE=100;
    protected final static int ACCESS_FINE_LOCATION_REQUEST_CODE=100;
    /**
     * 通用头部辅助类
     */
    protected CommonTitleBarHelper commonTitleBarHelper;
    private PermissionUtil permissionUtil;
    protected Activity activity;
    protected Activity mActivity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResID());
        activity=this;
        mActivity=this;
        ButterKnife.bind(this);
        EventBusHelper.register(this);
        initPresenter();
        permissionUtil=new PermissionUtil();
        permissionUtil.setRequsetResult(this);
        commonTitleBarHelper=new CommonTitleBarHelper();
        commonTitleBarHelper.init(this);
        setNavigationBar();
        initViews();
        initData();


    }
    //字体不随着系统的字体而变化
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();

            res.updateConfiguration(newConfig, res.getDisplayMetrics());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                createConfigurationContext(newConfig);
            } else {
                res.updateConfiguration(newConfig, res.getDisplayMetrics());
            }
        }
        return res;
    }
        /**
         * 获取编辑框的内容（去除两边空格）
         *
         * @param editText
         * @return
         */
    public String getEditTextStr(EditText editText) {
        return editText.getText().toString().trim();
    }
    @Override
    public void setTitle(int titleId) {
       commonTitleBarHelper.setTitle(titleId);
    }
    public void initLayout(int style){
        commonTitleBarHelper.initLayout(style);
    }
    public void setRightOnClick(View.OnClickListener rightOnClick){
        commonTitleBarHelper.setRightOnClick(rightOnClick);
    }
    @Override
    public void setTitle(CharSequence titleId) {
       commonTitleBarHelper.setTitle((String) titleId);
    }

    public void initViews(){

    }
    /**
     * 设置沉浸式状态栏
     * 这个方法只适用于使用公共标题的情况下，个人情况下需要重新该方法
     */
    protected void setNavigationBar() {
        ScreenUtil.setNavigationBar(this);
    }
    @Subscribe(threadMode=ThreadMode.MAIN)
    public final void handleMessageInner(BaseMessage message){
        handleMessage(message);
    }

    /**
     * 子类如需处理EventBus发送的消息，重写此方法
     * @param message 消息内容
     */
    protected void handleMessage(BaseMessage message) {
    }

    /**
     * 初始化P类型的对象，并且绑定该IBaseView子类
     */
    private void initPresenter() {
        mPersenter=autoCreatePresenter();
        if (mPersenter!=null){
            try {
                mPersenter.attachView((IBaseView) this);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }
    /**
     * 检测权限，如果返回true,有权限 false 无权限
     * @param permissions 权限
     * @return 是否有权限
     */
    public boolean checkSelfPermission(String... permissions){
        return PermissionUtil.checkSelfPermission(permissions);
    }
    public void requestPermissions(int requestCode,String... permissions){
        PermissionUtil.requestPermissions(this,requestCode,permissions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionUtil.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
    public void requestPermissionsSuccess(int requestCode){

    };
    public void requestPermissionsFail(int requestCode){};
    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 布局资源文件
     * @return 布局资源文件
     */
    public abstract int getLayoutResID();
    /**
     * 生成P类型的一个实例
     * @return P类型的一个实例
     */
    public  P autoCreatePresenter() {
        return ObjectUtil.getParameterizedType(getClass());
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBusHelper.unregister(this);
        if (mPersenter!=null) {
            mPersenter.detachView();
        }
    }

    /**
     * 跳转到Activity
     * @param clazz Activity类
     */
    protected void startActivity(Class clazz) {
        startActivityForResult(clazz,-1,null);
    }

    /**
     * 跳转到Activity，携带参数
     * @param clazz Activity类
     * @param bundle 数据
     */
    protected void startActivity(Class clazz, Bundle bundle) {
        startActivityForResult(clazz,-1,bundle);
    }
    /**
     * 开启一个activity并且自己销毁
     *
     * @param clazz
     */
    protected void startActivityAndSelfFinish(Class clazz) {
        startActivity(clazz);
        finish();
    }
    /**
     * startActivityForResult方式跳转到Activity
     * @param clazz Activity类
     */
    protected void startActivityForResult(Class clazz, int requestCode) {
        startActivityForResult(clazz, requestCode,null);
    }
    /**
     * startActivityForResult方式跳转到Activity
     * @param clazz Activity类
     * @param bundle 数据
     */
    protected void startActivityForResult(Class clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle!=null){
            intent.putExtras(bundle);
        }
        if (requestCode>0){
            startActivityForResult(intent, requestCode);
        }else{
            startActivity(intent);
        }

    }
    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        if (startActivitySelfCheck(intent)) {
            // 查看源码得知 startActivity 最终也会调用 startActivityForResult
            super.startActivityForResult(intent, requestCode, options);
        }
    }

    private String mActivityJumpTag;
    private static long mActivityJumpTime;
    /**
     * 检查当前 Activity 是否重复跳转了，不需要检查则重写此方法并返回 true 即可
     *
     * @param intent          用于跳转的 Intent 对象
     * @return                检查通过返回true, 检查不通过返回false
     */
    protected boolean startActivitySelfCheck(Intent intent) {
        // 默认检查通过
        boolean result = true;
        // 标记对象
        String tag;
        if (intent.getComponent() != null) { // 显式跳转
            tag = intent.getComponent().getClassName();
        }else if (intent.getAction() != null) { // 隐式跳转
            tag = intent.getAction();
        }else {
            return result;
        }
        LogUtil.d("tag:"+tag);
        LogUtil.d("mActivityJumpTag:"+mActivityJumpTag);
        long dx=System.currentTimeMillis()-mActivityJumpTime;
        LogUtil.d("dx:"+dx);
        if (tag.equals(mActivityJumpTag) && dx<=500) {
            // 检查不通过
            result = false;
        }

        // 记录启动标记和时间
        mActivityJumpTag = tag;
        mActivityJumpTime = System.currentTimeMillis();
        return result;
    }

    protected void showToast(String msg) {
        ToastUtil.showToast(this, msg);
    }

    protected void showToast(int msg) {
//        ToastUtil.showToast(IdoApp.getAppContext(), msg);
        ToastUtil.showToast(this, getString(msg));
    }


    protected boolean isNetWorkAndToast() {
        if (!NetUtil.isConnected(this)) {
            showToast((R.string.no_network));
            return false;
        }
        return true;

    }
    /**
     * 百度统计埋点
     *
     * @param eventId 事件id
     * @param label   事件标签
     */
    protected void setBaiduStat(String eventId, String label) {
        StatService.onEvent(this, eventId, label);
    }
}
