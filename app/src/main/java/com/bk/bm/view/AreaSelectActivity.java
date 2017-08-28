package com.bk.bm.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.bk.bm.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by choi on 2017. 8. 28..
 */

public class AreaSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_select);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.down)
    public void onPageDown() {
        onBackPressed();
    }

    @OnClick(R.id.save)
    public void onSaveButtonClick() {
        //TODO 데이터 전송
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.page_fix, R.anim.area_page_down);
    }
}
