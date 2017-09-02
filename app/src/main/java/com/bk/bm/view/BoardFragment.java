package com.bk.bm.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bk.bm.R;
import com.bk.bm.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by choi on 2017. 8. 19..
 */

public class BoardFragment extends BaseFragment {
    private final String TAG = BoardFragment.class.getName();

    private Animation mFabOpen, mFabClose, mFabRotateForward, mFabRotateBackward;
    private boolean isFabOpen = false;

    @BindView(R.id.fab_main) FloatingActionButton mFabMain;
    @BindView(R.id.fab_purchase) FloatingActionButton mFabPurchase;
    @BindView(R.id.fab_sale) FloatingActionButton mFabSale;

    public static Fragment newInstance() {
        Fragment fragment = new BoardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mFabOpen = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        mFabClose = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
        mFabRotateForward = AnimationUtils.loadAnimation(getContext(), R.anim.fab_rotate_forward);
        mFabRotateBackward = AnimationUtils.loadAnimation(getContext(), R.anim.fab_rotate_backward);
        return view;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_board;
    }

    @OnClick(R.id.fab_main)
    public void onClickFab() {
        changeFabState();
    }

    @OnClick(R.id.fab_purchase)
    public void onClickFabPurchase() {
        changeFabState();
    }

    @OnClick(R.id.fab_sale)
    public void onClickFabSale() {
        changeFabState();
    }

    private void changeFabState() {
        if (!isFabOpen) {
            mFabMain.startAnimation(mFabRotateForward);
            mFabPurchase.startAnimation(mFabOpen);
            mFabSale.startAnimation(mFabOpen);
            mFabPurchase.setClickable(true);
            mFabSale.setClickable(true);
            isFabOpen = true;
        } else {
            mFabMain.startAnimation(mFabRotateBackward);
            mFabPurchase.startAnimation(mFabClose);
            mFabSale.startAnimation(mFabClose);
            mFabPurchase.setClickable(false);
            mFabSale.setClickable(false);
            isFabOpen = false;
        }
    }
}
