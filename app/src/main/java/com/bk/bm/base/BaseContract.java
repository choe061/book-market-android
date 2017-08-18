package com.bk.bm.base;

/**
 * Created by choi on 2017. 8. 18..
 */

public interface BaseContract {

    interface BaseView<P extends BasePresenter> {
        void setPresenter(P presenter);

        void showProgress();

        void hideProgress();

        void showToast(String title);
    }

    interface BasePresenter<V extends BaseView> {
        void attachView(V view);

        void detachView();
    }
}
