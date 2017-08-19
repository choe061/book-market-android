package com.bk.bm.base;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by choi on 2017. 8. 18..
 */

public abstract class BaseFragment extends Fragment {

    BaseActivity mBaseActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            mBaseActivity = (BaseActivity) context;
        }
    }

}
