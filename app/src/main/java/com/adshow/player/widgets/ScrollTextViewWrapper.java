package com.adshow.player.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.adshow.player.R;

import anylife.scrolltextview.ScrollTextView;

public class ScrollTextViewWrapper extends ScrollTextView {


    public ScrollTextViewWrapper(Context context) {
        this(context, null);
    }

    public ScrollTextViewWrapper(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollTextViewWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setId(View.generateViewId());
    }
}
