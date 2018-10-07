package com.adshow.player.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class TextViewWrapper extends AppCompatTextView {
    public TextViewWrapper(Context context) {
        this(context, null);
    }

    public TextViewWrapper(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextViewWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
