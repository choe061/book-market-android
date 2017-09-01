package com.bk.bm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bk.bm.R;
import com.bk.bm.adapter.viewholder.BookViewHolder;
import com.bk.bm.adapter.viewholder.SearchBookViewHolder;
import com.bk.bm.base.BaseAdapterContract;
import com.bk.bm.widget.OnBookClickListener;
import com.bk.bm.model.domain.BookList.BookInfo;

import java.util.ArrayList;

/**
 * Created by choi on 2017. 9. 1..
 */

public class SearchBookRecyclerViewAdapter extends RecyclerView.Adapter<SearchBookViewHolder>
        implements BaseAdapterContract.Model<BookInfo>, BaseAdapterContract.View {

    private ArrayList<BookInfo> mBooks = new ArrayList<>();
    private Context mContext;

    private OnBookClickListener onBookClick;

    public SearchBookRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public SearchBookViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_search_book_item, viewGroup, false);
        return new SearchBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchBookViewHolder searchBookViewHolder, int position) {
        BookInfo bookInfo = mBooks.get(position);
        searchBookViewHolder.title.setText(bookInfo.getVolumeInfo().getTitle());
        if (bookInfo.getVolumeInfo().getAuthors() != null) {
            searchBookViewHolder.author.setText(bookInfo.getVolumeInfo().getAuthors().get(0));
        } else {
            searchBookViewHolder.author.setText("작가 없음");
        }
        searchBookViewHolder.itemView.setTag(bookInfo);
        searchBookViewHolder.itemView.setOnClickListener(v -> {
            if (onBookClick != null) {
                onBookClick.onBookClick(v, position);
            }
        });
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
        notifyDataSetChanged();
    }

    @Override
    public void addItems(ArrayList<BookInfo> models) {
        mBooks = models;
    }

    @Override
    public BookInfo getItem(int position) {
        return mBooks.get(position);
    }

    @Override
    public void clear() {
        if (!mBooks.isEmpty()) {
            mBooks.clear();
        }
    }
}
