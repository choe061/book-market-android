package com.bk.bm.presenter;

import android.content.Intent;
import android.util.Log;

import com.bk.bm.presenter.contract.LoginContract;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by choi on 2017. 8. 19..
 */

public class LoginPresenter implements LoginContract.Presenter {

    private final String TAG = LoginPresenter.class.getName();
    private LoginContract.View view;

    private SessionCallback mSessionCallback;
    private Session mSession;

    @Override
    public void attachView(LoginContract.View view) {
        this.view = view;
        mSessionCallback = new SessionCallback();
        mSession = Session.getCurrentSession();
        mSession.addCallback(mSessionCallback);
    }

    @Override
    public void detachView() {
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
            view.redirectSignupActivity();
        }
        //세션 연결 실패 시
        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Log.e(TAG, String.valueOf(exception));
            }
            //로그인 화면을 다시 화면을 띄움
            view.redirectLoginActivity();
        }
    }
}
