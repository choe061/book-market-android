package com.bk.bm.presenter.contract;

import android.content.Intent;

import com.bk.bm.base.BaseContract;

/**
 * Created by choi on 2017. 8. 19..
 */

public interface LoginContract {
    interface View extends BaseContract.BaseView<Presenter> {
        @Override
        void setPresenter(Presenter presenter);

        @Override
        void showProgress();

        @Override
        void hideProgress();

        @Override
        void showToast(String title);

        void redirectLoginActivity();

        void redirectMainActivity();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        @Override
        void attachView(View view);

        void onStart();

        @Override
        void detachView();

        boolean onActivityResult(int requestCode, int resultCode, Intent data);
    }

}
