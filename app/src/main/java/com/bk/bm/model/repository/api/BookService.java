package com.bk.bm.model.repository.api;

import com.bk.bm.model.domain.Book;
import com.bk.bm.model.domain.Content;
import com.bk.bm.network.HttpService;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import retrofit2.Response;

/**
 * Created by choi on 2017. 8. 29..
 */

public class BookService {
    private HttpService httpService;

    public BookService(HttpService httpService) {
        this.httpService = httpService;
    }

    public Observable<Response<Void>> uploadSaleBook(Book book) {
        Observable<Response<Void>> enrollBook = httpService.enrollSaleBook(book);
        return enrollBook.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Response<JsonObject>> uploadBookImage(MultipartBody.Part body) {
        Observable<Response<JsonObject>> uploadImage = httpService.enrollImage(body);
        return uploadImage.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Response<Content>> requestBookList() {
        Observable<Response<Content>> bookList = httpService.requestBookList();
        return bookList.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
