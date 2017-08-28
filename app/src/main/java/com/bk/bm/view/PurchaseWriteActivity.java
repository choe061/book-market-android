package com.bk.bm.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSeekBar;

import com.bk.bm.R;
import com.bk.bm.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by choi on 2017. 8. 28..
 */

public class PurchaseWriteActivity extends BaseActivity {

    @BindView(R.id.step) AppCompatSeekBar step;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("글쓰기");
        step.setPadding(0, 0, 0, 0);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_purchase_write;
    }
}
