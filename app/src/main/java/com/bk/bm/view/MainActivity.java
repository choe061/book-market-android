package com.bk.bm.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bk.bm.R;
import com.bk.bm.adapter.ViewPagerAdapter;
import com.bk.bm.base.BaseActivity;
import com.bk.bm.presenter.contract.MainContract;

import butterknife.BindView;

public class MainActivity extends BaseActivity
        implements MainContract.View, NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = MainActivity.class.getName();

    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view) NavigationView mNavigationView;
    @BindView(R.id.main_tab_layout) TabLayout mTabLayout;
    @BindView(R.id.main_view_pager) ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("췕84");
        onLoginCheck(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);

        mTabLayout.addTab(mTabLayout.newTab().setText("사고 싶어요"));
        mTabLayout.addTab(mTabLayout.newTab().setText("팔고 싶어요"));
        mTabLayout.addTab(mTabLayout.newTab().setText("책 중고장터"));

        ViewPagerAdapter viewPagerAdapter =
                new ViewPagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Menu menu = mNavigationView.getMenu();
        SwitchCompat alarmAtOnce = (SwitchCompat) menu.findItem(R.id.alarm_at_once)
                .getActionView().findViewById(R.id.alarm);
        SwitchCompat alarmRealTime = (SwitchCompat) menu.findItem(R.id.alarm_real_time)
                .getActionView().findViewById(R.id.alarm);

        alarmAtOnce.setOnClickListener(v -> {
            Log.d(TAG, "at once");
        });
        alarmRealTime.setOnClickListener(v -> {
            Log.d(TAG, "real time");
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.logout:
                onLogout(this);
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void startActivityToWrite(View view) {
        Log.e(TAG, "awefawef");
        startActivity(new Intent(this, PurchaseWriteActivity.class));
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {

    }

    @Override
    public void showProgress() {
        super.showProgress();
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
    }

    @Override
    public void showToast(String title) {
        super.showToast(title);
    }
}
