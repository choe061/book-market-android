package com.bk.bm.view.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bk.bm.App;
import com.bk.bm.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by choi on 2017. 8. 16..
 */

public abstract class BaseActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) protected Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        ButterKnife.bind(this);
        ((App) getApplication()).getAppComponent().inject(this);
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
}
