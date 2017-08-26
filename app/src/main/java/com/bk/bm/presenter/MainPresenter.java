package com.bk.bm.presenter;

import com.bk.bm.presenter.contract.MainContract;

/**
 * Created by choi on 2017. 8. 17..
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;

    @Override
    public void attachView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {

    }
}
