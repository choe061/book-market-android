package com.bk.bm.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.bk.bm.R;
import com.bk.bm.base.BaseActivity;
import com.bk.bm.util.EventData.Book;
import com.bk.bm.util.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by choi on 2017. 9. 4..
 */

public class SaleWriteActivity extends BaseActivity {
    private final String TAG = SaleWriteActivity.class.getName();

    private int mNowPage = 0;
    private ArrayList<Fragment> mStepFragments = new ArrayList<>();
    private HashMap<Book, Object> mBookInfo = new HashMap<>();

    @BindView(R.id.step) AppCompatSeekBar mStep;
    @BindView(R.id.enroll) Button mEnroll;
    @BindView(R.id.detail) Button mDetail;
    @BindView(R.id.success) Button mSuccess;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("글쓰기");
        mStep.setPadding(0, 0, 0, 0);

        mStepFragments = SaleStepFragment.newInstances();
        startFragmentInstance(mStepFragments.get(0), 16);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_sale_write;
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEvent(MessageEvent event) {
        mBookInfo.put(event.eventData.getType(), event.eventData.getMessage());
        Log.e(TAG, String.valueOf(mBookInfo));
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
            int progressStep = (mNowPage + 1) * 16;
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
                    startFragmentInstance(mStepFragments.get(mNowPage), progressStep);
                    break;
                case 6:
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
            mStep.setProgress(progressStep, true);
        } else {
            mStep.setProgress(progressStep);
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

    public HashMap<Book, Object> getBookInfo() {
        return mBookInfo;
    }
}
