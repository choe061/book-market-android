package com.bk.bm.presenter;

import android.util.Log;

import com.bk.bm.presenter.contract.KakaoSignupContract;
import com.google.firebase.iid.FirebaseInstanceId;
import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.AuthService;
import com.kakao.auth.ErrorCode;
import com.kakao.auth.network.response.AccessTokenInfoResponse;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

/**
 * Created by choi on 2017. 8. 19..
 */

public class KakaoSignupPresenter implements KakaoSignupContract.Presenter {
    private final String TAG = KakaoSignupPresenter.class.getName();
    private KakaoSignupContract.View view;

    @Override
    public void attachView(KakaoSignupContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {

    }

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
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
    }

    private void requestAccessTokenInfo() {
        AuthService.requestAccessTokenInfo(new ApiResponseCallback<AccessTokenInfoResponse>() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {

            }

            @Override
            public void onNotSignedUp() {

            }

            @Override
            public void onSuccess(AccessTokenInfoResponse result) {

            }
        });
    }
}
