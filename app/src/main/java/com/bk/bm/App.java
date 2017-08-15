package com.bk.bm;

import android.app.Application;

import com.bk.bm.util.di.AppComponent;
import com.bk.bm.util.di.DaggerAppComponent;
import com.bk.bm.util.di.modules.AppModule;
import com.bk.bm.util.di.modules.NetModule;

/**
 * Created by choi on 2017. 8. 16..
 */

public class App extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(""))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
