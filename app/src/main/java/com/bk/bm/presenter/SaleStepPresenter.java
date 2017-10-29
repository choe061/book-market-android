package com.bk.bm.presenter;

import android.util.Log;
import android.view.View;

import com.bk.bm.base.BaseAdapterContract;
import com.bk.bm.model.domain.Book;
import com.bk.bm.model.domain.BookList;
import com.bk.bm.model.repository.api.BookService;
import com.bk.bm.model.repository.api.GoogleBooksApiService;
import com.bk.bm.network.ApiCallback;
import com.bk.bm.presenter.contract.SaleStepContract;
import com.bk.bm.util.EventData;
import com.bk.bm.view.PurchaseStepFragment;
import com.bk.bm.view.SaleStepFragment;
import com.bk.bm.widget.OnBookClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

/**
 * Created by choi on 2017. 9. 4..
 */

public class SaleStepPresenter implements SaleStepContract.Presenter, OnBookClickListener {

    private final String TAG = SaleStepPresenter.class.getName();
    private SaleStepContract.View mView;
    private BookService mBookService;
    private GoogleBooksApiService mGoogleBooksApiService;

    private CompositeDisposable mCompositeDisposable;
    private BaseAdapterContract.Model mAdapterModel;
    private BaseAdapterContract.View mAdapterView;

    public SaleStepPresenter(BookService bookService) {
        this.mBookService = bookService;
    }

    public SaleStepPresenter(GoogleBooksApiService googleBooksApiService) {
        this.mGoogleBooksApiService = googleBooksApiService;
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
        mGoogleBooksApiService.requestSearchBook(bookId, new ApiCallback<Response<BookList>>() {
            @Override
            public void onSuccess(Response<BookList> model) {
                Log.d(TAG, "requestSearchBook http response : "+model);
                Log.d(TAG, "requestSearchBook onSuccess : "+String.valueOf(model.body()));
                ArrayList<BookList.BookInfo> bookInfos = model.body().getItems();
                if (!bookInfos.isEmpty()) {
                    mAdapterModel.addItems(bookInfos);
                    mAdapterView.notifyAdapter();

                    String[] isbns = getIsbnCodes(bookInfos.get(0).getVolumeInfo().getIndustryIdentifiers());
                    publishData(isbns[0], isbns[1],
                            bookInfos.get(0).getVolumeInfo().getTitle(),
                            bookInfos.get(0).getVolumeInfo().getImageLinks());
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
        Disposable disposable = mBookService.uploadSaleBook(book)
                .subscribe(response -> {
                    mView.hideProgress();
                    mView.redirectMainActivity();
                    Log.d(TAG, String.valueOf(response));
                }, throwable -> {
                    mView.hideProgress();
                    Log.e(TAG, throwable.getMessage());
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void uploadBookImage(File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        Disposable disposable = mBookService.uploadBookImage(body)
                .subscribe(response -> {
                    Log.d(TAG, String.valueOf(response));
                }, throwable -> {
                    Log.e(TAG, throwable.getMessage());
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onBookClick(View view, Object value) {
        BookList.BookInfo bookInfo = (BookList.BookInfo) mAdapterModel.getItem((int)value);

        String[] isbns = getIsbnCodes(bookInfo.getVolumeInfo().getIndustryIdentifiers());
        publishData(isbns[0], isbns[1],
                bookInfo.getVolumeInfo().getTitle(), bookInfo.getVolumeInfo().getImageLinks());
    }

    private String[] getIsbnCodes(ArrayList<BookList.Identity> identities) {
        String[] isbns = new String[2];
        Arrays.fill(isbns, null);
        for (int i=0; i<identities.size(); i++) {
            if (identities.get(i).getType().equals(String.valueOf(EventData.Book.ISBN_10))) {
                isbns[0] = identities.get(i).getIdentifier();
            } else if (identities.get(i).getType().equals(String.valueOf(EventData.Book.ISBN_13))) {
                isbns[1] = identities.get(i).getIdentifier();
            }
        }
        return isbns;
    }

    private void publishData(String isbn10, String isbn13, String title, BookList.BookImage bookImage) {
        mView.setSearch(title);

        PurchaseStepFragment.eventDataProvider(EventData.Book.ISBN_10, isbn10);
        PurchaseStepFragment.eventDataProvider(EventData.Book.ISBN_13, isbn13);
        PurchaseStepFragment.eventDataProvider(EventData.Book.TITLE, title);
        PurchaseStepFragment.eventDataProvider(EventData.Book.IMAGE, bookImage);
    }


}
