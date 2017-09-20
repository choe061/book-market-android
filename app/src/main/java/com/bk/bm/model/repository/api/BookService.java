package com.bk.bm.model.repository.api;

import android.util.Log;

import com.bk.bm.model.domain.Book;
import com.bk.bm.model.domain.BookList;
import com.bk.bm.network.ApiCallback;
import com.bk.bm.network.HttpService;
import com.bk.bm.util.Constants;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by choi on 2017. 8. 29..
 */

public class BookService {
    private HttpService httpService;

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl("https://www.googleapis.com/")
                    .addConverterFactory(GsonConverterFactory.create());

    public BookService() {
    }

    public BookService(HttpService httpService) {
        this.httpService = httpService;
    }

    public <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    public void requestSearchBook(String bookId, ApiCallback<Response<BookList>> callback) {
        HttpService httpService = createService(HttpService.class);
        Call<BookList> books = httpService.getBooks(Constants.GOOGLE_BOOKS_API_KEY, bookId);
        books.enqueue(new Callback<BookList>() {
            @Override
            public void onResponse(Call<BookList> call, Response<BookList> response) {
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(Call<BookList> call, Throwable t) {
                Log.e("BookService onFailure", t.toString());
                callback.onError(t.getMessage());
            }
        });
    }

    public Observable<Response<Void>> uploadSaleBook(Book book) {
        Observable<Response<Void>> enrollBook = httpService.enrollSaleBook(book);
        return enrollBook.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
