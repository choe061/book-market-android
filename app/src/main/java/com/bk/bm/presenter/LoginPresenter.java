package com.bk.bm.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.content.res.AppCompatResources;
import android.util.Log;

import com.bk.bm.model.User;
import com.bk.bm.model.repository.api.UserApi;
import com.bk.bm.network.ApiCallback;
import com.bk.bm.network.HttpService;
import com.bk.bm.presenter.contract.LoginContract;
import com.bk.bm.util.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.JsonObject;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Response;

/**
 * Created by choi on 2017. 8. 19..
 */

public class LoginPresenter implements LoginContract.Presenter {

    private final String TAG = LoginPresenter.class.getName();
    private LoginContract.View view;
    private UserApi userApi;
    private SharedPreferences sharedPreferences;
    private CompositeDisposable mCompositeDisposable;

    private Context context;

    private SessionCallback mSessionCallback;
    private Session mSession;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseAuth mAuth;

    @Override
    public void attachView(LoginContract.View view, HttpService httpService, SharedPreferences sharedPreferences) {
        this.view = view;
        this.userApi = new UserApi(httpService);
        this.sharedPreferences = sharedPreferences;
        this.mCompositeDisposable = new CompositeDisposable();
        mSessionCallback = new SessionCallback();
        mSession = Session.getCurrentSession();
        mSession.addCallback(mSessionCallback);
    }

    @Override
    public void onStart(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void detachView() {
        mCompositeDisposable.dispose();
        mSession.removeCallback(mSessionCallback);
    }

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mSession.handleActivityResult(requestCode, resultCode, data)) {
            return false;
        }
        return true;
    }

    private class SessionCallback implements ISessionCallback {

        //세션 연결 성공 시
        @Override
        public void onSessionOpened() {
            String kakaoAccessToken = Session.getCurrentSession().getTokenInfo().getAccessToken();
            requestFirebaseAuthToken(kakaoAccessToken);

        }
        //세션 연결 실패 시
        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Log.e(TAG, "OpenFailed : "+String.valueOf(exception));
            }
            //로그인 화면을 다시 화면을 띄움
            view.redirectLoginActivity();
        }
    }

    private void requestFirebaseAuthToken(String token) {
        Disposable disposable = userApi.requestFirebaseAuthToken(token, new ApiCallback<Response<JsonObject>>() {
            @Override
            public void onSuccess(Response<JsonObject> model) {
                String firebase_token = String.valueOf(model.body().get("firebase_token"));
                signInWithCustomToken(firebase_token);
//                sendUserInfo(fcm_token);
            }

            @Override
            public void onError(String msg) {
                Log.e(TAG, String.valueOf(msg));
                view.showToast("다시 시도해주세요.");
                view.redirectLoginActivity();
            }
        });
        mCompositeDisposable.add(disposable);
    }

    private void sendUserInfo(String fcm_token) {
        User user = new User();
        user.setFcm_token(fcm_token);
        Disposable disposable = userApi.sendUserInfo(user, new ApiCallback<Response<JsonObject>>() {
            @Override
            public void onSuccess(Response<JsonObject> model) {
                Log.e(TAG, "sendUserInfo : "+model.body().get("message"));
                view.redirectMainActivity();
            }

            @Override
            public void onError(String msg) {
                //TODO fcm token을 보내지 못했을 경우 처리
                view.redirectMainActivity();
            }
        });
        mCompositeDisposable.add(disposable);
    }

    private void signInWithCustomToken(String token) {
        ExecutorService service = Executors.newCachedThreadPool();
        mAuth.signInWithCustomToken(token)
                .addOnCompleteListener(service, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@android.support.annotation.NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCustomToken:success");

                            FirebaseUser user = mAuth.getCurrentUser();
                            service.execute(new Runnable() {
                                @Override
                                public void run() {
                                    String loginToken = null;
                                    if (user != null) {
                                        loginToken = user.getToken(false).getResult().getToken();
                                    }
                                    Log.e(TAG, "service : "+loginToken);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(Constants.FIREBASE_USER_TOKEN, String.valueOf(loginToken));
                                    editor.commit();

                                    String firebaseMessageToken = sharedPreferences.getString(Constants.FIREBASE_MSG_TOKEN, null);
                                    sendUserInfo(firebaseMessageToken);
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                            view.showToast("Authentication failed.");

                        }
                    }
                });
    }

//    private Task<String> getFirebaseJwt(final String kakaoAccessToken) {
//        final TaskCompletionSource<String> source = new TaskCompletionSource<>();
//        post()
//
//    }

    String post(String url, String json) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(Constants.JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        okhttp3.Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
