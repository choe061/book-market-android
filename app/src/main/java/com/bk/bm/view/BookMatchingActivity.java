package com.bk.bm.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.bk.bm.R;
import com.bk.bm.adapter.BookMatchingRecyclerViewAdapter;
import com.bk.bm.base.BaseActivity;
import com.bk.bm.model.domain.Book;
import com.bk.bm.model.repository.api.BookService;
import com.bk.bm.presenter.BookMatchingPresenter;
import com.bk.bm.presenter.contract.BookMatchingContract;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by choi on 2017. 9. 4..
 */

public class BookMatchingActivity extends BaseActivity implements BookMatchingContract.View {

    @BindView(R.id.book_matching_list) RecyclerView mBookRecyclerView;

    private BookMatchingContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Book book = intent.getExtras().getParcelable("book_uid");
        setTitle(book.getTitle());

        RequestManager requestManager = Glide.with(this);
        BookMatchingRecyclerViewAdapter adapter = new BookMatchingRecyclerViewAdapter(requestManager, this);
        mBookRecyclerView.setAdapter(adapter);
        mBookRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        BookService bookService = new BookService();
        mPresenter = new BookMatchingPresenter(bookService);
        mPresenter.attachView(this);
        mPresenter.setAdapterModel(adapter);
        mPresenter.setAdapterView(adapter);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_book_matching;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void setPresenter(BookMatchingContract.Presenter presenter) {

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
    public void showToast(String message) {
        super.showToast(message);
    }

    @Override
    public void startBookInfoActivity(Book book) {
        Intent intent = new Intent(this, BookInfoActivity.class);
        Book testBook = new Book("1234", "1234", "빅피처", 10000, new ArrayList<>(), "good", "gg", new ArrayList<>());
        intent.putExtra("book_uid", testBook);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.matching_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

}
