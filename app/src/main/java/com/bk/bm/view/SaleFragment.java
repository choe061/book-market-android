package com.bk.bm.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bk.bm.R;
import com.bk.bm.base.BaseFragment;

/**
 * Created by choi on 2017. 8. 19..
 */

public class SaleFragment extends BaseFragment {

    public static Fragment newInstance() {
        Fragment fragment = new SaleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale, container, false);
        return view;
    }
}
