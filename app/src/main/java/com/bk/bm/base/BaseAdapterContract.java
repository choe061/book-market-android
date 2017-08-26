package com.bk.bm.base;

import com.bk.bm.widget.OnBookClickListener;

import java.util.ArrayList;

/**
 * Created by choi on 2017. 8. 18..
 * RecyclerView Adapter에서 공통적으로 사용되는 Contract interface
 */

public interface BaseAdapterContract {

    interface View {

        void setOnClickListener(OnBookClickListener onBookClickListener);

        void notifyAdapter();

    }

    interface Model<M> {

        void addItems(ArrayList<M> models);

        M getItem(int position);

        void clear();

    }
}
