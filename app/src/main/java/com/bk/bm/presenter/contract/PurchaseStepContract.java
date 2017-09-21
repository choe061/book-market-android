package com.bk.bm.presenter.contract;

import com.bk.bm.base.BaseAdapterContract;
import com.bk.bm.base.BaseContract;

/**
 * Created by choi on 2017. 8. 30..
 */

public interface PurchaseStepContract {
    interface View extends BaseContract.BaseView {
        @Override
        void showProgress();

        @Override
        void hideProgress();

        @Override
        void showToast(String title);

        void setSearch(String title);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        @Override
        void attachView(View view);

        @Override
        void detachView();

        void setAdapterModel(BaseAdapterContract.Model adapterModel);

        void setAdapterView(BaseAdapterContract.View adapterView);

        void requestSearchBook(String bookId);
    }
}
