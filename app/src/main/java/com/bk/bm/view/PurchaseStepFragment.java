package com.bk.bm.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bk.bm.R;
import com.bk.bm.base.BaseFragment;
import com.bk.bm.util.EventData;
import com.bk.bm.util.EventData.Book;
import com.bk.bm.util.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by choi on 2017. 8. 28..
 */

public class PurchaseStepFragment extends FragmentPagerAdapter {

    private int numOfTabs;

    public PurchaseStepFragment(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = FirstStepFragment.newInstance();
                break;
            case 1:
                fragment = SecondStepFragment.newInstance();
                break;
            case 2:
                fragment = ThirdStepFragment.newInstance();
                break;
            case 3:
                fragment = FourthStepFragment.newInstance();
                break;
            case 4:
                fragment = FifthStepFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    public static void EventDataPost(Book book, Object o) {
        MessageEvent event = new MessageEvent(new EventData(book, o));
        EventBus.getDefault().post(event);
    }

    public static class FirstStepFragment extends BaseFragment {
        public static Fragment newInstance() {
            Fragment fragment = new FirstStepFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_purchase_first, container, false);
            ButterKnife.bind(this, view);

            return view;
        }
    }

    public static class SecondStepFragment extends BaseFragment {
        public static Fragment newInstance() {
            Fragment fragment = new SecondStepFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_purchase_second, container, false);
            ButterKnife.bind(this, view);
            return view;
        }
    }

    public static class ThirdStepFragment extends BaseFragment {

        @BindView(R.id.direct) AppCompatCheckBox directCheckBox;

        public static Fragment newInstance() {
            Fragment fragment = new ThirdStepFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_purchase_third, container, false);
            ButterKnife.bind(this, view);

            directCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    startActivity(new Intent(getContext(), AreaSelectActivity.class));
                    getActivity().overridePendingTransition(R.anim.area_page_up, R.anim.page_fix);
                }
            });
            return view;
        }
    }

    public static class FourthStepFragment extends BaseFragment {
        public static Fragment newInstance() {
            Fragment fragment = new FourthStepFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_purchase_fourth, container, false);
            ButterKnife.bind(this, view);
            return view;
        }
    }

    public static class FifthStepFragment extends BaseFragment {
        public static Fragment newInstance() {
            Fragment fragment = new FifthStepFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_purchase_fifth, container, false);
            ButterKnife.bind(this, view);
            return view;
        }
    }
}
