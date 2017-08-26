package com.bk.bm.presenter;

import android.content.SharedPreferences;

import com.bk.bm.model.repository.api.UserApi;
import com.bk.bm.network.HttpService;
import com.bk.bm.presenter.contract.MainContract;

/**
 * Created by choi on 2017. 8. 17..
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private UserApi userApi;

    @Override
    public void attachView(MainContract.View view, HttpService httpService, SharedPreferences sharedPreferences) {
        this.view = view;
    }

    @Override
    public void setApi(UserApi userApi) {
        this.userApi = userApi;
    }

    @Override
    public void detachView() {

    }
}
