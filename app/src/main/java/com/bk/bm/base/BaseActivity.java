package com.bk.bm.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bk.bm.App;
import com.bk.bm.R;
import com.bk.bm.network.HttpService;
import com.bk.bm.util.BookUtils;
import com.bk.bm.util.exception.ExceptionHandler;
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
    @BindView(R.id.toolbar_title) TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        BookUtils.setStatusBackground(this);
        super.onCreate(savedInstanceState);
//        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(getLayoutResource());
        ButterKnife.bind(this);
        ((App)getApplication()).getAppComponent().inject(this);
        httpService = retrofit.create(HttpService.class);
        setDisplayHomeEnabled(true);
    }

    protected abstract @LayoutRes int getLayoutResource();

    public void setDisplayHomeEnabled(boolean enabled) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    public Toolbar getToolbar() {
        return this.toolbar;
    }

    @Override
    public void setTitle(CharSequence title) {
//        getSupportActionBar().setTitle(title);
        this.title.setText(title);
    }

    @Override
    public void setTitle(int titleId) {
//        getSupportActionBar().setTitle(titleId);
        this.title.setText(titleId);
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

    protected final void onLoginCheck(Activity activity) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            if (Session.getCurrentSession().isClosed()) {
                startActivity(new Intent(activity, LoginActivity.class));
                finish();
            }
        }
    }

    protected final void onLogout(Activity activity) {
        showProgress();
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                Session.getCurrentSession().close();
                FirebaseAuth.getInstance().signOut();
                hideProgress();
                startActivity(new Intent(activity, LoginActivity.class));
            }
        });
    }
}
