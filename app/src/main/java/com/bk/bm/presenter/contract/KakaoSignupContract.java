package com.bk.bm.presenter.contract;

import android.content.SharedPreferences;

import com.bk.bm.base.BaseContract;
import com.bk.bm.network.HttpService;

/**
 * Created by choi on 2017. 8. 19..
 */

public interface KakaoSignupContract {
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

        void finish();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        @Override
        void attachView(View view, HttpService httpService, SharedPreferences sharedPreferences);

        void onStart();

        void onStop();

        @Override
        void detachView();
    }
}
