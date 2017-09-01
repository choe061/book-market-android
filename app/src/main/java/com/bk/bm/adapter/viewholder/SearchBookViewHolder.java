package com.bk.bm.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bk.bm.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by choi on 2017. 9. 1..
 */

public class SearchBookViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.book_title) public TextView title;
    @BindView(R.id.book_author) public TextView author;

    public SearchBookViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
