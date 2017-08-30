package com.bk.bm.presenter;

import android.util.Log;

import com.bk.bm.base.BaseAdapterContract;
import com.bk.bm.model.BookList;
import com.bk.bm.model.repository.api.BookService;
import com.bk.bm.network.ApiCallback;
import com.bk.bm.presenter.contract.PurchaseStepContract;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Response;

/**
 * Created by choi on 2017. 8. 30..
 */

public class PurchaseStepPresenter implements PurchaseStepContract.Presenter {

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
    }

    @Override
    public void requestSearchBook(String bookId) {
        mView.showProgress();
        mBookService.requestSearchBook(bookId, new ApiCallback<Response<BookList>>() {
            @Override
            public void onSuccess(Response<BookList> model) {
                Log.d(TAG, "requestSearchBook onSuccess : "+model);
                Log.d(TAG, "requestSearchBook onSuccess : "+String.valueOf(model.body()));
                Log.d(TAG, model.body().getItems().get(0).getVolumeInfo().getTitle());
                mView.hideProgress();
            }

            @Override
            public void onError(String msg) {
                Log.e(TAG, "requestSearchBook onError : "+msg);
                mView.hideProgress();
            }
        });
    }
}
