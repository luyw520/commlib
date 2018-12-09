package com.lu.library.widget;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lu.library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用说明：这个自定义的公共的标题栏类提供了丰富的标题栏的方法，建议尽量使用，用来替代重复复杂的标题栏
 * 用法：
 * 只需要在xml布局文件中添加 common_title 标题栏就行了：
 * <p/>
 * 案例如下：
 * <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
 * xmlns:app="http://schemas.android.com/apk/res-auto"
 * android:layout_width="match_parent"
 * android:layout_height="match_parent"
 * android:background="#F5F5F5"
 * android:orientation="vertical">
 * <p/>
 * // 标题栏
 * <include layout="@layout/common_title" />
 * <p/>
 * </LinearLayout>

 */

/**
 * @author: lyw
 * @package: com.ido.veryfitpro.util
 * @description: ${TODO}{自定义的公共的标题栏类}
 * @date: 2016/5/18 09:13
 */
public class CommonTitleBarHelper {

    public static final  int leftId = 0X0000; // 左边返回图片id
    public static final  int titleId = 0X0001;
    public static final  int rightId = 0X0002;
    public static final  int rightImgId = 0X0003; // 右边第一个image id
    public static final  int rightImgId2 = 0X0004; // 右边第一个image id
    public static final  int leftStrId = 0X0005; // 左边文字id
    public static final  int rightStrId = 0X0006; // 右边文字id
    public static final  int DEFAULT_LEFT_IMAGE = 0;
    public static final  int NO_LEFT_IMAGE = -1;
    public static final  int DEFAULT_MID_TITLECOLOR = 0;
    public static final  int DEFAULT_MID_TITLESIZE = 0;
    public static final  int DEFAULT_RIGHT_IMAGE = 0;
    public  static int defaultTextSize = 18;

    public static final  int LOACTION_LEFT=0;
    public static final  int LOACTION_MID=1;
    public static final  int LOACTION_RIGHT=2;
    public static final  int LOACTION_ALL=3;
    Activity activity;


    //中间子控件的集合
    List<View> mids = new ArrayList<View>();

    //右边子控件的集合
    List<View> rights = new ArrayList<View>();


    //左边子控件的集合
    List<View> lefts = new ArrayList<View>();

    /**
     * 默认的布局
     * 左边一个ImageView,中间一个TextView
     */
    public static final int STYLE_DEFAUL=0;


    /**
     * 带右边的ImageView的布局
     * 左边一个ImageView,中间一个TextView,右边一个ImageView
     */
    public static final int STYLE_RIGHT_IMG=1;
    /**
     * 带右边的TextView的布局
     * 左边一个ImageView,中间一个TextView,右边一个TextView
     */
    public static final int STYLE_RIGHT_TEXT=2;

    /**
     * 带右边的ImageView的布局
     * 左边一个ImageView,中间一个TextView,右边一个两个ImageView
     */
    public static final int STYLE_DOUBLE_RIGHT_IMG=3;


    /**
     * 左边默认图片
     */
    private static final int DEFAULT_LEFT_IMG_RES= R.drawable.cancel;

    /**
     * 右边默认图片
     */
    private static final int DEFAULT_RIGHT_IMG_RES=R.drawable.comfirm;

    /**
     * 标题栏默认背景
     */
    private static final int DEFAULT_BG_RES=R.drawable.scan_device_bg;

    public boolean isHasBar() {
        return hasBar;
    }

    public void setHasBar(boolean hasBar) {
        this.hasBar = hasBar;
    }

    /**
     * 判断该Activity是否设置了头部
     */
    private boolean hasBar=true;
    private ImageView rightImg;

    /**
     * 默认的布局是STYLE_DEFAUL
     * @param activity
     * @see #STYLE_DEFAUL
     * @see #STYLE_RIGHT_IMG
     * @see #STYLE_RIGHT_TEXT
     */
    public void init(Activity activity){
        this.activity=activity;
        if(activity.findViewById(R.id.layout_left)!=null){
            hasBar=true;
        }else{
            hasBar=false;
        }
        if (hasBar){
            initLayout();
        }

    }

    private CommonTitleBarHelper initLayout(){
        return initLayout(STYLE_DEFAUL);
    }

