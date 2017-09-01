package com.bk.bm.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.bk.bm.R;
import com.bk.bm.adapter.SearchBookRecyclerViewAdapter;
import com.bk.bm.base.BaseFragment;
import com.bk.bm.model.repository.api.BookService;
import com.bk.bm.presenter.PurchaseStepPresenter;
import com.bk.bm.presenter.contract.PurchaseStepContract;
import com.bk.bm.util.EventData;
import com.bk.bm.util.EventData.Book;
import com.bk.bm.util.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    public ArrayList<Fragment> getFragmentInstances() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(FirstStepFragment.newInstance());
        fragments.add(SecondStepFragment.newInstance());
        fragments.add(ThirdStepFragment.newInstance());
        fragments.add(FourthStepFragment.newInstance());
        fragments.add(FifthStepFragment.newInstance());
        return fragments;
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    public static void EventDataPost(Book book, Object o) {
        MessageEvent event = new MessageEvent(new EventData(book, o));
        EventBus.getDefault().post(event);
    }

    public static class FirstStepFragment extends BaseFragment implements PurchaseStepContract.View {

        private PurchaseStepContract.Presenter mPresenter;
        @BindView(R.id.search_book) EditText search;
        @BindView(R.id.search_book_result) RecyclerView mSearchBookRecyclerView;

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
            SearchBookRecyclerViewAdapter adapter = new SearchBookRecyclerViewAdapter(getContext());
            mSearchBookRecyclerView.setAdapter(adapter);
            mSearchBookRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            BookService bookService = new BookService();
            mPresenter = new PurchaseStepPresenter(bookService);
            mPresenter.attachView(this);
            mPresenter.setAdapterModel(adapter);
            mPresenter.setAdapterView(adapter);

            search.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN)
                            && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        String bookId = search.getText().toString();
                        mPresenter.requestSearchBook(bookId);
                        return true;
                    }
                    return false;
                }
            });
            return view;
        }

        @Override
        public void onResume() {
            super.onResume();

        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            mPresenter.detachView();
        }

        @Override
        public void setPresenter(PurchaseStepContract.Presenter presenter) {

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

        @Override
        public void setSearch(String title) {
            this.search.setText(title);
        }
    }

    public static class SecondStepFragment extends BaseFragment {

        @BindView(R.id.min_price) EditText minPrice;
        @BindView(R.id.max_price) EditText maxPrice;

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
            minPrice.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    EventDataPost(Book.MIN_PRICE, s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            maxPrice.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    EventDataPost(Book.MAX_PRICE, s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
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
