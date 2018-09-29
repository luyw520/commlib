package com.lu.library.photo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Handler;
import android.view.View;

import com.lu.library.Constant;
import com.lu.library.R;
import com.lu.library.base.BaseActivity;
import com.lu.library.util.AsyncTaskUtil;
import com.lu.library.util.BitmapUtil;
import com.lu.library.util.DebugLog;
import com.lu.library.widget.CommonTitleBarHelper;
import com.lu.library.widget.clip.ClipImageLayout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static com.lu.library.Constant.REQUEST_CODE_PHOTO_RESOULT;
import static com.lu.library.photo.PhotoHelper.photoPath;
import static com.lu.library.widget.DialogManager.showWaitDialog;

public class ImageFactoryActivity extends BaseActivity {


	private ClipImageLayout cropImageView;
	private Bitmap bitmap;
	private Handler handler = new Handler() ;
	@Override
	public void initData() {
		// 标题栏【带左边取消和右边确定监听处理的】
		commonTitleBarHelper.initLayout(CommonTitleBarHelper.STYLE_RIGHT_IMG);
		setTitle(R.string.crop);
		commonTitleBarHelper.setRightOnClick(cropClick);
		final Uri uri=getIntent().getData();
		if (android.os.Build.MANUFACTURER.equalsIgnoreCase("xiaomi")||android.os.Build.MANUFACTURER.equalsIgnoreCase("samsung")){

			Bitmap bitmap = BitmapUtil.readBitmapFromFile(uri.getPath(), 400, 400);
			if(uri.getPath().endsWith(".png")) {
				if (android.os.Build.MANUFACTURER.equalsIgnoreCase("samsung")) {
					if (bitmap != null) {
						cropImageView.mZoomImageView.setImageBitmap(rotateBitmapByDegree(bitmap, getBitmapDegree(uri.getPath())));
					}
				} else {
					if (bitmap != null) {
						cropImageView.mZoomImageView.setImageBitmap(rotateBitmapByDegree(bitmap, getBitmapDegree(uri.getPath())));
					}
				}
			}else{
				Bitmap bitmap2 = null;
				try {
					bitmap2=getBitmapFormUri(uri);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (bitmap2 != null){
					DebugLog.d("ImageFactoryActivity  不是png图片+bitmap2不为空");
					cropImageView.mZoomImageView.setImageBitmap(rotateBitmapByDegree(bitmap2, getBitmapDegree(uri.getPath())));
				}else{
					DebugLog.d("ImageFactoryActivity  不是png图片+bitmap2为空");
				}

			}
		}else{
			Drawable drawable=null;
			try {
//				InputStream input = getContentResolver().openInputStream(uri);
//				drawable=Drawable.createFromStream(getContentResolver().openInputStream(uri),null);
//				Bitmap bitmap2 = BitmapFactory.decodeStream(input);

						Bitmap bitmap=getBitmapFormUri(uri);
				if (bitmap!=null){
					cropImageView.mZoomImageView.setImageBitmap(bitmap);
				}else{
					bitmap = BitmapUtil.readBitmapFromFile(uri.getPath(), 400, 400);
					if (bitmap!=null){
						cropImageView.mZoomImageView.setImageBitmap(bitmap);
					}else{
						handler.postDelayed(new Runnable() {
							@Override
							public void run() {
								Bitmap bitmap2=getBitmapFormUri(uri);
								cropImageView.mZoomImageView.setImageBitmap(bitmap2);
							}
						},2000);
					}
				}


			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		initEvent();
	}

	@Override
	public int getLayoutResID() {
		return R.layout.activity_imagefactory;
	}

	public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
		Bitmap returnBm = null;

		// 根据旋转角度，生成旋转矩阵
		Matrix matrix = new Matrix();
		matrix.postRotate(degree);
		try {
			// 将原始图片按照旋转矩阵进行旋转，并得到新的图片
			returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
		} catch (OutOfMemoryError e) {
		}
		if (returnBm == null) {
			returnBm = bm;
		}
		if (bm != returnBm) {
			bm.recycle();
		}
		return returnBm;
	}
	private int getBitmapDegree(String path) {
		int degree = 0;
		try {
			// 从指定路径下读取图片，并获取其EXIF信息
			ExifInterface exifInterface = new ExifInterface(path);
			// 获取图片的旋转信息
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}
	public void initEvent() {
	}

	View.OnClickListener cropClick=new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			final ProgressDialog dialog=showWaitDialog(ImageFactoryActivity.this,getString(R.string.requesting));
			new AsyncTaskUtil().setIAsyncTaskCallBack(new AsyncTaskUtil.IAsyncTaskCallBack() {
				@Override
				public Object doInBackground(String... arg0) {
					Bitmap bitmap=cropImageView.clip();
					File file = new File(Constant.PIC_PATH + photoPath);
					BitmapUtil.saveBitmap(bitmap, file.getAbsolutePath());
					return null;
				}

				@Override
				public void onPostExecute(Object result) {
//					DialogUtil.dismissWaitDialog();
					dialog.dismiss();
					Intent intent=new Intent();
					setResult(REQUEST_CODE_PHOTO_RESOULT,intent);
					finish();
				}
			}).execute("");
		}
	};

	@Override
	public void initViews() {
		cropImageView= (ClipImageLayout) findViewById(R.id.cropImageView);

	}

	public  Bitmap getBitmapFormUri( Uri uri) {

		try {
			InputStream input= getContentResolver().openInputStream(uri);
			BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
			onlyBoundsOptions.inJustDecodeBounds = true;
			onlyBoundsOptions.inDither = true;//optional
			onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
			BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
			input.close();
			int originalWidth = onlyBoundsOptions.outWidth;
			int originalHeight = onlyBoundsOptions.outHeight;
			DebugLog.i("getBitmapFormUri()............22");
			if ((originalWidth == -1) || (originalHeight == -1)){
				DebugLog.i("getBitmapFormUri()............nullll");
				return null;
			}

			//图片分辨率以480x800为标准
			float hh = 800f;//这里设置高度为800f
			float ww = 480f;//这里设置宽度为480f
			//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
			int be = 1;//be=1表示不缩放
			if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
				be = (int) (originalWidth / ww);
			} else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
				be = (int) (originalHeight / hh);
			}
			if (be <= 0)
				be = 1;
			//比例压缩
			BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
			bitmapOptions.inSampleSize = be;//设置缩放比例
			bitmapOptions.inDither = true;//optional
			bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
			input = getContentResolver().openInputStream(uri);
			Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
			input.close();
			DebugLog.i("getBitmapFormUri():"+bitmap);
			return compressImage(bitmap);
		} catch (Exception e) {
			DebugLog.i("Exception():"+bitmap);
			e.printStackTrace();
		}


		return compressImage(bitmap);//再进行质量压缩
	}


	public  Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();//重置baos即清空baos
			//第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;//每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
		return bitmap;
	}

}