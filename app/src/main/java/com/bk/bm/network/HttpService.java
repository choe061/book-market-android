package com.bk.bm.network;

import com.bk.bm.model.Book;
import com.bk.bm.model.BookList;
import com.bk.bm.model.User;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by choi on 2017. 8. 17..
 */

public interface HttpService {

    @FormUrlEncoded
    @POST("/v0/user")
    Observable<Response<JsonObject>> firebaseAuthToken(@Field("token") String token);

    @POST("/test")
    Observable<Response<JsonObject>> sendUserInfo(@Body User user);

    @GET("/books/v1/volumes")
    Call<BookList> getBooks(@Query("key") String key, @Query("q") String id);

}
