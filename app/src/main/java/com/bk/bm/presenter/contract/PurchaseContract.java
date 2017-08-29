package com.bk.bm.presenter.contract;

import com.bk.bm.base.BaseAdapterContract;
import com.bk.bm.base.BaseContract;

/**
 * Created by choi on 2017. 8. 29..
 */

public interface PurchaseContract {
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

        void setAdapterModel(BaseAdapterContract.Model adapterModel);

        void setAdapterView(BaseAdapterContract.View adapterView);

        void getBookList();

        void removeBook(int uid);
    }
}
