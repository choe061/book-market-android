package com.bk.bm.base;

import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 * Created by choi on 2017. 8. 28..
 */

public abstract class BasePresenter {
    @Inject
    protected SharedPreferences sharedPreferences;
}
