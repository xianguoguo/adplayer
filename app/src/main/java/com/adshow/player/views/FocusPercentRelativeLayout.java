package com.adshow.player.views;

import android.content.Context;
import android.graphics.Canvas;
import android.support.percent.PercentRelativeLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.adshow.player.R;

public class FocusPercentRelativeLayout extends PercentRelativeLayout implements View.OnFocusChangeListener {

    private FocusPainter mBorderPainter;

    public FocusPercentRelativeLayout(Context context) {
        super(context);
    }

    public FocusPercentRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBorderPainter = new FocusPainter(this, R.drawable.focus);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setOnFocusChangeListener(this);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        mBorderPainter.draw(canvas);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Log.v("onFocusChange", v.getId() + " ==> Focus");
        Toast.makeText(this.getContext(), v.getId() + " ==> Focus", Toast.LENGTH_SHORT);
        if (hasFocus) {
            mBorderPainter.setView(v);
        }
    }

    public void initFocusView(View v) {
        mBorderPainter.setView(v);
    }
}
