package com.bk.bm;

import android.app.Application;

import com.bk.bm.util.Constants;
import com.bk.bm.util.KakaoSDKAdapter;
import com.bk.bm.util.di.AppComponent;
import com.bk.bm.util.di.DaggerAppComponent;
import com.bk.bm.util.di.modules.AppModule;
import com.bk.bm.util.di.modules.NetModule;
import com.kakao.auth.KakaoSDK;

/**
 * Created by choi on 2017. 8. 16..
 */

public class App extends Application {
    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(Constants.BASE_URL))
                .build();
        KakaoSDK.init(new KakaoSDKAdapter());
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
