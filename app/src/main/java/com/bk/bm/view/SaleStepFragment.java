package com.bk.bm.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bk.bm.R;
import com.bk.bm.adapter.SearchBookRecyclerViewAdapter;
import com.bk.bm.base.BaseFragment;
import com.bk.bm.model.repository.api.BookService;
import com.bk.bm.presenter.SaleStepPresenter;
import com.bk.bm.presenter.contract.SaleStepContract;
import com.bk.bm.util.EventData;
import com.bk.bm.util.EventData.Book;
import com.bk.bm.util.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by choi on 2017. 9. 4..
 */

public class SaleStepFragment {
    public static ArrayList<Fragment> newInstances() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(FirstStepFragment.newInstance());
        fragments.add(SecondStepFragment.newInstance());
        fragments.add(ThirdStepFragment.newInstance());
        fragments.add(BookGalleryStepFragment.newInstance());
        fragments.add(FourthStepFragment.newInstance());
        fragments.add(FifthStepFragment.newInstance());
        return fragments;
    }

    public static void eventDataProvider(Book book, Object o) {
        MessageEvent event = new MessageEvent(new EventData(book, o));
        EventBus.getDefault().post(event);
    }

    public static class FirstStepFragment extends BaseFragment implements SaleStepContract.View {

        private SaleStepContract.Presenter mPresenter;

        @BindView(R.id.main_title) TextView mainTitle;
        @BindView(R.id.sub_title) TextView subTitle;
        @BindView(R.id.search_book) EditText search;
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
            mainTitle.setText("팔고 싶은 책 등록");
            subTitle.setText("팔고 싶은 책의 이름이나, 책 고유번호\nISBN(바코드번호)을 입력해주세요!");

            SearchBookRecyclerViewAdapter adapter = new SearchBookRecyclerViewAdapter(getContext());
            mSearchBookRecyclerView.setAdapter(adapter);
            mSearchBookRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            BookService bookService = new BookService();
            mPresenter = new SaleStepPresenter();
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
        public void setPresenter(SaleStepContract.Presenter presenter) {

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

        @BindView(R.id.price_title) TextView priceTitle;
        @BindView(R.id.min_price) EditText minPrice;
        @BindView(R.id.max_price) EditText maxPrice;
        @BindView(R.id.min_price_title) TextView minPriceTitle;
        @BindView(R.id.max_layout) LinearLayout maxLayout;

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
            priceTitle.setText("팔고 싶은 책의 가격을 설정해주세요");
            minPriceTitle.setText("판매 가격");
            maxLayout.setVisibility(View.GONE);

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

        @BindView(R.id.direct)
        AppCompatCheckBox directCheckBox;

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

    public static class BookGalleryStepFragment extends BaseFragment {

        @BindView(R.id.image1) ImageView image1;
        @BindView(R.id.image2) ImageView image2;
        @BindView(R.id.image3) ImageView image3;
        @BindView(R.id.image4) ImageView image4;
        @BindView(R.id.image5) ImageView image5;
        @BindView(R.id.image6) ImageView image6;

        public static Fragment newInstance() {
            Fragment fragment = new BookGalleryStepFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = super.onCreateView(inflater, container, savedInstanceState);

            return view;
        }

        @Override
        protected int getLayoutResource() {
            return R.layout.fragment_book_gallery;
        }

        @OnClick({R.id.image1, R.id.image2, R.id.image3, R.id.image4, R.id.image5, R.id.image6})
        public void onImageClick() {

//            shouldShowRequestPermissionRationale()
        }

        public void checkPermission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
                if(permissionCheck == PackageManager.PERMISSION_DENIED){
                    // 권한 없음
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {

                    }
                }else{
                    // 권한 있음
                }
            }
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

        @BindView(R.id.ok)
        Button ok;
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
            HashMap<Book, Object> bookInfo = ((SaleWriteActivity)getActivity()).getBookInfo();
        }
    }
}
