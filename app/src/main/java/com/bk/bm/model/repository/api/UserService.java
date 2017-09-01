package com.bk.bm.model.repository.api;

import com.bk.bm.model.domain.User;
import com.bk.bm.network.ApiCallback;
import com.bk.bm.network.HttpService;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by choi on 2017. 8. 18..
 */

public class UserService {
    private HttpService httpService;

    public UserService(HttpService httpService) {
        this.httpService = httpService;
    }

    public Disposable requestFirebaseJwt(String token, ApiCallback<Response<JsonObject>> callback) {
        Observable<Response<JsonObject>> firebaseToken = httpService.firebaseAuthToken(token);
        return firebaseToken.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> callback.onSuccess(response),
                        throwable -> callback.onError(throwable.getMessage()));
    }

    public Disposable sendUserInfo(User user, ApiCallback<Response<JsonObject>> callback) {
        Observable<Response<JsonObject>> sendUser = httpService.sendUserInfo(user);
        return sendUser.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> callback.onSuccess(response),
                        throwable -> callback.onError(throwable.getMessage()));
    }
}
