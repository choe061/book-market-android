package com.bk.bm.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import com.bk.bm.R;

/**
 * Created by choi on 2017. 8. 19..
 */

public final class BookUtils {
    private static final String TAG = BookUtils.class.getName();

    //block create instance
    private BookUtils() {}

    public static ProgressDialog showProgress(Context context) {
        ProgressDialog progressDialog = ProgressDialog.show(context, null, null, false, true);
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow()
                    .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_loading);
        return progressDialog;
    }
}
