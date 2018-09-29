package com.lu.library.photo;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;

import com.lu.library.Constant;
import com.lu.library.R;
import com.lu.library.util.AsyncTaskUtil;
import com.lu.library.util.image.BitmapUtils;

import java.io.File;

import static com.lu.library.Constant.PERMISSIONS_REQUEST_CODE_PHOTO_CAMERA;
import static com.lu.library.Constant.REQUEST_CODE_PHOTO_CAMERA;
import static com.lu.library.Constant.REQUEST_CODE_PHOTO_CROP;
import static com.lu.library.Constant.REQUEST_CODE_PHOTO_PICK;


/**
 * Created by lyw.
 *
 * @author: lyw
 * @package: com.ido.veryfitpro.common.photo
 * @description: ${TODO}{ 图片选择，或者拍照辅助类}
 * @date: 2018/9/17 0017
 */
public class PhotoHelper {
    Activity activity;
    public static String photoTemp = "/temp.png";
    public static String photoPath = "/avatar.jpg";
    public static File photoFile=new File(Constant.PIC_PATH+photoPath);
    public static File tempPhoto = new File(Constant.PIC_PATH + photoTemp);
    public PhotoHelper(Activity activity){
        this.activity=activity;
    }
    Dialog dialog;
    /**
     * 显示选择弹出框
     * @param activity
     */
    public void showPhotosDaiglog(Activity activity){
        if (dialog==null){
            dialog=showPhotosDaiglog(activity,selectPhotoClick,takePhotoClick);
        }
        dialog.show();
    }

    View.OnClickListener selectPhotoClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            activity.startActivityForResult(intent, REQUEST_CODE_PHOTO_PICK);
            dialog.dismiss();
        }
    };
    View.OnClickListener takePhotoClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean isPermissions = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED;
            if (isPermissions) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    activity.requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CODE_PHOTO_CAMERA);
                }
                return;
            }
            dialog.dismiss();
            jumpCaptureActivity();
        }
    };

    /**
     * 跳转裁剪界面
     * @param uri
     */
    private  void startPhotoCrop(Uri uri) {
            Intent intent = new Intent(activity, ImageFactoryActivity.class);
            intent.setData(uri);
            activity.startActivityForResult(intent, REQUEST_CODE_PHOTO_CROP);
    }
    public  Bitmap onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_CODE_PHOTO_CAMERA:
                startPhotoCrop(Uri.fromFile(tempPhoto));
                break;
            case REQUEST_CODE_PHOTO_PICK:
                if (data == null) {
                    return null;
                }
                photoTemp = data.getData().toString()
                        .substring(data.getData().toString().lastIndexOf("/") + 1);
                startPhotoCrop(data.getData());
                break;
            case REQUEST_CODE_PHOTO_CROP:
//                Bundle extras = data.getExtras();
//                if (extras != null) {
                    final Bitmap photo = BitmapFactory.decodeFile(Constant.PIC_PATH + photoPath);
                    File old =tempPhoto;
                    if (old.exists()) {
                        old.delete();
                    }
                    return photo;
//                }
//                break;
        }

        return null;

    }
    private void saveBitmapInBackground(final Bitmap photo){
        new AsyncTaskUtil(new AsyncTaskUtil.AsyncTaskCallBackAdapter(){
            @Override
            public Object doInBackground(String... arg0) {
                File file = new File(Constant.PIC_PATH + photoPath);
                BitmapUtils.save(photo,file.getAbsolutePath());
                if (file.exists()) {
                    File old =tempPhoto;
                    if (old.exists()) {
                        old.delete();
                    }
                }
                return null;
            }
        }).execute("");
    }
    /**
     * 跳转到拍照界面
     */
    public void jumpCaptureActivity(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        /*获取当前系统的android版本号*/
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion < 24) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempPhoto));
            activity.startActivityForResult(intent, REQUEST_CODE_PHOTO_CAMERA);
        } else {
            tempPhoto = new File(Constant.PIC_PATH, System.currentTimeMillis() + "_temp.png.");
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, tempPhoto.getAbsolutePath());
            Uri uri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            activity.startActivityForResult(intent, REQUEST_CODE_PHOTO_CAMERA);
        }
    }

    /**
     * 拍照的dialog
     *
     * @param activity
     */
    public static Dialog showPhotosDaiglog(final Activity activity, final View.OnClickListener takePhotoClick,View.OnClickListener pickClick) {
        final Dialog dialog = new Dialog(activity, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog_take_photo);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().width = (int) (activity.getWindowManager().getDefaultDisplay()
                .getWidth() * 1.0f);
        File file = new File(Constant.PIC_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        dialog.findViewById(R.id.photo_select_photos).setOnClickListener(takePhotoClick);
        dialog.findViewById(R.id.photo_take_photos).setOnClickListener(pickClick);
        dialog.findViewById(R.id.photo_cancel).setOnClickListener(new DismissListener(dialog));
        return dialog;
    }
    static class DismissListener implements View.OnClickListener {
        Dialog dialog;
        public DismissListener(Dialog dialog){
            this.dialog=dialog;
        }
        @Override
        public void onClick(View v) {
            if(dialog!=null){
                dialog.dismiss();
            }
        }
    }
}
