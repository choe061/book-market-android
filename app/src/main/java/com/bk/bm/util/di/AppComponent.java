package com.bk.bm.util.di;

import com.bk.bm.base.BaseFragment;
import com.bk.bm.base.BasePresenter;
import com.bk.bm.util.KakaoSDKAdapter;
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
    void inject(BaseFragment fragment);
    void inject(KakaoSDKAdapter adapter);
    void inject(BasePresenter presenter);
}
