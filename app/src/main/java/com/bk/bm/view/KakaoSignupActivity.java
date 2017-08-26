package com.bk.bm.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bk.bm.base.BaseActivity;
import com.bk.bm.presenter.KakaoSignupPresenter;
import com.bk.bm.presenter.contract.KakaoSignupContract;

/**
 * Created by choi on 2017. 8. 19..
 */

public class KakaoSignupActivity extends BaseActivity implements KakaoSignupContract.View {

    private KakaoSignupContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new KakaoSignupPresenter();
        mPresenter.attachView(this, httpService, sharedPreferences);
    }

    @Override
    protected int getLayoutResource() {
        return 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void setPresenter(KakaoSignupContract.Presenter presenter) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showToast(String title) {
        super.showToast(title);
    }

    @Override
    public void redirectLoginActivity() {
        final Intent intent = new Intent(this, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    @Override
    public void redirectMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
