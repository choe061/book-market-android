package com.bk.bm.util;

import android.app.Application;

import com.bk.bm.App;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.KakaoAdapter;

import javax.inject.Inject;

/**
 * Created by choi on 2017. 8. 19..
 */

public class KakaoSDKAdapter extends KakaoAdapter {
    @Inject
    Application application;

    @Override
    public IApplicationConfig getApplicationConfig() {
        App.getAppComponent().inject(this);
        IApplicationConfig config = () -> application;
        return config;
    }
}