package com.bk.bm.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by choi on 2017. 9. 4..
 */

public class BookMatchingViewHolder extends RecyclerView.ViewHolder {
    public BookMatchingViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
