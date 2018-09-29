package com.lu.library.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;

import com.lu.library.R;

/**
 * Created by lyw.
 *
 * @author: lyw
 * @package: com.lu.library.widget
 * @description: ${TODO}{ 类注释}
 * @date: 2018/9/29 0029
 */
public class DialogManager {


    public static ProgressDialog showWaitDialog(Context context, String msg) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        if (TextUtils.isEmpty(msg)) {
            msg = context.getString(R.string.loading);
        }
        progressDialog.setMessage(msg);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        return progressDialog;
    }
}
