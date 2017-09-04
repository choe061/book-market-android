package com.bk.bm.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bk.bm.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by choi on 2017. 9. 4..
 */

public class BookImagePagerAdapter extends PagerAdapter {

    @BindView(R.id.book_image) ImageView bookImage;

    private LayoutInflater mInflater;
    private ArrayList<String> imageUrl;
    private RequestManager mRequestManager;

    public BookImagePagerAdapter(LayoutInflater inflater) {
        this.mInflater = inflater;
        this.mRequestManager = Glide.with(mInflater.getContext());
    }

    public void setImageUrl(ArrayList<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int getCount() {
        return 3; //imageUrl.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return (view == o);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mInflater.inflate(R.layout.viewpager_book_image, null);
        ButterKnife.bind(this, view);
//        requestManager.load(imageUrl.get(position)).into(bookImage);
        mRequestManager.load(R.drawable.test_image).into(bookImage);
        container.addView(view);
        return view;
    }
}