    /**
     * 设置标题字符串
     * @param res
     * @return
     */
    public CommonTitleBarHelper setTitle(int res){
        return setTitle(activity.getResources().getString(res));
    }
    /**
     * 设置标题字符串
     * @param text
     * @return
     */
    public CommonTitleBarHelper setTitle(String text){
        if (hasBar){
            ((TextView)activity.findViewById(titleId)).setText(text);
        }

        return this;
    }
    /**
     * 设置右边图片资源
     * @param res
     * @return
     */
    public CommonTitleBarHelper setRightImg(int res){
            ((ImageView) activity.findViewById(rightImgId)).setImageResource(res);
        return this;
    }
    /**
     * 设置右边图片资源
     * @param res
     * @return
     */
    public CommonTitleBarHelper setRightImg2(int res){
            ((ImageView) activity.findViewById(rightImgId2)).setImageResource(res);
        return this;
    }
    /**
     * 设置右边图片资源
     * @param res
     * @return
     */
    public CommonTitleBarHelper setRightText(int res){
        if (hasBar) {
            ((TextView) activity.findViewById(rightStrId)).setText(res);
        }
        return this;
    }
    /**
     * 设置右边图片资源
     * @param l 监听
     * @return
     */
    public CommonTitleBarHelper setRightOnClick(View.OnClickListener l){
        if (hasBar && currentStyle!=STYLE_DEFAUL) {
            getTitleLayoutRight(activity).setOnClickListener(l);
        }
        return this;
    }
    /**
     * 设置右边图片资源
     * @param l 监听
     * @return
     */
    public CommonTitleBarHelper setRight1OnClick(View.OnClickListener l){
        if (hasBar && currentStyle!=STYLE_DEFAUL) {
            getView(rightImgId).setOnClickListener(l);
        }
        return this;
    }
    private View getView(int res){
        return getTitleLayoutAll(activity).findViewById(res);
    }
    /**
     * 设置右边图片资源
     * @param l 监听
     * @return
     */
    public CommonTitleBarHelper setRight2OnClick(View.OnClickListener l){
        if (hasBar && currentStyle!=STYLE_DEFAUL) {
            getView(rightImgId2).setOnClickListener(l);
        }
        return this;
    }
    /**
     * 设置右边图片资源
     * @param l 监听
     * @return
     */
    public CommonTitleBarHelper setLeftOnClick(View.OnClickListener l){
        if (hasBar) {
            getTitleLayoutLeft(activity).setOnClickListener(l);
        }
        return this;
    }
    private int currentStyle=STYLE_DEFAUL;
    /**
     * @see #STYLE_DEFAUL
     * @see #STYLE_RIGHT_IMG
     * @see #STYLE_RIGHT_TEXT
     * @param style 标题栏布局类型
     * @return
     */
    public CommonTitleBarHelper initLayout(int style){
        rights.clear();
        lefts.clear();
        mids.clear();
        currentStyle=style;
        switch (style){
            case STYLE_DEFAUL:
                addLeftImageView();
                addMidTextView();
                break;
            case STYLE_RIGHT_IMG:
                addLeftImageView();
                addMidTextView();
                addRightImageView();
                break;
            case STYLE_RIGHT_TEXT:
                addLeftImageView();
                addMidTextView();
                addRightTextView();
                break;
            case STYLE_DOUBLE_RIGHT_IMG:
                addLeftImageView();
                addMidTextView();
                addRightImageView();
                addRightImageView2();
                break;
        }
        initBar();
        return this;
    }
    /**
     * 方法1：得到标题左布局
     *
     * @param activity
     * @return
     */
    public  LinearLayout getTitleLayoutLeft(Activity activity) {
        return findViewById(activity,R.id.layout_left);
    }
    private  LinearLayout findViewById(Activity activity,int res){
        LinearLayout linearLayout = (LinearLayout) activity.findViewById(res);
        linearLayout.setVisibility(View.VISIBLE);
        return linearLayout;
    }
    /**
     * 方法2：得到标题中间布局
     *
     * @param activity
     * @return
     */
    public  LinearLayout getTitleLayoutMid(Activity activity) {
        return   findViewById(activity,R.id.layout_mid);
    }

    /**
     * 方法3：得到标题右布局
     *
     * @param activity
     * @return
     */
    public  LinearLayout getTitleLayoutRight(Activity activity) {
        return findViewById(activity,R.id.layout_right);
    }

    /**
     * 方法4：得到标题整体布局
     *
     * @param activity
     * @return
     */
    public  LinearLayout getTitleLayoutAll(Activity activity) {
        return findViewById(activity,R.id.layout_parent);
    }

