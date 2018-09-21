package com.lu.library.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Adapter;
import android.widget.ListView;

public class ViewUtil {

	/**
	 *
	 * @param colorA
	 *            the startColor
	 * @param colorB
	 *            the endColor
	 * @param degree
	 *            the count of color what you want between startColor & endColor
	 * @param progress
	 *            the index in degree
	 * @return
	 */
	public static int getColorBetweenAB(int colorA, int colorB, float degree, int progress) {
		// calculate R
		float r = (((colorB & 0xFF0000) >> 16) - ((colorA & 0xFF0000) >> 16)) / degree * progress + ((colorA & 0xFF0000) >> 16);
		// calculate G
		float g = (((colorB & 0x00FF00) - (colorA & 0x00FF00)) >> 8) / degree * progress + ((colorA & 0x00FF00) >> 8);
		// calculate B
		float b = ((colorB & 0x0000FF) - (colorA & 0x0000FF)) / degree * progress + (colorA & 0x0000FF);
		return Color.rgb((int) r, (int) g, (int) b);
	}

	/**
	 *
	 * @param b
	 *            需要模糊的bitmap
	 * @param context
	 * @return 模糊后的bitmap
	 */
	public static Bitmap blur(Bitmap b, Context context) {
		RenderScript rs = RenderScript.create(context);
		Allocation overlayAlloc = Allocation.createFromBitmap(rs, b);
		ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, overlayAlloc.getElement());
		blur.setInput(overlayAlloc);
		blur.setRadius(25);
		blur.forEach(overlayAlloc);
		overlayAlloc.copyTo(b);
		return b;

	}

	/**
	 *
	 * @param paint
	 *            测试前，必须保证paint已经设置过textSize , Typeface;
	 * @return 该paint画出文字的理应高度
	 */
	public static float getTextHeight(Paint paint) {
		FontMetrics m = paint.getFontMetrics();
		return m.bottom - m.top;
	}

	/**
	 *
	 * @param paint
	 * @param content
	 * @return
	 */
	public static float getTextRectWidth(Paint paint, String content) {
		// if()
		Rect rect = new Rect();

		paint.getTextBounds(content, 0, content.length(), rect);
//		return rect.width();
		return paint.measureText(content);
	}

	/**
	 * 这个返回值<={@link #getTextHeight(Paint)}
	 *
	 * @return
	 */
	public static float getTextRectHeight(Paint paint, String content) {
		Rect rect = new Rect();
		paint.getTextBounds(content, 0, content.length(), rect);
		return rect.height();
	}
	/**
	 * 给view添加旋转动画
	 * @param view
	 */
	public static void startRotateAnimation(View view) {
		view.setVisibility(View.VISIBLE);
		view.clearAnimation();
		RotateAnimation rotateAnimation = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setDuration(500);
		rotateAnimation.setRepeatCount(-1);
		view.startAnimation(rotateAnimation);
	}

	/**
	 * 画矩形
	 *
	 * @param canvas
	 * @param left           左边
	 * @param top            上面
	 * @param yScale         y刻度
	 * @param value          值
	 * @param MAX_VALUE      最大值
	 * @param width          宽度
	 * @param rectProportion 比例
	 * @param rectPadding    上下边距
	 * @param paint          画笔
	 */
	public static void drawRect(Canvas canvas, float left, float top, float yScale, int value, int MAX_VALUE, float width, float rectProportion, float rectPadding, Paint paint) {
		// 画矩形
		top = top + rectPadding;
		float right = left + calculateRectW(value, MAX_VALUE, width, rectProportion);
		float bottom = top + yScale - rectPadding;
		canvas.drawRect(left, top, right, bottom, paint);
	}


	/**
	 * 计算矩形的宽度
	 *
	 * @param value：值
	 * @param MAX_VALUE：最大值
	 * @param rectProportion：矩形比例（0 ~ 1）
	 * @return
	 */
	public static float calculateRectW(int value, int MAX_VALUE, float width, float rectProportion) {
		if (rectProportion >= 1f) {
			rectProportion = 1f;
		}

		if (rectProportion <= 0) {
			rectProportion = 0;
		}
		if (MAX_VALUE == 0) {
			return 0f;
		} else {
			float scaleH = (rectProportion * width) / MAX_VALUE;
			return value * scaleH;
		}
	}
	/**
	 * 获取文本，形如： xx 时 xx 分
	 *
	 * @param canvas
	 * @param mins：分钟
	 * @param startX：开始位置X
	 * @param startY：开始位置Y
	 * @param padding：字体之间的间距
	 * @param dataSize：数据大小
	 * @param unitSize：单位大小
	 * @param textColor：字体颜色
	 */
	public static void drawText(Canvas canvas, int mins, float startX, float startY, float padding, float dataSize, float unitSize, int textColor, String hourUnit, String minUnit) {

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setColor(textColor);

		String hourData = mins / 60 + "";
		String minData = mins % 60 + "";

		// 时数据
		paint.setTextSize(dataSize);
		float hourDataHeight = ViewUtil.getTextRectHeight(paint, hourData);
		canvas.drawText(hourData, startX, startY, paint);

		// 时单位
		startX = startX + padding + ViewUtil.getTextRectWidth(paint, hourData);
		paint.setTextSize(unitSize);
		float hourUnitHeight = ViewUtil.getTextRectHeight(paint, hourUnit);
		float surplusHeight = hourDataHeight > hourUnitHeight ? (hourDataHeight - hourUnitHeight) : (hourUnitHeight - hourDataHeight);
		canvas.drawText(hourUnit, startX, startY - surplusHeight / 4, paint);

		// 分数据
		startX = startX + padding + ViewUtil.getTextRectWidth(paint, hourUnit);
		paint.setTextSize(dataSize);
		canvas.drawText(minData, startX, startY, paint);

		// 分单位
		startX = startX + padding + ViewUtil.getTextRectWidth(paint, minData);
		paint.setTextSize(unitSize);
		canvas.drawText(minUnit, startX, startY - surplusHeight / 4, paint);
	}
	/**
	 * 获取文本，形如： xx 时 xx 分
	 *
	 * @param canvas
	 * @param mins：分钟
	 * @param startX：开始位置X
	 * @param startY：开始位置Y
	 * @param padding：字体之间的间距
	 * @param dataSize：数据大小
	 * @param unitSize：单位大小
	 * @param textColor：字体颜色
	 * @param align：方向
	 */
	public static void drawText(Canvas canvas, int mins, float startX, float startY, float padding, float dataSize, float unitSize, int textColor, String hourUnit, String minUnit, Paint.Align align) {

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setTextAlign(align);
		paint.setColor(textColor);

		String hourData = String.format("%02d", mins / 60);
		String minData = String.format("%02d", mins % 60);

		paint.setTextSize(dataSize);
		float hourDataHeight = ViewUtil.getTextRectHeight(paint, hourData);
		paint.setTextSize(unitSize);
		float hourUnitHeight = ViewUtil.getTextRectHeight(paint, hourUnit);
		// 计算多余的高度
		float surplusHeight = hourDataHeight > hourUnitHeight ? (hourDataHeight - hourUnitHeight) : (hourUnitHeight - hourDataHeight);

		if (align == Paint.Align.LEFT) {

			// 时数据
			paint.setTextSize(dataSize);
			canvas.drawText(hourData, startX, startY, paint);

			// 时单位
			startX = startX + padding + ViewUtil.getTextRectWidth(paint, hourData);
			paint.setTextSize(unitSize);
			canvas.drawText(hourUnit, startX, startY - surplusHeight / 4, paint);

			// 分数据
			startX = startX + padding + ViewUtil.getTextRectWidth(paint, hourUnit);
			paint.setTextSize(dataSize);
			canvas.drawText(minData, startX, startY, paint);

			// 分单位
			startX = startX + padding + ViewUtil.getTextRectWidth(paint, minData);
			paint.setTextSize(unitSize);
			canvas.drawText(minUnit, startX, startY - surplusHeight / 4, paint);
		} else if (align == Paint.Align.CENTER) {

			float saveX = startX;

			// 时单位
			paint.setTextAlign(Paint.Align.RIGHT);
			startX = saveX - padding / 2;
			paint.setTextSize(unitSize);
			canvas.drawText(hourUnit, startX, startY - surplusHeight / 4, paint);

			// 时数据
			paint.setTextAlign(Paint.Align.RIGHT);
			startX = startX - padding - ViewUtil.getTextRectWidth(paint, hourUnit);
			paint.setTextSize(dataSize);
			canvas.drawText(hourData, startX, startY, paint);

			// 分数据
			paint.setTextAlign(Paint.Align.LEFT);
			startX = saveX + padding / 2;
			paint.setTextSize(dataSize);
			canvas.drawText(minData, startX, startY, paint);

			// 分单位
			paint.setTextAlign(Paint.Align.LEFT);
			startX = startX + padding + ViewUtil.getTextRectWidth(paint, minData);
			paint.setTextSize(unitSize);
			canvas.drawText(minUnit, startX, startY - surplusHeight / 4, paint);

		} else if (align == Paint.Align.RIGHT) {

			// 分单位
			paint.setTextSize(unitSize);
			canvas.drawText(minUnit, startX, startY - surplusHeight / 4, paint);

			// 分数据
			startX = startX - padding - ViewUtil.getTextRectWidth(paint, minUnit);
			paint.setTextSize(dataSize);
			canvas.drawText(minData, startX, startY, paint);

			// 时单位
			startX = startX - padding - ViewUtil.getTextRectWidth(paint, minData);
			paint.setTextSize(unitSize);
			canvas.drawText(hourUnit, startX, startY - surplusHeight / 4, paint);

			// 时数据
			startX = startX - padding - ViewUtil.getTextRectWidth(paint, hourUnit);
			paint.setTextSize(dataSize);
			canvas.drawText(hourData, startX, startY, paint);

		}

	}


	public static float px2Dp(int px, Context context) {
		return px * context.getResources().getDisplayMetrics().density;
	}

	/**
	 * 获取ListView的高度
	 * @param listView listview 对象
	 * @param mAdapter Listview的adapter对象
	 * @return
	 */
	public static int getTotalHeightOfListView(ListView listView, Adapter mAdapter) {
		if (mAdapter == null) {
			return 0;
		}
		int totalHeight = 0;
		for (int i = 0; i < mAdapter.getCount(); i++) {
			View mView = mAdapter.getView(i, null, listView);
			if (mView.getMeasuredHeight()==0){
				mView.measure(
						View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
						View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
			}

			totalHeight += mView.getMeasuredHeight();
		}
		return totalHeight + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
	}

	/**
	 * 判断 listview里面的控件高度是否大于footerHeight
	 * @param listView Listview对象
	 * @param mAdapter Listview的adapter对象
	 * @param footerHeight 对比高度
	 * @return true 大于，false 不大于
	 */
	public static boolean getTotalHeightOfListView(ListView listView, Adapter mAdapter, int footerHeight) {
		if (mAdapter == null) {
			return false;
		}
		int totalHeight = 0;
		long startTime=System.currentTimeMillis();
		for (int i = 0; i < mAdapter.getCount(); i++) {
			View mView = mAdapter.getView(i, null, listView);
			if (mView.getMeasuredHeight()==0){
				mView.measure(
						View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
						View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

			}

			totalHeight += mView.getMeasuredHeight();
			if (totalHeight > footerHeight) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 画虚线
	 *
	 * @param canvas
	 * @param lineStartX
	 * @param lineStartTop
	 * @param lineWidth
	 * @param lineTotalWidth
	 * @param mPaint
	 */
	public static void drawLine(Canvas canvas, float lineStartX, float lineStartTop, float lineWidth, float lineTotalWidth, Paint mPaint) {
		int lineLen = (int) (lineTotalWidth / lineWidth);
		for (int i = 0; i < lineLen; i++) {
			float startX = lineWidth * (i + 1) + lineStartX;
			float stopX = lineWidth * (i + 1) + lineWidth / 2 + lineStartX;
			canvas.drawLine(startX, lineStartTop, stopX, lineStartTop, mPaint);
			if (startX >= lineTotalWidth || stopX >= lineTotalWidth) {
				break;
			}
		}
	}

}
