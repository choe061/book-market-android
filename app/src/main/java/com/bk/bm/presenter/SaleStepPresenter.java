package com.bk.bm.presenter;

import android.util.Log;
import android.view.View;

import com.bk.bm.base.BaseAdapterContract;
import com.bk.bm.model.domain.Book;
import com.bk.bm.model.domain.BookList;
import com.bk.bm.model.repository.api.BookService;
import com.bk.bm.network.ApiCallback;
import com.bk.bm.presenter.contract.SaleStepContract;
import com.bk.bm.util.EventData;
import com.bk.bm.view.PurchaseStepFragment;
import com.bk.bm.view.SaleStepFragment;
import com.bk.bm.widget.OnBookClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Response;

/**
 * Created by choi on 2017. 9. 4..
 */

public class SaleStepPresenter implements SaleStepContract.Presenter, OnBookClickListener {

    private final String TAG = SaleStepPresenter.class.getName();
    private SaleStepContract.View mView;
    private BookService mBookService;

    private CompositeDisposable mCompositeDisposable;
    private BaseAdapterContract.Model mAdapterModel;
    private BaseAdapterContract.View mAdapterView;

    public SaleStepPresenter() {
    }

    public SaleStepPresenter(BookService mBookService) {
        this.mBookService = mBookService;
    }

    @Override
    public void attachView(SaleStepContract.View view) {
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

    @Override
    public void requestSearchBook(String bookId) {
        mView.showProgress();
        mBookService.requestSearchBook(bookId, new ApiCallback<Response<BookList>>() {
            @Override
            public void onSuccess(Response<BookList> model) {
                Log.d(TAG, "requestSearchBook onSuccess : "+model);
                Log.d(TAG, "requestSearchBook onSuccess : "+String.valueOf(model.body()));
                ArrayList<BookList.BookInfo> bookInfos = model.body().getItems();
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
    public void uploadSaleBook(HashMap<EventData.Book, Object> bookInfo) {
        mView.showProgress();
        Book book = new Book();
        book.setIsbn10(String.valueOf(bookInfo.get(EventData.Book.ISBN_10)));
        book.setIsbn13(String.valueOf(bookInfo.get(EventData.Book.ISBN_13)));
        book.setTitle(String.valueOf(bookInfo.get(EventData.Book.TITLE)));
        book.setPrice(Integer.parseInt(String.valueOf(bookInfo.get(EventData.Book.MIN_PRICE))));
//        List<String> images = new ArrayList<>();
//        Collections.copy(images, (ArrayList<String>)bookInfo.get(EventData.Book.IMAGE));
//        book.setImage((ArrayList) bookInfo.get(EventData.Book.IMAGE));
        mBookService.uploadSaleBook(book, new ApiCallback<Response<Void>>() {
            @Override
            public void onSuccess(Response<Void> model) {
                mView.hideProgress();
                Log.d(TAG, String.valueOf(model));
            }

            @Override
            public void onError(String msg) {
                mView.hideProgress();
                Log.e(TAG, msg);
            }
        });
    }

    @Override
    public void onBookClick(View view, Object value) {
        BookList.BookInfo bookInfo = (BookList.BookInfo) mAdapterModel.getItem((int)value);
        mView.setSearch(bookInfo.getVolumeInfo().getTitle());
        ArrayList<BookList.Identity> identities = bookInfo.getVolumeInfo().getIndustryIdentifiers();
        String isbn10 = null, isbn13 = null;
        for (int i=0; i<identities.size(); i++) {
            if (identities.get(i).getType().equals(EventData.Book.ISBN_10)) {
                isbn10 = identities.get(i).getIdentifier();
            } else if (identities.get(i).getType().equals(EventData.Book.ISBN_13)) {
                isbn13 = identities.get(i).getIdentifier();
            }
        }
        PurchaseStepFragment.eventDataProvider(EventData.Book.ISBN_10, isbn10);
        PurchaseStepFragment.eventDataProvider(EventData.Book.ISBN_13, isbn13);
        PurchaseStepFragment.eventDataProvider(EventData.Book.TITLE, bookInfo.getVolumeInfo().getTitle());
        PurchaseStepFragment.eventDataProvider(EventData.Book.IMAGE, bookInfo.getVolumeInfo().getImageLinks());
    }
}
