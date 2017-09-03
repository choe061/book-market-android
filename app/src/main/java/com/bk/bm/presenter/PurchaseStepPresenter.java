package com.bk.bm.presenter;

import android.util.Log;
import android.view.View;

import com.bk.bm.base.BaseAdapterContract;
import com.bk.bm.model.domain.BookList;
import com.bk.bm.model.domain.BookList.BookInfo;
import com.bk.bm.model.repository.api.BookService;
import com.bk.bm.network.ApiCallback;
import com.bk.bm.presenter.contract.PurchaseStepContract;
import com.bk.bm.util.EventData;
import com.bk.bm.view.PurchaseStepFragment;
import com.bk.bm.widget.OnBookClickListener;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Response;

/**
 * Created by choi on 2017. 8. 30..
 */

public class PurchaseStepPresenter implements PurchaseStepContract.Presenter, OnBookClickListener {

    private final String TAG = PurchaseStepPresenter.class.getName();
    private PurchaseStepContract.View mView;
    private BookService mBookService;

    private CompositeDisposable mCompositeDisposable;
    private BaseAdapterContract.Model mAdapterModel;
    private BaseAdapterContract.View mAdapterView;

    public PurchaseStepPresenter(BookService mBookService) {
        this.mBookService = mBookService;
    }

    @Override
    public void attachView(PurchaseStepContract.View view) {
        this.mView = view;
        this.mCompositeDisposable = new CompositeDisposable();
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

    @SuppressWarnings("unchecked")
    @Override
    public void requestSearchBook(String bookId) {
        mView.showProgress();
        mBookService.requestSearchBook(bookId, new ApiCallback<Response<BookList>>() {
            @Override
            public void onSuccess(Response<BookList> model) {
                Log.d(TAG, "requestSearchBook onSuccess : "+model);
                Log.d(TAG, "requestSearchBook onSuccess : "+String.valueOf(model.body()));
                ArrayList<BookInfo> bookInfos = model.body().getItems();
                if (!bookInfos.isEmpty()) {
                    mAdapterModel.addItems(bookInfos);
                    mAdapterView.notifyAdapter();
                } else {
                    mView.showToast("검색된 책이 없습니다. 제목을 다시 입력해주세요.");
                }
                mView.hideProgress();
            }

            @Override
            public void onError(String msg) {
                Log.e(TAG, "requestSearchBook onError : "+msg);
                mView.showToast("검색된 책이 없습니다. 제목을 다시 입력해주세요.");
                mView.hideProgress();
            }
        });
    }

    @Override
    public void onBookClick(View view, Object value) {
        BookInfo bookInfo = (BookInfo) mAdapterModel.getItem((int)value);
        mView.setSearch(bookInfo.getVolumeInfo().getTitle());
        PurchaseStepFragment.eventDataProvider(EventData.Book.BOOK, bookInfo);
    }
}
