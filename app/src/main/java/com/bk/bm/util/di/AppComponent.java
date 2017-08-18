package com.bk.bm.util.di;

import com.bk.bm.util.di.modules.AppModule;
import com.bk.bm.util.di.modules.NetModule;
import com.bk.bm.base.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by choi on 2017. 8. 16..
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface AppComponent {
    void inject(BaseActivity activity);
}
