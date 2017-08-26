package com.bk.bm.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.bk.bm.network.HttpService;
import com.bk.bm.presenter.contract.KakaoSignupContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.kakao.auth.ErrorCode;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by choi on 2017. 8. 19..
 */

public class KakaoSignupPresenter implements KakaoSignupContract.Presenter {
    private final String TAG = KakaoSignupPresenter.class.getName();

    private KakaoSignupContract.View view;
    private CompositeDisposable mCompositeDisposable;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseAuth mAuth;

    @Override
    public void attachView(KakaoSignupContract.View view, HttpService httpService, SharedPreferences sharedPreferences) {
        this.view = view;
        mCompositeDisposable = new CompositeDisposable();

        requestUserInfo();
        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            } else {
                // User is signed out
                Log.d(TAG, "onAuthStateChanged:signed_out");
            }
        };
    }

    @Override
    public void onStart() {
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    public void detachView() {
        mCompositeDisposable.dispose();
    }

    /**
     * Update UI based on Firebase's current user. Show Login Button if not logged in.
     */
    private void updateUI() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
//            binding.setCurrentUser(currentUser);
            if (currentUser.getPhotoUrl() != null) {

            }
        } else {

        }
    }

    //카카오 회원가입 정보 받아오기
    private void requestUserInfo() {
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Logger.e(errorResult.toString());
                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    view.finish();
                } else {
                    view.redirectLoginActivity();
                }
            }

            @Override
            public void onNotSignedUp() {
                view.showToast("카카오톡 회원 조회를 실패했습니다.");
            }

            @Override
            public void onSuccess(UserProfile result) {
                Logger.d(result.toString());
                requestSignup(result);
            }
        });
    }

    private void requestSignup(UserProfile profile) {
        long kakaoID = profile.getId();
        String kakaoEmail = profile.getEmail();
        Log.d(TAG, "ID : "+kakaoID+", Email : "+kakaoEmail+", Access Token : "+profile);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        String accessToken = Session.getCurrentSession().getTokenInfo().getAccessToken();
    }
}
