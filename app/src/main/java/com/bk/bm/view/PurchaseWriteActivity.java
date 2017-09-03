package com.bk.bm.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.bk.bm.R;
import com.bk.bm.base.BaseActivity;
import com.bk.bm.util.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by choi on 2017. 8. 28..
 */

public class PurchaseWriteActivity extends BaseActivity {

    private int mNowPage = 0;
    private ArrayList<Fragment> mStepFragments = new ArrayList<>();

    @BindView(R.id.step) AppCompatSeekBar step;
    @BindView(R.id.enroll) Button enroll;
    @BindView(R.id.detail) Button detail;
    @BindView(R.id.success) Button success;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("글쓰기");
        step.setPadding(0, 0, 0, 0);

        mStepFragments = PurchaseStepFragment.getFragmentInstances();
        startFragmentInstance(mStepFragments.get(0), 20);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_purchase_write;
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEvent(MessageEvent event) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.next) {
            mNowPage++;
            int progressStep = (mNowPage + 1) * 20;
            switch (mNowPage) {
                case 1:
                    startFragmentInstance(mStepFragments.get(mNowPage), progressStep);
                    break;
                case 2:
                    startFragmentInstance(mStepFragments.get(mNowPage), progressStep);
                    break;
                case 3:
                    startFragmentInstance(mStepFragments.get(mNowPage), progressStep);
                    break;
                case 4:
                    startFragmentInstance(mStepFragments.get(mNowPage), progressStep);
                    break;
                case 5:
                    //완료
                    break;
            }
            return true;
        } else if (id == android.R.id.home) {
            this.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startFragmentInstance(Fragment fragment, int progressStep) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.container, fragment)
//                .addToBackStack(null)
                .commit();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            step.setProgress(progressStep, true);
        } else {
            step.setProgress(progressStep);
        }
    }

    @Override
    public void onBackPressed() {
        if (mNowPage == 0) {
            super.onBackPressed();
        } else {
            mNowPage--;
            int progressStep = (mNowPage + 1) * 20;
            startFragmentInstance(mStepFragments.get(mNowPage), progressStep);
        }
    }
}
