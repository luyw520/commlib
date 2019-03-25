package com.lu.library.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lu.library.R;
import com.lu.library.log.DebugLog;


public class CustomToggleButton extends View implements View.OnTouchListener {

    public boolean isTouchEnable = true;
    Paint paint = new Paint();
    // 开关开启时的背景，关闭时的背景，滑动按钮
    private Bitmap switch_on_Bkg, switch_off_Bkg, slip_Btn;
    private Rect on_Rect, off_Rect;
    // 是否正在滑动
    private boolean isSlipping = false;
    // 当前开关状态，true为开启，false为关闭
    private boolean isSwitchOn = false;
    // //是否设置了开关监听器
    // private boolean isSwitchListenerOn = false;
    // 手指按下时的水平坐标X，当前的水平坐标X
    private float previousX, currentX;
    // 开关监听器
    private OnSwitchListener onSwitchListener;
    private float dis;
    private int rectPadding = 3;

    public CustomToggleButton(Context context) {
        super(context);
        init();
    }

    public CustomToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOnTouchListener(this);
        setImageResource(R.drawable.toggle_on, R.drawable.toggle_off, R.drawable.toggle_thumb);
    }

    public void setImageResource(int switchOnBkg, int switchOffBkg, int slipBtn) {
        switch_on_Bkg = BitmapFactory.decodeResource(getResources(), switchOnBkg);
        switch_off_Bkg = BitmapFactory.decodeResource(getResources(), switchOffBkg);
        slip_Btn = BitmapFactory.decodeResource(getResources(), slipBtn);

        // 右半边Rect，即滑动按钮在右半边时表示开关开启
        on_Rect = new Rect(switch_off_Bkg.getWidth() - slip_Btn.getWidth() - rectPadding, rectPadding, switch_off_Bkg.getWidth() - rectPadding, slip_Btn.getHeight() - rectPadding);
        // 左半边Rect，即滑动按钮在左半边时表示开关关闭
        off_Rect = new Rect(rectPadding, rectPadding, slip_Btn.getWidth() + rectPadding, slip_Btn.getHeight() - rectPadding);
    }

    public boolean getSwitchState() {
        return isSwitchOn;
    }

    public void setSwitchState(boolean switchState) {
        isSwitchOn = switchState;
        postInvalidate();

    }

    protected void updateSwitchState(boolean switchState) {
        isSwitchOn = switchState;
        postInvalidate();
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    private Callback callback;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        DebugLog.d("onDraw.........isSwitchOn:"+isSwitchOn);
        // Matrix matrix = new Matrix();
        // Paint paint = new Paint();
        // 滑动按钮的左边坐标
        float left_SlipBtn;

        // 手指滑动到左半边的时候表示开关为关闭状态，滑动到右半边的时候表示开关为开启状态
        // if(isSlipping && currentX < (switch_on_Bkg.getWidth() / 2)) {
        // // canvas.drawBitmap(switch_off_Bkg, null, paint);
        // canvas.drawBitmap(switch_off_Bkg, 0, (getMeasuredHeight() -
        // switch_off_Bkg.getHeight()) / 2, paint);
        // } else {
        // // canvas.drawBitmap(switch_on_Bkg, null, paint);
        // canvas.drawBitmap(switch_on_Bkg, 0, (getMeasuredHeight() -
        // switch_off_Bkg.getHeight()) / 2, paint);
        // }

        // 判断当前是否正在滑动
        if (isSlipping) {
            if (currentX > switch_on_Bkg.getWidth()) {
                left_SlipBtn = switch_on_Bkg.getWidth() - slip_Btn.getWidth();
            } else {
                left_SlipBtn = currentX - slip_Btn.getWidth() / 2;
            }
            if (isSlipping && currentX < (switch_on_Bkg.getWidth() / 2)) {
                canvas.drawBitmap(switch_off_Bkg, 0, (getMeasuredHeight() - switch_off_Bkg.getHeight()) / 2, paint);
            } else {
                canvas.drawBitmap(switch_on_Bkg, 0, (getMeasuredHeight() - switch_off_Bkg.getHeight()) / 2, paint);
            }
        } else {
            // 根据当前的开关状态设置滑动按钮的位置
            if (isSwitchOn) {
                left_SlipBtn = on_Rect.left;
                canvas.drawBitmap(switch_on_Bkg, 0, (getMeasuredHeight() - switch_off_Bkg.getHeight()) / 2, paint);
            } else {
                left_SlipBtn = off_Rect.left;
                canvas.drawBitmap(switch_off_Bkg, 0, (getMeasuredHeight() - switch_off_Bkg.getHeight()) / 2, paint);
            }
        }

        // 对滑动按钮的位置进行异常判断
        if (left_SlipBtn < 0) {
            left_SlipBtn = 0;
        } else if (left_SlipBtn > switch_on_Bkg.getWidth() - slip_Btn.getWidth()) {
            left_SlipBtn = switch_on_Bkg.getWidth() - slip_Btn.getWidth();
        }

        canvas.drawBitmap(slip_Btn, left_SlipBtn, (getMeasuredHeight() - slip_Btn.getHeight()) / 2, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // DebugLog.d("ToggleView mode = " +
        // MeasureSpec.getMode(heightMeasureSpec));
        int h = MeasureSpec.getSize(heightMeasureSpec);
        switch (MeasureSpec.getMode(heightMeasureSpec)) {
            case MeasureSpec.UNSPECIFIED:
                h = switch_on_Bkg.getHeight() + getPaddingBottom() + getPaddingTop();
                break;

            default:
                break;
        }
        setMeasuredDimension(switch_on_Bkg.getWidth(), h);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (callback!=null&& callback.handerEvent()){
            return false;
        }
        if (!isTouchEnable) return false;
        // 松开前开关的状态
        boolean previousSwitchState = isSwitchOn;
        boolean tempState = isSwitchOn;
        switch (event.getAction()) {
            // 滑动
            case MotionEvent.ACTION_MOVE:
                dis = event.getX() - previousX;
                currentX = event.getX();
                break;

            // 按下
//            case MotionEvent.ACTION_DOWN:
//                if (event.getX() > switch_on_Bkg.getWidth()) {
//                    return false;
//                }
//                dis = 0;
//                isSlipping = true;
//                previousX = event.getX();
//                currentX = previousX;
//                break;
            // 松开
            case MotionEvent.ACTION_UP:
                isSlipping = false;

                tempState = dis > (switch_on_Bkg.getWidth() / 2);
                DebugLog.d("eventAction = " + event.getAction() + "***x = " + event.getX() + "***limit = " + (switch_on_Bkg.getWidth()));
                DebugLog.d("eventAction = " + event.getAction() + "***tempState = " + tempState + "***isSwitchOn = " + isSwitchOn + "***dis = " + dis);
//                if (event.getX() >= (switch_on_Bkg.getWidth() / 2)) {
//                    tempState = true;
//                } else {
//                    tempState = false;
//                }
                isSwitchOn=!isSwitchOn;
                // 如果设置了监听器，则调用此方法
                if (onSwitchListener != null) {
                    onSwitchListener.onSwitched(isSwitchOn);
                }
//                if (onSwitchListener != null && (tempState != isSwitchOn)) {
//                    onSwitchListener.onSwitched(tempState);
//                }
//                isSwitchOn = tempState;
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                isSlipping = false;
                // 如果设置了监听器，则调用此方法
//                tempState = currentX >= switch_on_Bkg.getWidth() / 2;
//                DebugLog.d("eventAction = " + event.getAction() + "***tempState = " + tempState + "***isSwitchOn = " + isSwitchOn + "***dis = " + dis);
//                if (onSwitchListener != null && tempState != isSwitchOn) {
//                    onSwitchListener.onSwitched(tempState);
//                }
//                isSwitchOn = tempState;
                // 重新绘制控件
                invalidate();
                break;

            default:
                break;
        }


        return true;
    }

    public void setOnSwitchListener(OnSwitchListener listener) {
        onSwitchListener = listener;
    }

    public interface OnSwitchListener {
        abstract void onSwitched(boolean isSwitchOn);
    }
    public interface Callback{
        boolean handerEvent();
    }
}
