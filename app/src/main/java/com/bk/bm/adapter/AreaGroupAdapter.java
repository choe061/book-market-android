package com.bk.bm.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ToggleButton;

import com.bk.bm.R;

import java.util.ArrayList;

/**
 * Created by choi on 2017. 9. 27..
 */

public class AreaGroupAdapter extends BaseAdapter {

    private Context mContext;
    private String[] mArea;
    private ArrayList<String> mSelected = new ArrayList<>();

    public AreaGroupAdapter(Context context) {
        this.mContext = context;
        mArea = mContext.getResources().getStringArray(R.array.area);
    }

    @Override
    public int getCount() {
        return mArea.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // create a new Button for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ToggleButton toggleButton;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            toggleButton = new ToggleButton(mContext);
            toggleButton.setLayoutParams(new GridView.LayoutParams(90, 70));
            toggleButton.setPadding(3, 3, 3, 3);
        } else {
            toggleButton = (ToggleButton) convertView;
        }
        toggleButton.setText(mArea[position]);
        return toggleButton;
    }
}
