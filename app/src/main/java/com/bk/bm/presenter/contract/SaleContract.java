package com.bk.bm.presenter.contract;

import com.bk.bm.base.BaseAdapterContract;
import com.bk.bm.base.BaseContract;
import com.bk.bm.model.domain.Book;

/**
 * Created by choi on 2017. 9. 23..
 */

public interface SaleContract {
    interface View extends BaseContract.BaseView {
        @Override
        void showProgress();

        @Override
        void hideProgress();

        @Override
        void showToast(String title);

        void startBookMatchingActivity(Book book);

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
