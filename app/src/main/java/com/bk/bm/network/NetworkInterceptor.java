package com.bk.bm.network;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bk.bm.util.Constants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by choi on 2017. 8. 26..
 */

public class NetworkInterceptor implements Interceptor {

    private String mFirebaseUserToken;

    public NetworkInterceptor(Application application) {
        Context context = application.getApplicationContext();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        mFirebaseUserToken = preferences.getString(Constants.FIREBASE_USER_TOKEN, null);
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Log.d("Add Token to Header", mFirebaseUserToken);

        Request request = chain.request();
        Request newRequest;
        newRequest = request.newBuilder()
                .addHeader("mytoken", String.format("%s", mFirebaseUserToken))
                .build();
        return chain.proceed(newRequest);
    }
}
