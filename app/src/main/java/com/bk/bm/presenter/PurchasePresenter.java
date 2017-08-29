package com.bk.bm.presenter;

import com.bk.bm.base.BaseAdapterContract;
import com.bk.bm.model.repository.api.BookService;
import com.bk.bm.presenter.contract.PurchaseContract;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by choi on 2017. 8. 29..
 */

public class PurchasePresenter implements PurchaseContract.Presenter {

    private PurchaseContract.View mView;
    private BookService mBookService;

    private CompositeDisposable mCompositeDisposable;
    private BaseAdapterContract.Model mAdapterModel;
    private BaseAdapterContract.View mAdapterView;

    public PurchasePresenter(BookService mBookService) {
        this.mBookService = mBookService;
    }

    @Override
    public void attachView(PurchaseContract.View view) {
        this.mView = view;
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void detachView() {
        mView = null;
        mCompositeDisposable.dispose();
    }

    @Override
    public void setAdapterModel(BaseAdapterContract.Model adapterModel) {
        this.mAdapterModel = adapterModel;
    }

    @Override
    public void setAdapterView(BaseAdapterContract.View adapterView) {
        this.mAdapterView = adapterView;
    }

    @Override
    public void getBookList() {

    }

    @Override
    public void removeBook(int uid) {

    }
}
