package com.bk.bm.util;

import com.bk.bm.App;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.KakaoAdapter;

/**
 * Created by choi on 2017. 8. 19..
 */

public class KakaoSDKAdapter extends KakaoAdapter {

    @Override
    public IApplicationConfig getApplicationConfig() {
        IApplicationConfig config = () -> App.getGlobalApplicationContext();
        return config;
    }
}