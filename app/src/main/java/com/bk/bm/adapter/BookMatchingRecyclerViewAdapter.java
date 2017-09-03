package com.bk.bm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bk.bm.R;
import com.bk.bm.adapter.viewholder.BookMatchingViewHolder;
import com.bk.bm.adapter.viewholder.BookViewHolder;
import com.bk.bm.base.BaseAdapterContract;
import com.bk.bm.model.domain.Book;
import com.bk.bm.widget.OnBookClickListener;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

/**
 * Created by choi on 2017. 9. 4..
 */

public class BookMatchingRecyclerViewAdapter extends RecyclerView.Adapter<BookMatchingViewHolder>
        implements BaseAdapterContract.Model<Book>, BaseAdapterContract.View {

    private ArrayList<Book> mBooks = new ArrayList<>();
    private RequestManager mRequestManager;
    private Context mContext;

    private OnBookClickListener onBookClick;

    public BookMatchingRecyclerViewAdapter(RequestManager mRequestManager, Context mContext) {
        this.mRequestManager = mRequestManager;
        this.mContext = mContext;
    }

    @Override
    public BookMatchingViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_book_matching_item, viewGroup, false);
        return new BookMatchingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookMatchingViewHolder viewHolder, int position) {

        viewHolder.itemView.setOnClickListener(v -> {
            if (onBookClick != null) {
                onBookClick.onBookClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 6; //mBooks.size();
    }

    @Override
    public void setOnClickListener(OnBookClickListener onBookClickListener) {
        this.onBookClick = onBookClickListener;
    }

    @Override
    public void notifyAdapter() {
        notifyDataSetChanged();
    }

    @Override
    public void addItems(ArrayList<Book> models) {
        this.mBooks = models;
    }

    @Override
    public Book getItem(int position) {
        return mBooks.get(position);
    }

    @Override
    public void clear() {
        if (!mBooks.isEmpty()) {
            mBooks.clear();
        }
    }
}
