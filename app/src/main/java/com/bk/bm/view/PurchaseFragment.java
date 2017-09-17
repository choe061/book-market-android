package com.bk.bm.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bk.bm.R;
import com.bk.bm.adapter.BookRecyclerViewAdapter;
import com.bk.bm.base.BaseFragment;
import com.bk.bm.model.domain.Book;
import com.bk.bm.model.repository.api.BookService;
import com.bk.bm.presenter.PurchasePresenter;
import com.bk.bm.presenter.contract.PurchaseContract;
import com.bk.bm.widget.OnDataListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by choi on 2017. 8. 19..
 */

public class PurchaseFragment extends BaseFragment implements PurchaseContract.View {

    @BindView(R.id.book_tv) TextView mBookTextView;
    @BindView(R.id.book_list) RecyclerView mBookRecyclerView;
    @BindView(R.id.fab_write) FloatingActionButton mFabWirte;

    private PurchaseContract.Presenter mPresenter;

    public static Fragment newInstance() {
        Fragment fragment = new PurchaseFragment();
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

        BookService bookService = new BookService();
        mPresenter = new PurchasePresenter(bookService);
        mPresenter.attachView(this);
        mPresenter.setAdapterModel(adapter);
        mPresenter.setAdapterView(adapter);
        return view;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_purchase;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
        mBookTextView.setText("사고싶은 책을 등록해보세요!\n췕84가 매칭시켜드립니다");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @OnClick({R.id.fab_write})
    public void startActivityToWrite() {
        startActivity(new Intent(getContext(), PurchaseWriteActivity.class));
    }

    @Override
    public void setPresenter(PurchaseContract.Presenter presenter) {

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
        Intent intent = new Intent(getContext(), BookMatchingActivity.class);
        Book testBook = new Book("1234", "1234", "빅피처", 10000, new ArrayList<>(), new ArrayList<>(), "good", "gg", new ArrayList<>());
        Log.e("BOOK", testBook.getTitle());
        intent.putExtra("book_uid", testBook); //제목과 매칭될 책 id만 넘길지 parcelable 구현해서 book을 넘길지
        startActivity(intent);
    }

}
