package com.bk.bm.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bk.bm.App;
import com.bk.bm.network.HttpService;
import com.bk.bm.util.BookUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import retrofit2.Retrofit;

/**
 * Created by choi on 2017. 8. 18..
 */

public abstract class BaseFragment extends Fragment {

    @Inject
    Retrofit retrofit;
    protected HttpService httpService;
    BaseActivity mBaseActivity;
    private ProgressDialog mProgressDialog;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            mBaseActivity = (BaseActivity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResource(), container, false);
        ButterKnife.bind(this, view);
        App.getAppComponent().inject(this);
        httpService = retrofit.create(HttpService.class);
        return view;
    }

    protected abstract @LayoutRes int getLayoutResource();

    protected void showProgress() {
        hideProgress();
        mProgressDialog = BookUtils.showProgress(getContext());
    }

    protected void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.cancel();
        }
    }

    protected void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

}
