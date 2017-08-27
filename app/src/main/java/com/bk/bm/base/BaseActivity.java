package com.bk.bm.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.bk.bm.App;
import com.bk.bm.R;
import com.bk.bm.network.HttpService;
import com.bk.bm.util.BookUtils;
import com.bk.bm.view.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.kakao.auth.Session;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

/**
 * Created by choi on 2017. 8. 16..
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    Retrofit retrofit;
    @Inject
    protected SharedPreferences sharedPreferences;
    protected HttpService httpService;
    private ProgressDialog mProgressDialog;

    @BindView(R.id.toolbar) protected Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        ButterKnife.bind(this);
        App.getAppComponent().inject(this);
        httpService = retrofit.create(HttpService.class);
        setDisplayHomeEnabled(true);
    }

    protected abstract @LayoutRes int getLayoutResource();

    public void setDisplayHomeEnabled(boolean enabled) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);
        }
    }

    public Toolbar getToolbar() {
        return this.toolbar;
    }

    @Override
    public void setTitle(CharSequence title) {
        toolbar.setTitle(title);
    }

    @Override
    public void setTitle(int titleId) {
        toolbar.setTitle(titleId);
    }

    protected void showProgress() {
        hideProgress();
        mProgressDialog = BookUtils.showProgress(this);
    }

    protected void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.cancel();
        }
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected final void onKakaoLoginCheck(Activity activity) {
        if (Session.getCurrentSession().isClosed()) {
            startActivity(new Intent(activity, LoginActivity.class));
            finish();
        }
    }

    protected final void onKakaoLogout(Activity activity) {
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(activity, LoginActivity.class));
            }
        });
    }
}
