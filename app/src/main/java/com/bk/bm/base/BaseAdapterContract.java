package com.bk.bm.base;

import com.bk.bm.util.OnItemClickListener;

import java.util.ArrayList;

/**
 * Created by choi on 2017. 8. 18..
 */

public interface BaseAdapterContract {

    interface View {

        void setOnClickListener(OnItemClickListener onItemClickListener);

        void notifyAdapter();

    }

    interface Model<M> {

        void addItems(ArrayList<M> models);

        M getItem(int position);

        void clear();

    }
}
