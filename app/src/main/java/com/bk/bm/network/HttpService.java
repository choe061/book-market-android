package com.bk.bm.network;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by choi on 2017. 8. 17..
 */

public interface HttpService {

    @GET("/oauth/authorize?client_id={app_key}&redirect_uri={redirect_uri}&response_type=code")
    Observable<Void> getCode(@Query("client_id") String clientId,
                             @Query("rediret_ur") String redirectUri,
                             @Query("response_type") String code);

}
