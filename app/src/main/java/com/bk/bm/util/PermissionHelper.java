package com.bk.bm.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

/**
 * Created by choi on 2017. 9. 23..
 */

public class PermissionHelper {

    /**
     * 요청 AndroidOS의 버젼이 마쉬멜로우 이상 버젼이 아닐 경우
     */
    public static final int NOT_SUPPORT_VERSION = 2;

    /**
     * 요청 권한을 이미 가지고 있을 경우
     */
    public static final int ALREADY_GRANTED = -1;

    /**
     * 권한을 System에게 요청한 경우 * Activity의 onRequestPermissionsResult() 로 결과 리턴됨.
     * */
    public static final int REQUEST_PERMISSION = 0;

    private Activity mContext;
    private Builder mBuilder;

    public void setBuilder(Builder builder) {
        this.mBuilder = builder;
    }

    public PermissionHelper(Activity context) {
        this.mContext = context;
    }

    public int request(final String permission, final int requestCode, final OnPermissionDenyListener deny) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionCheck = ContextCompat.checkSelfPermission(mContext, permission);
            /**
             * 권한이 없는 경우
             */
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                if (mContext.shouldShowRequestPermissionRationale(permission)) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                    dialog.setTitle(mBuilder.getTitle())
                            .setMessage(mBuilder.getMessage())
                            .setPositiveButton(mBuilder.getPositiveButtonName(), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    /**
                                     * 여기서 권한 취득하고자하는 권한을 배열에 넣고 취득한다.
                                     */
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        mContext.requestPermissions(new String[]{permission}, requestCode);
                                    }
                                }
                            }).setNegativeButton(mBuilder.getNegativeButtonName(), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    /**
                                     * 권한 설정을 거절한 경우 적절한 행동을 취해야 한다.
                                     */
                                    deny.onClick(mContext);
                                }
                        }).create().show();
                    return REQUEST_PERMISSION;
                } else {
                    /**
                     * 처음 권한 요청을 하는 경우, 권한을 요청
                     */
                    mContext.requestPermissions(new String[]{permission}, requestCode);
                    return REQUEST_PERMISSION;
                }
            } else {
                /**
                 * 이미 권한을 가지고 있는 경우
                 */
                return ALREADY_GRANTED;
            }
        }
        return NOT_SUPPORT_VERSION;
    }

    public static class Builder {
        private PermissionHelper mHelper;

        public Builder(Activity context) {
            mHelper = new PermissionHelper(context);
        }

        private String title = "권한 요청";
        private String message = "기능의 사용을 위해 권한이 필요합니다.";
        private String positiveButtonName = "네";
        private String negativeButtonName = "아니요";

        public PermissionHelper getmHelper() {
            return mHelper;
        }

        public void setmHelper(PermissionHelper mHelper) {
            this.mHelper = mHelper;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getPositiveButtonName() {
            return positiveButtonName;
        }

        public void setPositiveButtonName(String positiveButtonName) {
            this.positiveButtonName = positiveButtonName;
        }

        public String getNegativeButtonName() {
            return negativeButtonName;
        }

        public void setNegativeButtonName(String negativeButtonName) {
            this.negativeButtonName = negativeButtonName;
        }

        public PermissionHelper create() {
            mHelper.setBuilder(this);
            return mHelper;
        }
    }

    public interface OnPermissionDenyListener {
        void onClick(Activity activity);
    }
}
