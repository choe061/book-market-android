package com.bk.bm.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bk.bm.base.BaseFragment;

/**
 * Created by choi on 2017. 8. 18..
 */

public class HomeFragment extends BaseFragment {

    public static Fragment newInstance() {
        Fragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
