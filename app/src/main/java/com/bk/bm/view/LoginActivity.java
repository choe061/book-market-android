package com.bk.bm.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bk.bm.R;
import com.bk.bm.base.BaseActivity;
import com.bk.bm.presenter.LoginPresenter;
import com.bk.bm.presenter.contract.LoginContract;

/**
 * Created by choi on 2017. 8. 19..
 */

public class LoginActivity extends BaseActivity implements LoginContract.View {

    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginPresenter = new LoginPresenter();
        mLoginPresenter.attachView(this, httpService, sharedPreferences);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!mLoginPresenter.onActivityResult(requestCode, resultCode, data)) {
            return ;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLoginPresenter.onStart(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.detachView();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {

    }

    @Override
    public void showProgress() {
        super.showProgress();
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
    }

    @Override
    public void showToast(String title) {
        super.showToast(title);
    }

    @Override
    public void redirectLoginActivity() {
        getLayoutResource();
    }

    @Override
    public void redirectMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
