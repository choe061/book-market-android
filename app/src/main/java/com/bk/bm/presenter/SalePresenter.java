package com.bk.bm.presenter;

import android.util.Log;
import android.view.View;

import com.bk.bm.base.BaseAdapterContract;
import com.bk.bm.model.repository.api.BookService;
import com.bk.bm.presenter.contract.PurchaseContract;
import com.bk.bm.presenter.contract.SaleContract;
import com.bk.bm.widget.OnBookClickListener;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by choi on 2017. 9. 23..
 */

public class SalePresenter implements SaleContract.Presenter, OnBookClickListener {

    private final String TAG = SalePresenter.class.getName();
    private SaleContract.View mView;
    private BookService mBookService;

    private CompositeDisposable mCompositeDisposable;
    private BaseAdapterContract.Model mAdapterModel;
    private BaseAdapterContract.View mAdapterView;

    public SalePresenter(BookService mBookService) {
        this.mBookService = mBookService;
    }

    @Override
    public void attachView(SaleContract.View view) {
        mView = view;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onResume() {
        getBookList();
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
        this.mAdapterView.setOnClickListener(this);
    }

    @Override
    public void getBookList() {
        Disposable disposable = mBookService.requestBookList()
                .subscribe(response -> {
                    mAdapterModel.addItems(response.body().getBooks());
                    mAdapterView.notifyAdapter();
                    Log.d(TAG, String.valueOf(response));
                }, throwable -> {
                    Log.e(TAG, throwable.getMessage());
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void removeBook(int uid) {

    }

    @Override
    public void onBookClick(View view, Object value) {

    }
}
