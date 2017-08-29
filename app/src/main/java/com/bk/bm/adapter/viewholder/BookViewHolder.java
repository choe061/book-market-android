package com.bk.bm.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by choi on 2017. 8. 29..
 */

public class BookViewHolder extends RecyclerView.ViewHolder {


    public BookViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
