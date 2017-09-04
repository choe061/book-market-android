package com.bk.bm.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.bk.bm.R;
import com.bk.bm.adapter.BookImagePagerAdapter;
import com.bk.bm.base.BaseActivity;
import com.bk.bm.presenter.BookInfoPresenter;
import com.bk.bm.presenter.contract.BookInfoContract;

import butterknife.BindView;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by choi on 2017. 9. 4..
 */

public class BookInfoActivity extends BaseActivity implements BookInfoContract.View {

    private BookInfoContract.Presenter mPresenter;
//    private ArrayList<WeakReference<BookImageFragment>> imgFragments = new ArrayList<>();

    @BindView(R.id.viewpager) ViewPager mViewPager;
    @BindView(R.id.indicator) CircleIndicator mIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("제품정보");

        BookImagePagerAdapter adapter = new BookImagePagerAdapter(getLayoutInflater());
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2);
        mIndicator.setViewPager(mViewPager);

        mPresenter = new BookInfoPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_book_info;
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
    public void setPresenter(BookInfoContract.Presenter presenter) {

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

}
