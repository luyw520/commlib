package com.lu.library.util.image;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;

/**
 */
public class ImageUtil {
	public static final int TOP = 0; //
	public static final int BOTTOM = 1; //
	public static final int LEFT = 2; //
	public static final int RIGHT = 3; //
	public static final int LEFT_TOP = 4; //
	public static final int LEFT_BOTTOM = 5; //
	public static final int RIGHT_TOP = 6; //
	public static final int RIGHT_BOTTOM = 7; //

	/**
	 * 图片转二进制
	 *
	 * @param bm：要做转换的图片
	 * @return
	 */
	public static byte[] bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * 图片转二进制
	 *
	 * @param drawable：要做转换的图片
	 * @return
	 */
	public byte[] drawable2Bytes(Drawable drawable) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Bitmap bm = drawableToBitmap(drawable);
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}


	/**
	 * Bitmap转Drawable
	 *
	 * @param resources：资源
	 * @param bm：要转换的图片
	 * @return
	 */
	public static Drawable bitmapToDrawable(Resources resources, Bitmap bm) {
		Drawable drawable = new BitmapDrawable(resources, bm);
		return drawable;
	}

	/**
	 * Drawable转Bitmap
	 *
	 * @param drawable：要转换的Drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable){
			return drawableToBitmap(drawable);
		}
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 *
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(BitmapDrawable drawable) {
		return (drawable).getBitmap();
	}

	/**
	 *
	 * @param bitmap
	 * @return
	 */
	public static Drawable bitmapToDrawable(Bitmap bitmap) {
		@SuppressWarnings("deprecation")
		Drawable drawable = new BitmapDrawable(bitmap);
		return drawable;
	}

	/**
	 * Bitmapתbyte[]
	 *
	 * @param bitmap
	 * @return
	 */
	public static byte[] bitmapToByte(Bitmap bitmap) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
		return out.toByteArray();
	}

	/**
	 * byte[]תBitmap
	 *
	 * @param data
	 * @return
	 */
	public static Bitmap byteToBitmap(byte[] data) {
		if (data.length != 0) {
			return BitmapFactory.decodeByteArray(data, 0, data.length);
		}
		return null;
	}


	/**
	 *
	 * @param context
	 * @param srcId
	 * @param tipId
	 * @return
	 */
	public static Drawable createSelectedTip(Context context, int srcId,
			int tipId) {
		Bitmap src = BitmapFactory
				.decodeResource(context.getResources(), srcId);
		Bitmap tip = BitmapFactory
				.decodeResource(context.getResources(), tipId);
		final int w = src.getWidth();
		final int h = src.getHeight();
		Bitmap bitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Paint paint = new Paint();
		Canvas canvas = new Canvas(bitmap);
		canvas.drawBitmap(src, 0, 0, paint);
		canvas.drawBitmap(tip, (w - tip.getWidth()), 0, paint);
		return bitmapToDrawable(bitmap);
	}


}
