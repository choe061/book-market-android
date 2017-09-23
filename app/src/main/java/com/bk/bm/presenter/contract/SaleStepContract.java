package com.bk.bm.presenter.contract;

import com.bk.bm.base.BaseAdapterContract;
import com.bk.bm.base.BaseContract;
import com.bk.bm.util.EventData;

import java.io.File;
import java.util.HashMap;

/**
 * Created by choi on 2017. 9. 4..
 */

public interface SaleStepContract {
    interface View extends BaseContract.BaseView {
        @Override
        void showProgress();

        @Override
        void hideProgress();

        @Override
        void showToast(String title);

        void setSearch(String title);

        void redirectMainActivity();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        @Override
        void attachView(View view);

        @Override
        void detachView();

        void setAdapterModel(BaseAdapterContract.Model adapterModel);

        void setAdapterView(BaseAdapterContract.View adapterView);

        void requestSearchBook(String bookId);

        void uploadSaleBook(HashMap<EventData.Book, Object> bookInfo);

        void uploadBookImage(File file);
    }
}
