package com.bk.bm.network;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.bk.bm.util.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by choi on 2017. 8. 26..
 * kakao token -> server ---------------------> android : firebase login -> firebase user token
 *                 <-> firebase login token
 */

public class NetworkInterceptor implements Interceptor {

    private final String TAG = NetworkInterceptor.class.getName();
    private String mFirebaseUserToken;
    private SharedPreferences mPreferences;

    public NetworkInterceptor(Application application) {
        Context context = application.getApplicationContext();
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        refreshToken();
        mFirebaseUserToken = mPreferences.getString(Constants.FIREBASE_USER_TOKEN, null);
        Log.e(TAG, mFirebaseUserToken);
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Log.d(TAG+" Add Token to Header", mFirebaseUserToken);

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
            StringBuilder token = new StringBuilder();
            CountDownLatch countDownLatch = new CountDownLatch(1);
            user.getToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                @Override
                public void onComplete(@NonNull Task<GetTokenResult> task) {
                    if (task.isSuccessful()) {
                        Log.e(TAG, "isSuccessful : "+task.getResult().getToken());
                        token.append(task.getResult().getToken());
                        Log.d(TAG, String.valueOf(token) + ", " + task);
                        countDownLatch.countDown();
                    } else {
                        Log.e(TAG, "Token 실패");
                    }
                }
            });
            try {
                countDownLatch.await(3L, TimeUnit.SECONDS);
                Log.d(TAG, "refreshToken "+token);
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putString(Constants.FIREBASE_USER_TOKEN, String.valueOf(token));
                editor.commit();
            } catch (InterruptedException ie) {
                Log.e("refreshToken Exception", String.valueOf(ie));
            }
        }
    }
}
