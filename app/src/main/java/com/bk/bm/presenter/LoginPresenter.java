package com.bk.bm.presenter;

import android.content.Intent;

import com.bk.bm.presenter.contract.LoginContract;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

/**
 * Created by choi on 2017. 8. 19..
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;

    private SessionCallback callback;
    private Session session;

    @Override
    public void attachView(LoginContract.View view) {
        this.view = view;
        callback = new SessionCallback();
        session = Session.getCurrentSession();
        session.addCallback(callback);
    }

    @Override
    public void detachView() {
        session.removeCallback(callback);
    }

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (session.handleActivityResult(requestCode, resultCode, data)) {
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
                Logger.e(exception);
            }
            //로그인 화면을 다시 화면을 띄움
            view.redirectLoginActivity();
        }
    }
}
