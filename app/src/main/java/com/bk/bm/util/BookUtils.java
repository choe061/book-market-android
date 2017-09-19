package com.bk.bm.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

import com.bk.bm.R;

/**
 * Created by choi on 2017. 8. 19..
 */

public final class BookUtils {
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBackground(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = ContextCompat.getDrawable(activity, R.drawable.book_background);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(activity, android.R.color.transparent));
            window.setNavigationBarColor(ContextCompat.getColor(activity, android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }
}
