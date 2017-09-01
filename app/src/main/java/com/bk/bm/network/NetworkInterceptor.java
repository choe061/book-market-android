package com.bk.bm.network;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bk.bm.util.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by choi on 2017. 8. 26..
 */

public class NetworkInterceptor implements Interceptor {

    private String mFirebaseUserToken;
    private SharedPreferences mPreferences;

    public NetworkInterceptor(Application application) {
        Context context = application.getApplicationContext();
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        refreshToken();
        mFirebaseUserToken = mPreferences.getString(Constants.FIREBASE_USER_TOKEN, null);
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

    private void refreshToken() {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            ExecutorService service = Executors.newCachedThreadPool();
            service.execute(() -> {
                String loginToken = user.getToken(false).getResult().getToken();
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putString(Constants.FIREBASE_USER_TOKEN, String.valueOf(loginToken));
                editor.commit();
            });
        }
    }
}
