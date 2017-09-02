package com.bk.bm.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bk.bm.R;
import com.bk.bm.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by choi on 2017. 8. 19..
 */

public class SaleFragment extends BaseFragment {

    @BindView(R.id.book_tv) TextView bookTextView;
    @BindView(R.id.write) Button write;

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
        return view;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_sale;
    }

    @Override
    public void onResume() {
        super.onResume();
        bookTextView.setText("팔고싶은 책을 등록해보세요!\n췕84가 매칭시켜드립니다");
    }
}
