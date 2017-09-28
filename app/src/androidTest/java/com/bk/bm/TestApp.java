package com.bk.bm;

import com.bk.bm.util.di.AppComponent;
import com.bk.bm.util.di.DaggerAppComponent;

/**
 * Created by choi on 2017. 9. 28..
 */

public class TestApp extends App {
    @Override
    public AppComponent getAppComponent() {
        return DaggerAppComponent.builder().build();
    }
}
