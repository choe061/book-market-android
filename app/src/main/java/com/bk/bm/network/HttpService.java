package com.bk.bm.network;

import com.bk.bm.model.domain.Book;
import com.bk.bm.model.domain.BookList;
import com.bk.bm.model.domain.User;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by choi on 2017. 8. 17..
 */

public interface HttpService {

    /**
     * @param token 카카오 로그인 토큰
     * @return 커스텀 토큰
     */
    @FormUrlEncoded
    @POST("/v0/user")
    Observable<Response<JsonObject>> firebaseAuthToken(@Field("token") String token);

    /**
     *
     * @param user
     * @return
     */
    @POST("/test")
    Observable<Response<JsonObject>> sendUserInfo(@Body User user);

    /**
     * Google Books API
     * @param key API Key
     * @param id 검색할 책 이름 or ISBN Code
     * @return BookList
     */
    @GET("/books/v1/volumes?maxResults=40")
    Call<BookList> getBooks(@Query("key") String key, @Query("q") String id);

    /**
     * 사는 책 등록은 Filter로 수정
     * @param book
     * @return
     */
    @POST("/v0/book")
    Observable<Response<Void>> enrollPurchaseBook(@Body Book book);

    /**
     * 파는 책 등록
     * @param book
     * @return
     */
    @POST("/v0/book")
    Observable<Response<Void>> enrollSaleBook(@Body Book book);

}