    /**
     * 方法8：设置标题的背景色
     *
     * @param activity
     * @param bgcolor
     * @param location:0:左边布局 1:中间布局  2:右边布局  3:整体布局
     * @see #LOACTION_LEFT
     * @see #LOACTION_MID
     * @see #LOACTION_RIGHT
     * @see #LOACTION_ALL
     */
    public  void setTitleLayoutBgcolor(Activity activity, int bgcolor, int location) {
        LinearLayout layout = null;
        switch (location) {
            case LOACTION_LEFT:
                layout = getTitleLayoutLeft(activity);
                break;
            case LOACTION_MID:
                layout = getTitleLayoutMid(activity);
                break;
            case LOACTION_RIGHT:
                layout = getTitleLayoutRight(activity);
                break;
            case LOACTION_ALL:
                layout = getTitleLayoutAll(activity);
                break;
            default:
                layout = getTitleLayoutAll(activity);
                break;
        }
        layout.setBackgroundColor(bgcolor);
    }

    /**
     * 方法9：设置整个标题栏的背景色
     *
     * @param activity
     * @param bgcolor
     */
    public  void setTitleLayoutAllBgcolor(Activity activity, int bgcolor) {
        setTitleLayoutBgcolor(activity, bgcolor, LOACTION_ALL);
    }

    /**
     * 方法10：标题栏最基础添加方法
     *
     * @param activity
     * @param lefts
     * @param mids
     * @param rights
     */
    private  void addTitle(Activity activity, List<View> lefts,
                                List<View> mids, List<View> rights) {

        LinearLayout leftLayout = getTitleLayoutLeft(activity);
        LinearLayout midLayout = getTitleLayoutMid(activity);
        LinearLayout rightLayout = getTitleLayoutRight(activity);
        leftLayout.removeAllViews();
        midLayout.removeAllViews();
        rightLayout.removeAllViews();
        if (lefts != null) {
            for (View view : lefts) {
                leftLayout.addView(view);
            }
        }
        if (mids != null) {
            for (View view : mids) {
                midLayout.addView(view);
            }
        }
        if (rights != null) {
            for (View view : rights) {
                rightLayout.addView(view);
            }
        }
        setBarBackground(DEFAULT_BG_RES);
    }
    /**
     * 方法10：标题栏最基础添加方法
     */
    private  void initBar() {
        LinearLayout leftLayout = getTitleLayoutLeft(activity);
        LinearLayout midLayout = getTitleLayoutMid(activity);
        LinearLayout rightLayout = getTitleLayoutRight(activity);
        leftLayout.removeAllViews();
        midLayout.removeAllViews();
        rightLayout.removeAllViews();
        if (lefts != null) {
            for (View view : lefts) {
                leftLayout.addView(view);
            }
        }
        if (mids != null) {
            for (View view : mids) {
                midLayout.addView(view);
            }
        }
        if (rights != null) {
            for (View view : rights) {
                rightLayout.addView(view);
            }
        }
        setBarBackground(DEFAULT_BG_RES);
    }
    private ImageButton createImageButton(int id,int imageResource){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(20, 0, 0, 0);
        params.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;

        // 返回按钮
        ImageButton imageButton = new ImageButton(activity);
        imageButton.setLayoutParams(params);
        imageButton.setId(id);
        imageButton.setPadding(0, 0, 0, 0);
        if (imageResource == 0) {
            imageButton.setImageResource(DEFAULT_LEFT_IMG_RES);
        } else if (imageResource == -1) {

        } else {
            imageButton.setImageResource(imageResource);
        }
        imageButton.setBackgroundColor(Color.TRANSPARENT);
        return imageButton;
    }
    public void startRightAnimation(){
        setRightImg(R.drawable.refresh);
        Animation rotateAnimation = AnimationUtils.loadAnimation(activity, R.anim.progress_drawable);
        rightImg = activity.findViewById(rightImgId);
        rightImg.startAnimation(rotateAnimation);
    }
    /**
     *设置右边图片点击前添加动画
     * @param rightListener 右边监听
     * @param isAnimaton  是否执行动画
     * @param isDelay 是否延迟执行监听
     */
    public  CommonTitleBarHelper setRightAnimation(final View.OnClickListener rightListener, final boolean isAnimaton,final boolean isDelay) {


        rightImg = activity.findViewById(rightImgId);
        rightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rightListener==null){
                    return;
                }

