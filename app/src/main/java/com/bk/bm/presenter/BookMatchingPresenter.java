package com.bk.bm.presenter;

import android.view.View;

import com.bk.bm.base.BaseAdapterContract;
import com.bk.bm.model.domain.Book;
import com.bk.bm.model.repository.api.BookService;
import com.bk.bm.presenter.contract.BookMatchingContract;
import com.bk.bm.widget.OnBookClickListener;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by choi on 2017. 9. 4..
 */

public class BookMatchingPresenter implements BookMatchingContract.Presenter, OnBookClickListener {

    private BookService mBookService;

    private BookMatchingContract.View mView;
    private CompositeDisposable mCompositeDisposable;
    private BaseAdapterContract.Model mAdapterModel;
    private BaseAdapterContract.View mAdapterView;

    public BookMatchingPresenter(BookService mBookService) {
        this.mBookService = mBookService;
    }

    @Override
    public void attachView(BookMatchingContract.View view) {
        mView = view;
        mCompositeDisposable = new CompositeDisposable();
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
    public void onBookClick(View view, Object value) {
//        Book book = (Book) mAdapterModel.getItem((int) value);
        mView.startBookInfoActivity(new Book());
    }
}
