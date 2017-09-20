package com.bk.bm.adapter.viewholder;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bk.bm.R;
import com.bk.bm.view.BookFixDialog;
import com.bk.bm.widget.OnDataListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by choi on 2017. 8. 29..
 */

public class BookViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.book_img_blur) public ImageView bookImageBlur;
    @BindView(R.id.book_img) public ImageView bookImage;
    @BindView(R.id.book_layout) public RelativeLayout bookLayout;
    @BindView(R.id.book_move_vert) public ImageButton bookMoveVert;
    @BindView(R.id.book_match_count) public TextView bookMatchCount;
    @BindView(R.id.book_name) public TextView bookName;
    @BindView(R.id.book_enroll_date) public TextView bookEnrollDate;

    private FragmentManager mFragmentManager;

    public BookViewHolder(View itemView, FragmentManager fragmentManager) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mFragmentManager = fragmentManager;
    }

    @OnClick(R.id.book_move_vert)
    public void onMoveVertClick() {
        BookFixDialog dialog = new BookFixDialog();
        dialog.show(mFragmentManager, "BOOK_FIX");
        dialog.setOnDataListener(new OnDataListener() {
            @Override
            public void onDataReceived(Object object) {

            }
        });
    }
}
