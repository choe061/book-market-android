package com.bk.bm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bk.bm.R;
import com.bk.bm.adapter.viewholder.BookViewHolder;
import com.bk.bm.base.BaseAdapterContract;
import com.bk.bm.model.Book;
import com.bk.bm.widget.OnBookClickListener;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

/**
 * Created by choi on 2017. 8. 29..
 */

public class BookRecyclerViewAdapter extends RecyclerView.Adapter<BookViewHolder>
        implements BaseAdapterContract.Model<Book>, BaseAdapterContract.View {

    private ArrayList<Book> mBooks = new ArrayList<>();
    private RequestManager mRequestManager;
    private Context mContext;

    private OnBookClickListener onBookClick;

    public BookRecyclerViewAdapter(RequestManager mRequestManager, Context mContext) {
        this.mRequestManager = mRequestManager;
        this.mContext = mContext;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_book_item, viewGroup, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookViewHolder bookViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    @Override
    public void setOnClickListener(OnBookClickListener onBookClickListener) {
        this.onBookClick = onBookClickListener;
    }

    @Override
    public void notifyAdapter() {

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

    }
}
