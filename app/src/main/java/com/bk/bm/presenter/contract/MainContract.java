package com.bk.bm.presenter.contract;

import com.bk.bm.base.BaseContract;
import com.bk.bm.model.repository.api.UserApi;

/**
 * Created by choi on 2017. 8. 17..
 */

public interface MainContract {

    interface View extends BaseContract.BaseView<Presenter> {

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

        void setApi(UserApi userApi);

        @Override
        void detachView();
    }
}
