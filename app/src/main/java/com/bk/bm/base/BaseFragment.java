package com.bk.bm.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.bk.bm.util.BookUtils;

/**
 * Created by choi on 2017. 8. 18..
 */

public abstract class BaseFragment extends Fragment {

    BaseActivity mBaseActivity;
    private ProgressDialog mProgressDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            mBaseActivity = (BaseActivity) context;
        }
    }

    protected void showProgress() {
        hideProgress();
        mProgressDialog = BookUtils.showProgress(getContext());
    }

    protected void hideProgress() {
        mProgressDialog.cancel();
    }

    protected void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

}
