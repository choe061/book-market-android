package com.bk.bm.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bk.bm.view.BoardFragment;
import com.bk.bm.view.PurchaseFragment;
import com.bk.bm.view.SaleFragment;

/**
 * Created by choi on 2017. 8. 19..
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs;

    public ViewPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = PurchaseFragment.newInstance();
                break;
            case 1:
                fragment = SaleFragment.newInstance();
                break;
            case 2:
                fragment = BoardFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
