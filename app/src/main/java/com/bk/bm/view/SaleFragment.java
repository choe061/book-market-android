package com.bk.bm.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bk.bm.R;
import com.bk.bm.adapter.BookRecyclerViewAdapter;
import com.bk.bm.base.BaseFragment;
import com.bk.bm.model.domain.Book;
import com.bk.bm.model.repository.api.BookService;
import com.bk.bm.presenter.SalePresenter;
import com.bk.bm.presenter.contract.SaleContract;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by choi on 2017. 8. 19..
 */

public class SaleFragment extends BaseFragment implements SaleContract.View {


    @BindView(R.id.book_tv) TextView mBookTextView;
    @BindView(R.id.book_list) RecyclerView mBookRecyclerView;
    @BindView(R.id.fab_write) FloatingActionButton mFabWirte;
//    @BindView(R.id.book_empty) RelativeLayout empty_layout;
//    @BindView(R.id.book_empty) CoordinatorLayout list_layout;

    private SaleContract.Presenter mPresenter;

    public static Fragment newInstance() {
        Fragment fragment = new SaleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        RequestManager requestManager = Glide.with(this);
        BookRecyclerViewAdapter adapter = new BookRecyclerViewAdapter(requestManager, getContext());
        mBookRecyclerView.setAdapter(adapter);
        mBookRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        BookService bookService = new BookService(httpService);
        mPresenter = new SalePresenter(bookService);

        mPresenter.attachView(this);
        mPresenter.setAdapterModel(adapter);
        mPresenter.setAdapterView(adapter);
        return view;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_sale;
    }

    @Override
    public void onResume() {
        super.onResume();
//        mPresenter.onResume();
        mBookTextView.setText("팔고싶은 책을 등록해보세요!\n췕84가 매칭시켜드립니다");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @OnClick({R.id.fab_write})
    public void startActivityToWrite() {
        startActivity(new Intent(getContext(), SaleWriteActivity.class));
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
    public void startBookMatchingActivity(Book book) {

    }
}
