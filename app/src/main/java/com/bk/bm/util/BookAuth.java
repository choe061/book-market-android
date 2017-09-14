package com.bk.bm.util;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by choi on 2017. 8. 26..
 */

public class BookAuth {

    private static final String TAG = BookAuth.class.getName();

    private BookAuth() {}

    public static void signInFirebase(FirebaseAuth auth, String customToken,
                                      SharedPreferences preferences, SignInCallback callback) {
        ExecutorService service = Executors.newCachedThreadPool();
        auth.signInWithCustomToken(customToken)
                .addOnCompleteListener(service, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = auth.getCurrentUser();
                            service.execute(new Runnable() {
                                @Override
                                public void run() {
                                    String loginToken = user.getToken(false).getResult().getToken();
                                    Log.d(TAG, "signInWithCustomLoginToken : "+loginToken);

                                    FirebaseAuth mAuth;
                                    mAuth = FirebaseAuth.getInstance();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    StringBuilder token = new StringBuilder();
                                    CountDownLatch countDownLatch = new CountDownLatch(1);
                                    user.getToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                                            token.append(task.getResult().getToken());
                                            Log.d(TAG, String.valueOf(token));
                                            countDownLatch.countDown();
                                        }
                                    });
                                    try {
                                        countDownLatch.await(3L, TimeUnit.SECONDS);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString(Constants.FIREBASE_USER_TOKEN, String.valueOf(token));
                                        editor.commit();
                                    } catch (InterruptedException ie) {
                                        Log.e("refreshToken Exception", String.valueOf(ie));
                                    }

                                    callback.onSuccess(token.toString());
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                            callback.onError();
                        }
                    }
                });
    }

    public static void signInAnonymouslyFirebase(FirebaseAuth auth, SharedPreferences preferences,
                                                 SignInCallback callback) {
        ExecutorService service = Executors.newCachedThreadPool();
        auth.signInAnonymously()
                .addOnCompleteListener(service, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = auth.getCurrentUser();
                            String anonymousUserToken = user.getToken(false).getResult().getToken();
                            Log.d(TAG, "signInAnonymously:success, "+anonymousUserToken);

                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString(Constants.FIREBASE_USER_TOKEN, String.valueOf(anonymousUserToken));
                            editor.apply();

                            callback.onSuccess(anonymousUserToken);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            callback.onError();
                        }
                    }
                });
    }

    public interface SignInCallback {
        void onSuccess(String loginToken);

        void onError();
    }
}
