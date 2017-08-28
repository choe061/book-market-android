package com.bk.bm.view;

import android.content.Intent;
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
import butterknife.OnClick;

/**
 * Created by choi on 2017. 8. 19..
 */

public class PurchaseFragment extends BaseFragment {

    @BindView(R.id.book_tv) TextView bookTextView;
    @BindView(R.id.write) Button write;

    public static Fragment newInstance() {
        Fragment fragment = new PurchaseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchase, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        bookTextView.setText("사고싶은 책을 등록해보세요!\n췕84가 매칭시켜드립니다");
    }

    @OnClick(R.id.write)
    public void startActivityToWrite() {
        startActivity(new Intent(getContext(), PurchaseWriteActivity.class));
    }
}
