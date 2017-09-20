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
import android.widget.Button;
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
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by choi on 2017. 8. 28..
 */

public class PurchaseStepFragment {

    public static ArrayList<Fragment> newInstances() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(FirstStepFragment.newInstance());
        fragments.add(SecondStepFragment.newInstance());
        fragments.add(ThirdStepFragment.newInstance());
        fragments.add(FourthStepFragment.newInstance());
        fragments.add(FifthStepFragment.newInstance());
        return fragments;
    }

    public static void eventDataProvider(Book book, Object o) {
        MessageEvent event = new MessageEvent(new EventData(book, o));
        EventBus.getDefault().post(event);
    }

    public static class FirstStepFragment extends BaseFragment implements PurchaseStepContract.View {

        private PurchaseStepContract.Presenter mPresenter;
        @BindView(R.id.search_book) EditText mSearch;
        @BindView(R.id.search_book_result) RecyclerView mSearchBookRecyclerView;

        public static Fragment newInstance() {
            Fragment fragment = new FirstStepFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            View view = super.onCreateView(inflater, container, savedInstanceState);

            SearchBookRecyclerViewAdapter adapter = new SearchBookRecyclerViewAdapter(getContext());
            mSearchBookRecyclerView.setAdapter(adapter);
            mSearchBookRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            BookService bookService = new BookService();
            mPresenter = new PurchaseStepPresenter(bookService);
            mPresenter.attachView(this);
            mPresenter.setAdapterModel(adapter);
            mPresenter.setAdapterView(adapter);

            mSearch.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN)
                            && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        String bookId = mSearch.getText().toString();
                        mPresenter.requestSearchBook(bookId);
                        return true;
                    }
                    return false;
                }
            });
            return view;
        }

        @Override
        protected int getLayoutResource() {
            return R.layout.fragment_purchase_first;
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
            this.mSearch.setText(title);
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
            View view = super.onCreateView(inflater, container, savedInstanceState);

            minPrice.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    eventDataProvider(Book.MIN_PRICE, s);
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
                    eventDataProvider(Book.MAX_PRICE, s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            return view;
        }

        @Override
        protected int getLayoutResource() {
            return R.layout.fragment_purchase_second;
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
            View view = super.onCreateView(inflater, container, savedInstanceState);

            directCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    startActivity(new Intent(getContext(), AreaSelectActivity.class));
                    getActivity().overridePendingTransition(R.anim.area_page_up, R.anim.page_fix);
                }
            });
            return view;
        }

        @Override
        protected int getLayoutResource() {
            return R.layout.fragment_purchase_third;
        }
    }

    public static class FourthStepFragment extends BaseFragment {

        @BindView(R.id.comment) EditText comment;

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
            View view = super.onCreateView(inflater, container, savedInstanceState);
            comment.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    eventDataProvider(Book.COMMENT, s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            return view;
        }

        @Override
        protected int getLayoutResource() {
            return R.layout.fragment_purchase_fourth;
        }
    }

    public static class FifthStepFragment extends BaseFragment {

        @BindView(R.id.ok) Button ok;
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
            View view = super.onCreateView(inflater, container, savedInstanceState);
            return view;
        }

        @Override
        protected int getLayoutResource() {
            return R.layout.fragment_purchase_fifth;
        }

        @OnClick(R.id.ok)
        public void onOkClick() {
            HashMap<Book, Object> bookInfo = ((PurchaseWriteActivity)getActivity()).getBookInfo();
        }
    }
}
