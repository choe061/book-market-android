package com.bk.bm.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bk.bm.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by choi on 2017. 8. 29..
 */

public class BookViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.book_img) ImageView bookImage;
    @BindView(R.id.book_layout) LinearLayout bookLayout;
    @BindView(R.id.book_match_count) TextView bookMatchCount;
    @BindView(R.id.book_name) TextView bookName;
    @BindView(R.id.book_enroll_date) TextView bookEnrollDate;
    @BindView(R.id.fix) Button fix;
    @BindView(R.id.remove) Button remove;

    public BookViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
