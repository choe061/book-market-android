package com.bk.bm.presenter;

import android.view.View;

import com.bk.bm.base.BaseAdapterContract;
import com.bk.bm.model.domain.Book;
import com.bk.bm.model.repository.api.BookService;
import com.bk.bm.model.repository.api.GoogleBooksApiService;
import com.bk.bm.presenter.contract.PurchaseContract;
import com.bk.bm.widget.OnBookClickListener;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by choi on 2017. 8. 29..
 */

public class PurchasePresenter implements PurchaseContract.Presenter, OnBookClickListener {

    private PurchaseContract.View mView;
    private GoogleBooksApiService mGoogleBooksApiService;

    private CompositeDisposable mCompositeDisposable;
    private BaseAdapterContract.Model mAdapterModel;
    private BaseAdapterContract.View mAdapterView;

    public PurchasePresenter(GoogleBooksApiService mGoogleBooksApiService) {
        this.mGoogleBooksApiService = mGoogleBooksApiService;
    }

    @Override
    public void attachView(PurchaseContract.View view) {
        this.mView = view;
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onResume() {
        mAdapterView.notifyAdapter();
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

    }

    @Override
    public void removeBook(int uid) {

    }

    @Override
    public void onBookClick(View view, Object value) {
//        Book book = (Book) mAdapterModel.getItem((int) value);
        mView.startBookMatchingActivity(new Book());
        //TODO 매칭 리스트 페이지로 book
    }
}
