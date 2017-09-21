package com.bk.bm.presenter.contract;

import com.bk.bm.base.BaseContract;

/**
 * Created by choi on 2017. 8. 17..
 */

public interface MainContract {

    interface View extends BaseContract.BaseView {

        @Override
        void showProgress();

        @Override
        void hideProgress();

        @Override
        void showToast(String title);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        @Override
        void attachView(View view);

        @Override
        void detachView();
    }
}
