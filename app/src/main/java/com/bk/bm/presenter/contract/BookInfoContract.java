package com.bk.bm.presenter.contract;

import com.bk.bm.base.BaseContract;

/**
 * Created by choi on 2017. 9. 4..
 */

public interface BookInfoContract {
    interface View extends BaseContract.BaseView<Presenter> {
        @Override
        void setPresenter(Presenter presenter);

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

        void onResume();

        @Override
        void detachView();
    }
}
