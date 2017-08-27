package com.bk.bm.presenter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.bk.bm.base.BasePresenter;
import com.bk.bm.model.User;
import com.bk.bm.model.repository.api.UserService;
import com.bk.bm.network.ApiCallback;
import com.bk.bm.presenter.contract.LoginContract;
import com.bk.bm.util.Constants;
import com.bk.bm.util.BookAuth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonObject;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;

import java.io.IOException;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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
    private UserService userService;
    private SharedPreferences sharedPreferences;

    private CompositeDisposable mCompositeDisposable;
    private SessionCallback mSessionCallback;
    private Session mSession;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseAuth mAuth;

    public LoginPresenter(UserService userService, SharedPreferences sharedPreferences) {
        this.userService = userService;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void attachView(LoginContract.View view) {
        this.view = view;
        this.mCompositeDisposable = new CompositeDisposable();
        mSessionCallback = new SessionCallback();
        mSession = Session.getCurrentSession();
        mSession.addCallback(mSessionCallback);
    }

    @Override
    public void onStart() {
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void detachView() {
        mCompositeDisposable.dispose();
        mSession.removeCallback(mSessionCallback);
        view.hideProgress();
    }

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        view.showProgress();
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
            requestFirebaseJwt(kakaoAccessToken);
        }
        //세션 연결 실패 시
        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Log.e(TAG, "SessionOpenFailed : "+String.valueOf(exception));
            }
            view.hideProgress();
            view.redirectLoginActivity();
        }
    }

    private void requestFirebaseJwt(String token) {
        Disposable disposable = userService.requestFirebaseJwt(token, new ApiCallback<Response<JsonObject>>() {
            @Override
            public void onSuccess(Response<JsonObject> model) {
                String firebase_token = String.valueOf(model.body().get("firebase_token"));
                signInWithCustomToken(firebase_token);
            }

            @Override
            public void onError(String msg) {
                Log.e(TAG, "requestFirebaseAuthToken Error : "+String.valueOf(msg));
                view.showToast("다시 시도해주세요.");
                view.redirectLoginActivity();
            }
        });
        mCompositeDisposable.add(disposable);
    }

    private void signInWithCustomToken(String customToken) {
        BookAuth.signInFirebase(mAuth, customToken,
                sharedPreferences, new BookAuth.SignInCallback() {
            @Override
            public void onSuccess() {
                String firebaseMessageToken = sharedPreferences.getString(Constants.FIREBASE_MSG_TOKEN, null);
                updateFcmToken(firebaseMessageToken);
            }

            @Override
            public void onError() {
                view.showToast("Authentication failed. Firebase Sign in failed.");
            }
        });
//        ExecutorService service = Executors.newCachedThreadPool();
//        mAuth.signInWithCustomToken(token)
//                .addOnCompleteListener(service, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@android.support.annotation.NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCustomToken:success");
//
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            service.execute(new Runnable() {
//                                @Override
//                                public void run() {
//                                    String loginToken = user.getToken(false).getResult().getToken();
//                                    Log.e(TAG, "service : "+loginToken);
//                                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                                    editor.putString(Constants.FIREBASE_USER_TOKEN, String.valueOf(loginToken));
//                                    editor.commit();
//
//                                    String firebaseMessageToken = sharedPreferences.getString(Constants.FIREBASE_MSG_TOKEN, null);
//                                    sendUserInfo(firebaseMessageToken);
//                                }
//                            });
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCustomToken:failure", task.getException());
//                            view.showToast("Authentication failed.");
//
//                        }
//                    }
//                });
    }

    //TODO 이 메소드 전체가 MainActivity로 가도 될 듯
    private void updateFcmToken(String fcm_token) {
        User user = new User();
        user.setFcm_token(fcm_token);
        Disposable disposable = userService.sendUserInfo(user, new ApiCallback<Response<JsonObject>>() {
            @Override
            public void onSuccess(Response<JsonObject> model) {
                Log.d(TAG, "updateFcmToken Success : "+model.body().get("message"));
                view.redirectMainActivity();
            }

            @Override
            public void onError(String msg) {
                //TODO fcm token을 보내지 못했을 경우 처리
                Log.e(TAG, "updateFcmToken Error : "+ msg);
                view.redirectMainActivity();
            }
        });
        mCompositeDisposable.add(disposable);
    }

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