                    if (isAnimation(isAnimaton)){
                    //设置动画显示异常
//                        rightImg.setBackgroundResource(R.drawable.refresh);
                        setRightImg(R.drawable.refresh);
                        Animation rotateAnimation = AnimationUtils.loadAnimation(activity, R.anim.progress_drawable);
                        rightImg.startAnimation(rotateAnimation);
                    }
                    if (isDelay){
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    rightListener.onClick(view);

            }
        });
        return this;
    }
    public boolean rightAnimation=true;
    private boolean isAnimation(boolean isAnimation){
     return isAnimation&& rightAnimation;
    }



    private boolean isDelay=false;


    /**
     * 关闭右边的动画
     */
    public  void closeAnimation() {
        rightImg=activity.findViewById(rightImgId);
        if (rightImg != null) {
            rightImg.clearAnimation();
            rightImg.setImageResource(DEFAULT_RIGHT_IMG_RES);
        }
//        if (rotateAnimation != null) {
//            rotateAnimation.cancel();
//            rotateAnimation = null;
//        }
    }

    /**
     * ：设置整个标题栏的背景
     * @param backgroundRes 背景资源文件
     */
    public  CommonTitleBarHelper setBarBackground(int backgroundRes) {
        findViewById(activity,R.id.layout_parent).setBackgroundResource(backgroundRes);
        return this;
    }


    private  ImageView createImageView(Activity activity,int id,int leftImage,int defaltRes){
        return createImageView(activity,id,leftImage,defaltRes,0,0,0,0);
    }
    private  ImageView createImageView(Activity activity,int id,int leftImage,int defaltRes,int padleft,int padtop,int padright,int padbottom){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageView leftImg = new ImageView(activity);
        leftImg.setLayoutParams(params);
        leftImg.setId(id);
        leftImg.setPadding(padleft, padtop, padright, padbottom);
        if (leftImage == DEFAULT_LEFT_IMAGE) {
            leftImg.setImageResource(defaltRes);
        } else {
            leftImg.setImageResource(leftImage);
        }
        return leftImg;
    }

    private TextView createTextView(Activity activity,String title,int textSize ,int textColor,int id){
        return createTextView(title,textSize,textColor,id);
    }
    private TextView createTextView(String title,int textSize ,int textColor,int id){
        return createTextView(title,textSize,textColor,id,0);
    }
    private TextView createTextView(String title,int textSize ,int textColor,int id,int paddingRight){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        // 返回标题
        TextView textView = new TextView(activity);
        textView.setText(title);
        textView.setId(id);
        textView.setMaxEms(9);
        textView.setSingleLine(true);
        textView.setPadding(0, 0, paddingRight, 0);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setLayoutParams(params);
        if (textSize == 0) {
            textView.setTextSize(defaultTextSize);
        } else {
            textView.setTextSize(textSize);
        }
        if (textColor == 0) {
            textView.setTextColor(Color.WHITE);
        } else {
            textView.setTextColor(activity.getResources().getColor(textColor));
        }
        return textView;
    }
    static class ClickFinished implements View.OnClickListener{
        private Activity activity;
        public ClickFinished(Activity activity){
            this.activity=activity;
        }
        @Override
        public void onClick(View v) {
            activity.finish();
        }
    }

    /**
     * 左边默认的监听是调用finished方法
     */
    private void addLeftImageView(){
        // 左侧的取消按钮
        ImageView leftImg = createImageView(activity,leftStrId,DEFAULT_LEFT_IMG_RES,DEFAULT_LEFT_IMG_RES);
        getTitleLayoutLeft(activity).setOnClickListener(new ClickFinished(activity));
        lefts.add(leftImg);
    }
    private void addRightImageView(){
        // 左侧的取消按钮
        ImageView leftImg = createImageView(activity,rightImgId,DEFAULT_RIGHT_IMG_RES,DEFAULT_RIGHT_IMG_RES,10,10,10,10);
        rights.add(leftImg);
    }
    private void addRightImageView2(){
        // 左侧的取消按钮
        ImageView leftImg = createImageView(activity,rightImgId2,DEFAULT_RIGHT_IMG_RES,DEFAULT_RIGHT_IMG_RES,10,10,10,10);
        rights.add(leftImg);
    }
    private void addMidTextView(){
        // 左侧的取消按钮
        TextView midTitle = createTextView("",16,R.color.white,titleId);
        mids.add(midTitle);
    }
    private void addRightTextView(){
        // 左侧的取消按钮
        TextView midTitle = createTextView("",16,R.color.white,rightStrId);
        rights.add(midTitle);
    }


    public  class CloseAnimator {
        private ImageView view;
        public void close() {
            if (view != null) {
                view.clearAnimation();
                view.setImageResource(R.drawable.comfirm);
            }

        }
    }
}
