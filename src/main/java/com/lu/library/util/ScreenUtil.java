package com.lu.library.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import com.id.app.comm.lib.R;

import java.lang.reflect.Method;

public class ScreenUtil {


    private static int screenW;
    private static int screenH;
    private static float screenDensity;

    public static void initScreen(Activity mActivity) {
        DisplayMetrics metric = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenW = metric.widthPixels;
        screenH = metric.heightPixels;
        screenDensity = metric.density;
    }

    public static int getScreenW() {
        return screenW;
    }

    public static int getScreenH() {
        return screenH;
    }

    public static float getScreenDensity() {
        return screenDensity;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(float dpValue) {
        return (int) (dpValue * getScreenDensity() + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(float pxValue) {
        return (int) (pxValue / getScreenDensity() + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dip(像素) 的单位 转成为 px
     */
    public static int dip2px(float pxValue) {
        return (int) (pxValue * getScreenDensity() + 0.5f);
    }

    public static int dpToPx(Resources res, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                res.getDisplayMetrics());
    }

    public static int spToPx(Resources res, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp,
                res.getDisplayMetrics());
    }

    public static String getScreenInfo() {
        return "getScreenH() * getScreenW()=" + getScreenH() + " * " + getScreenW();
    }

    public static int getTextHeight(Paint paint, String text){
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.height();
    }

    public static int getTextWidth(Paint paint,String text){
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.width();
    }
    /**
     * 设置通用的沉浸式状态栏【带CommonTitle标题栏的Activity】
     *
     * @param activity
     */
    public static void setNavigationBar(Activity activity) {
        ScreenUtil.setImmersiveStatusBar(activity);
        ViewGroup parentView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);//拿到第一层的content
        if (parentView.getChildAt(0) instanceof ViewGroup) {

            ViewGroup childView = (ViewGroup) parentView.getChildAt(0);//第二层布局layout
            childView.setFitsSystemWindows(true);
            childView.setClipToPadding(true);
            ViewGroup.LayoutParams layoutParams = childView.getLayoutParams();
            layoutParams.height=(int) (ScreenUtil.getStatusBarHeight(activity.getResources()) + activity.getResources().getDimension(R.dimen.common_tittle_height));
//            if (parentView instanceof LinearLayout) {
//                layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        (int) (ScreenUtil.getStatusBarHeight(activity.getResources()) + activity.getResources().getDimension(R.dimen.common_tittle_height)));
//            } else if (parentView instanceof RelativeLayout) {
//                layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        (int) (ScreenUtil.getStatusBarHeight(activity.getResources()) + activity.getResources().getDimension(R.dimen.common_tittle_height)));
//            } else if (parentView instanceof FrameLayout) {
//
//            }
            childView.setLayoutParams(layoutParams);
        }
    }
    /**
     * 设置通用的沉浸式状态栏【带CommonTitle标题栏的Activity】
     *
     * @param activity
     */
    public static void setNavigationBar(Activity activity,int viewId) {
        ScreenUtil.setImmersiveStatusBar(activity);
        ViewGroup parentView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);//拿到第一层的content

        ViewGroup childView = parentView.findViewById(viewId);//第二层布局layout
//        if (parentView.getChildAt(0) instanceof ViewGroup) {
            childView.setFitsSystemWindows(true);
            childView.setClipToPadding(true);
            ViewGroup.LayoutParams layoutParams = childView.getLayoutParams();
            layoutParams.height=(int) (ScreenUtil.getStatusBarHeight(activity.getResources()) + activity.getResources().getDimension(R.dimen.common_tittle_height));
            childView.setLayoutParams(layoutParams);
//        }
    }

    /**
     * 设置通用的沉浸式状态栏【带CommonTitle标题栏的Activity】
     *
     * @param activity
     */
//    public static void setNavigationBar2(Activity activity) {
//        ScreenUtil.setImmersiveStatusBar(activity);
//        ViewGroup parentView = (ViewGroup) ((ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0)).getChildAt(0);//拿到第一层的content
//        if (parentView.getChildAt(0) instanceof ViewGroup) {
//            ViewGroup childView = (ViewGroup) parentView.getChildAt(0);//第二层布局layout
//            childView.setFitsSystemWindows(true);
//            childView.setClipToPadding(true);
//            DebugLog.d("viewGroup=LinearLayout is " + (childView instanceof LinearLayout) + ",viewGroup=LinearLayout is " + (childView instanceof FrameLayout));
//            ViewGroup.LayoutParams layoutParams = null;
//            if (parentView instanceof LinearLayout) {
//                layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        (int) (ScreenUtil.getStatusBarHeight(activity.getResources()) + activity.getResources().getDimension(R.dimen.common_tittle_height)));
//            } else if (parentView instanceof RelativeLayout) {
//                layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        (int) (ScreenUtil.getStatusBarHeight(activity.getResources()) + activity.getResources().getDimension(R.dimen.common_tittle_height)));
//            } else if (parentView instanceof FrameLayout) {
//
//            }
//            childView.setLayoutParams(layoutParams);
//        }
//    }
    /**
     * 截图全屏
     */
    public static Bitmap getTotleScreenShot(final ViewGroup viewContainer,View... views) {
        int width = viewContainer.getWidth();
        int h = viewContainer.getChildAt(0).getHeight();
//        for (int i = 0; i < views.length; i++) {
////                    if (viewContainer.getChildAt(i).getVisibility()==View.VISIBLE)
//            h += views[i].getHeight();
//        }
        final Bitmap screenBitmap = Bitmap.createBitmap(width, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(screenBitmap);
        viewContainer.getChildAt(0).draw(canvas);
//        for (View view : views) {
////                    if (view.getVisibility()==View.VISIBLE
////
//// ){
//            view.setVisibility(View.VISIBLE);
//            view.setDrawingCacheEnabled(true);
//            canvas.drawBitmap(view.getDrawingCache(), view.getLeft(), view.getTop(), null);
////                    }
//
//        }
//        for (int i=0,size=viewContainer.getChildCount();i<size;i++) {
//
//            view.setVisibility(View.VISIBLE);
//            view.setDrawingCacheEnabled(true);
//            canvas.drawBitmap(view.getDrawingCache(), view.getLeft(), view.getTop(), null);
//
//
//        }
        return screenBitmap;
    }
    /**
     * 监听根布局
     *
     * @param view
     * @param listener
     */
    public static void rootViewListener(View view, ViewTreeObserver.OnGlobalLayoutListener listener) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(listener);
    }

    /**
     * 设置登录页面的沉浸式状态栏
     *
     * @param activity
     */
//    public static void setLoginNavigationBar(Activity activity) {
//        ScreenUtil.setImmersiveStatusBar(activity);
//        ViewGroup parentView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
//        if (parentView.getChildAt(0) instanceof ViewGroup) {
//            ViewGroup childView = (ViewGroup) parentView.getChildAt(0);
//            childView.setFitsSystemWindows(true);
//            childView.setClipToPadding(true);
//            DebugLog.d("viewGroup=LinearLayout is " + (childView instanceof LinearLayout) + ",viewGroup=LinearLayout is " + (childView instanceof FrameLayout));
//            ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    (int) (ScreenUtil.getStatusBarHeight(activity.getResources()) + activity.getResources().getDimension(R.dimen.common_tittle_height)));
//            childView.setLayoutParams(layoutParams);
//        }
//    }

    /**
     * 获取屏幕的宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获取状态栏的高度
     *
     * @param res
     * @return
     */
    public static int getStatusBarHeight(Resources res) {
        int result = 0;
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 设置沉浸式状态栏
     */
    public static void setImmersiveStatusBar(Activity activity) {
        // 透明状态栏
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    /**
     * 获取底部虚拟按键的高度
     *
     * @param activity
     * @return
     */
    public static int getNavigationBarHeight(Activity activity) {
        if (checkDeviceHasNavigationBar(activity)) {
            Resources resources = activity.getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            int height = resources.getDimensionPixelSize(resourceId);
            return height;
        }
        return 0;
    }

    /**
     * 设置状态栏的颜色
     *
     * @param activity
     * @param on
     * @param res
     */
    /*public static void setStatusBarColor(Activity activity, boolean on, int res) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(activity, on);
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(res);//通知栏所需颜色
        }
    }*/

    /**
     * 设置状态栏的颜色
     *
     * @param activity
     * @param on
     */
    /*public static void setStatusBarColor(Activity activity, boolean on, Drawable res) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(activity, on);
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintDrawable(res);//通知栏所需颜色
        }
    }*/
    @TargetApi(19)
    public static void setTranslucentStatus(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 是否有虚拟按键
     *
     * @param activity
     * @return
     */
    public static boolean isNavigationBar(Activity activity) {
        if (getNavigationBarHeight(activity) == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断手机底部是否有虚拟按键
     * 获取是否存在NavigationBar
     */
    public static boolean checkDeviceHasNavigationBar(Context activity) {
        boolean hasNavigationBar = false;
        Resources rs = activity.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }

    /**
     * 获取屏幕尺寸
     *
     * @param ctx
     * @return
     */
    public static double getScreenPhysicalSize(Activity ctx) {
        DisplayMetrics dm = new DisplayMetrics();
        ctx.getWindowManager().getDefaultDisplay().getMetrics(dm);
        double diagonalPixels = Math.sqrt(Math.pow(dm.widthPixels, 2) + Math.pow(dm.heightPixels, 2));
        return diagonalPixels / (160 * dm.density);
    }

    /**
     * 是否大于6英寸
     *
     * @param ctx
     * @return
     */
    public static boolean isOver6Inch(Activity ctx) {
        return getScreenPhysicalSize(ctx) >= 6.0 ? true : false;
    }

    // 判断手机品牌是否是乐视手机【该手机动画延迟1s】
    public static boolean isLeEcoPhone() {
        if (android.os.Build.BRAND.equals("LeEco")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否是2K屏幕，即分别率是 2560x1440 的屏幕
     * 如：三星 S7,乐视Pro等
     *
     * @param context
     * @return
     */
    public static boolean is2kScreen(Context context) {
        if (getScreenWidth(context) == 1440 && getScreenHeight(context) == 2560) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(float dpValue,Activity context) {
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return (int) (dpValue * metric.density + 0.5f);
    }
    // 获取指定Activity的截屏，保存到png文件
    public static Bitmap takeScreenShot(Activity activity) {

        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        System.out.println(statusBarHeight);

        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay()
                .getHeight();

        String brand = android.os.Build.BRAND;//获得手机品牌
        Bitmap b = null;
        //如果屏幕大于900*500则取900*500，否则按照屏幕大小截图
        if (width>500&&height<900){
            width=500;
            height=900;
        }
        if (brand.equals("Meizu")) {
//            b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - 5 * statusBarHeight
//            );
            b = Bitmap.createBitmap(b1, 0, 0, width, height);

        } else {

            // 去掉标题栏
            b = Bitmap.createBitmap(b1, 0, 0, width, height);
//            b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
//                    - statusBarHeight);
        }

        view.destroyDrawingCache();
        return b;
    }


    /**
     * 获取屏幕宽度和高度，单位为px
     * @param context
     * @return
     */
    public static Point getScreenMetrics(Context context){
        DisplayMetrics dm =context.getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        return new Point(w_screen, h_screen);

    }


}
