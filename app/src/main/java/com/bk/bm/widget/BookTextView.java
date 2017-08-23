package com.bk.bm.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;

import com.bk.bm.R;

/**
 * Created by choi on 2017. 8. 23..
 */

public class BookTextView extends AppCompatTextView {

    public BookTextView(Context context) {
        super(context);
    }

    public BookTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BookTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text.toString().replace(" ", "\u00A0"), type);
    }

    public void setCustomText(CharSequence text, int start, int end) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(text);
        sb.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.colorPrimary)),
                start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        super.append(sb);
    }
}
