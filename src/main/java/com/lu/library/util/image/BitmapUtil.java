package com.lu.library.util.image;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import com.lu.library.util.file.FileUtil;
import com.lu.library.util.string.ConvertUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <pre>
 *
 *     desc  : Bitmap相关工具类
 * </pre>
 */
public class BitmapUtil {

    private BitmapUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    /**
     * 从文件读取方式一：获取缩放后的本地图片【不建议使用，效率低】
     *
     * @param filePath 文件路径
     * @param width    宽
     * @param height   高
     * @return
     */
    public static Bitmap readBitmapFromFile(String filePath, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        int inSampleSize = 1;

        if (srcHeight > height || srcWidth > width) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / height);
            } else {
                inSampleSize = Math.round(srcWidth / width);
            }
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;

        return BitmapFactory.decodeFile(filePath, options);
    }
    /**
     * 从二进制数据读取图片：获取缩放后的本地图片
     *
     * @param data
     * @param width
     * @param height
     * @return
     */
    public static Bitmap readBitmapFromByteArray(byte[] data, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        int inSampleSize = 1;

        if (srcHeight > height || srcWidth > width) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / height);
            } else {
                inSampleSize = Math.round(srcWidth / width);
            }
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }
    /**
     * 得到bitmap的大小
     * 单位KB
     */
    public static int getBitmapSize(Bitmap bitmap) {
        //API 19
        return bitmap.getAllocationByteCount()/1024;

    }
    /**
     * 组装地图截图和其他View截图，需要注意的是目前提供的方法限定为MapView与其他View在同一个ViewGroup下
     *
     * @param bitmap        地图截图回调返回的结果
     * @param viewContainer MapView和其他要截图的View所在的父容器ViewGroup
     * @param mapView       MapView控件
     * @param views         其他想要在截图中显示的控件
     */
    public static Bitmap getMapAndViewScreenShot(final Bitmap bitmap, final ViewGroup viewContainer, final View mapView, final boolean isScreeH, final View... views) {

        int width = viewContainer.getWidth();
//        int height = viewContainer.getHeight();
        int h = viewContainer.getHeight();
        if (isScreeH) {
            h = 0;
            for (int i = 0; i < viewContainer.getChildCount(); i++) {
                h += viewContainer.getChildAt(i).getHeight();
            }
        }

        final Bitmap screenBitmap = Bitmap.createBitmap(width, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(screenBitmap);
        canvas.drawBitmap(bitmap, mapView.getLeft(), mapView.getTop(), null);
        for (View view : views) {
            if (view.getVisibility() == View.VISIBLE) {
                view.setDrawingCacheEnabled(true);
                canvas.drawBitmap(view.getDrawingCache(), view.getLeft(), view.getTop(), null);
            }

        }

        return screenBitmap;
    }
    /**
     * bitmap转byteArr
     *
     * @param bitmap bitmap对象
     * @param format 格式
     * @return 字节数组
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap, CompressFormat format) {
        return ConvertUtil.bitmap2Bytes(bitmap, format);
    }

    /**
     * byteArr转bitmap
     *
     * @param bytes 字节数组
     * @return bitmap
     */
    public static Bitmap bytes2Bitmap(byte[] bytes) {
        return ConvertUtil.bytes2Bitmap(bytes);
    }

    /**
     * drawable转bitmap
     *
     * @param drawable drawable对象
     * @return bitmap
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        return ConvertUtil.drawable2Bitmap(drawable);
    }

    /** Bitmap 保存到sdcard
     *
     * @param b bitmap 对象，
     * @param strFileName 文件路径
     */
    public static void saveBitmap(Bitmap b, String strFileName) {
        FileOutputStream fos = null;
        long start=System.currentTimeMillis();
        try {
            fos = new FileOutputStream(String.valueOf(strFileName));
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * bitmap转drawable
     *
     * @param res    resources对象
     * @param bitmap bitmap对象
     * @return drawable
     */
    public static Drawable bitmap2Drawable(Resources res, Bitmap bitmap) {
        return ConvertUtil.bitmap2Drawable(res, bitmap);
    }

    /**
     * drawable转byteArr
     *
     * @param drawable drawable对象
     * @param format   格式
     * @return 字节数组
     */
    public static byte[] drawable2Bytes(Drawable drawable, CompressFormat format) {
        return ConvertUtil.drawable2Bytes(drawable, format);
    }

    /**
     * byteArr转drawable
     *
     * @param res   resources对象
     * @param bytes 字节数组
     * @return drawable
     */
    public static Drawable bytes2Drawable(Resources res, byte[] bytes) {
        return ConvertUtil.bytes2Drawable(res, bytes);
    }

    /**
     * view转Bitmap
     *
     * @param view 视图
     * @return bitmap
     */
    public static Bitmap view2Bitmap(View view) {
        return ConvertUtil.view2Bitmap(view);
    }
    /** Bitmap 保存到sdcard
     *
     * @param b bitmap 对象，
     * @param filePath 文件路径
     */
    public static void save(Bitmap b, String filePath) {
        save(b,filePath,100);
    }
    /** Bitmap 保存到sdcard
     *
     * @param b bitmap 对象，
     * @param file 文件路径
     */
    public static void save(Bitmap b, File file) {
        save(b,file,100);
    }
    /** Bitmap 保存到sdcard
     *
     * @param b bitmap 对象，
     * @param filePath 文件路径
     * @param quality 质量
     */
    public static void save(Bitmap b, String filePath,int quality) {
        save(b,filePath,null,quality);
    }
    /** Bitmap 保存到sdcard
     *
     * @param b bitmap 对象，
     * @param file 文件路径
     * @param quality 质量
     */
    public static void save(Bitmap b, File file,int quality) {
        save(b,file,null,quality);
    }

    /**
     * 保存图片
     *
     * @param src      源图片
     * @param filePath 要保存到的文件路径
     * @param format   格式
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean save(Bitmap src, String filePath, CompressFormat format,int quality) {
        return save(src, FileUtil.getFileByPath(filePath), format, quality,false);
    }

    /**
     * 保存图片
     *
     * @param src    源图片
     * @param file   要保存到的文件
     * @param format 格式
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean save(Bitmap src, File file, CompressFormat format,int quality) {
        return save(src, file, format, quality,false);
    }

    /**
     * 保存图片
     *
     * @param src      源图片
     * @param filePath 要保存到的文件路径
     * @param format   格式
     * @param recycle  是否回收
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean save(Bitmap src, String filePath, CompressFormat format,int quality, boolean recycle) {
        return save(src, FileUtil.getFileByPath(filePath), format,quality, recycle);
    }

    /**
     * 保存图片
     *
     * @param src     源图片
     * @param file    要保存到的文件
     * @param format  格式
     * @param recycle 是否回收
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean save(Bitmap src, File file, CompressFormat format,int quality, boolean recycle) {
        if (isEmptyBitmap(src) || !FileUtil.createOrExistsFile(file)) return false;
        if (format==null){
            format=Bitmap.CompressFormat.PNG;
        }
        OutputStream os = null;
        boolean ret = false;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            ret = src.compress(format, quality, os);
            if (recycle && !src.isRecycled()) src.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            FileUtil.closeIO(os);
        }
        return ret;
    }

    /**
     * 根据文件名判断文件是否为图片
     *
     * @param file 　文件
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isImage(File file) {
        return file != null && isImage(file.getPath());
    }

    /**
     * 根据文件名判断文件是否为图片
     *
     * @param filePath 　文件路径
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isImage(String filePath) {
        String path = filePath.toUpperCase();
        return path.endsWith(".PNG") || path.endsWith(".JPG")
                || path.endsWith(".JPEG") || path.endsWith(".BMP")
                || path.endsWith(".GIF");
    }

    /**
     * 获取图片类型
     *
     * @param filePath 文件路径
     * @return 图片类型
     */
    public static String getImageType(String filePath) {
        return getImageType(FileUtil.getFileByPath(filePath));
    }

    /**
     * 获取图片类型
     *
     * @param file 文件
     * @return 图片类型
     */
    public static String getImageType(File file) {
        if (file == null) return null;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            return getImageType(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            FileUtil.closeIO(is);
        }
    }

    /**
     * 流获取图片类型
     *
     * @param is 图片输入流
     * @return 图片类型
     */
    public static String getImageType(InputStream is) {
        if (is == null) return null;
        try {
            byte[] bytes = new byte[8];
            return is.read(bytes, 0, 8) != -1 ? getImageType(bytes) : null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取图片类型
     *
     * @param bytes bitmap的前8字节
     * @return 图片类型
     */
    public static String getImageType(byte[] bytes) {
        if (isJPEG(bytes)) return "JPEG";
        if (isGIF(bytes)) return "GIF";
        if (isPNG(bytes)) return "PNG";
        if (isBMP(bytes)) return "BMP";
        return null;
    }

    private static boolean isJPEG(byte[] b) {
        return b.length >= 2
                && (b[0] == (byte) 0xFF) && (b[1] == (byte) 0xD8);
    }

    private static boolean isGIF(byte[] b) {
        return b.length >= 6
                && b[0] == 'G' && b[1] == 'I'
                && b[2] == 'F' && b[3] == '8'
                && (b[4] == '7' || b[4] == '9') && b[5] == 'a';
    }

    private static boolean isPNG(byte[] b) {
        return b.length >= 8
                && (b[0] == (byte) 137 && b[1] == (byte) 80
                && b[2] == (byte) 78 && b[3] == (byte) 71
                && b[4] == (byte) 13 && b[5] == (byte) 10
                && b[6] == (byte) 26 && b[7] == (byte) 10);
    }

    private static boolean isBMP(byte[] b) {
        return b.length >= 2
                && (b[0] == 0x42) && (b[1] == 0x4d);
    }

    /**
     * 判断bitmap对象是否为空
     *
     * @param src 源图片
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isEmptyBitmap(Bitmap src) {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
    }




}