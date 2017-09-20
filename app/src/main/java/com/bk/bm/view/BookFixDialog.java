package com.bk.bm.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.bk.bm.R;
import com.bk.bm.widget.OnDataListener;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by choi on 2017. 9. 17..
 */

public class BookFixDialog extends DialogFragment {

    private OnDataListener mOnDataListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_book_fix, null);
        ButterKnife.bind(this, view);

        builder.setView(view);
        AlertDialog dialog = builder.create();

        dialog.show();
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            int dialogWidth = getResources().getDimensionPixelSize(R.dimen.dialog_width);
            int dialogHeight = LinearLayoutCompat.LayoutParams.WRAP_CONTENT;
            getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
        }
    }

    @OnClick(R.id.fix)
    public void onFixClick() {
        if (getDialog() != null) {
            mOnDataListener.onDataReceived(BookFixDialog.this);
            getDialog().dismiss();
        }
    }

    @OnClick(R.id.remove)
    public void onRemoveClick() {
        if (getDialog() != null) {
            mOnDataListener.onDataReceived(BookFixDialog.this);
            getDialog().dismiss();
        }
    }

    public void setOnDataListener(OnDataListener mOnDataListener) {
        this.mOnDataListener = mOnDataListener;
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
