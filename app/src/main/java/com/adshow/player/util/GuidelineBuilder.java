package com.adshow.player.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.Guideline;
import android.support.v7.widget.AppCompatImageView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.adshow.player.activitys.fullscreen.PlayerActivity;

public class GuidelineBuilder {

    private Context context;

    private PlayerActivity activity;

    private Guideline guidelineTop;

    private Guideline guidelineBottom;

    private Guideline guidelineLeft;

    private Guideline guidelineRight;

    private ConstraintLayout playerLayout;


    public GuidelineBuilder(PlayerActivity activity, Context context, ConstraintLayout playerLayout) {
        this.context = context;
    }



    private GuidelineBuilder withTop(float percentage) {
        guidelineTop = createGuideline(ConstraintLayout.LayoutParams.HORIZONTAL, 0);
        playerLayout.addView(guidelineTop);
        return this;
    }

    private GuidelineBuilder withBottom(float percentage) {
        guidelineBottom = createGuideline(ConstraintLayout.LayoutParams.HORIZONTAL, 1);
        return this;
    }

    private GuidelineBuilder withLeft(float percentage) {
        guidelineRight = createGuideline(ConstraintLayout.LayoutParams.VERTICAL, 0);
        return this;
    }

    private GuidelineBuilder withRight(float percentage) {
        guidelineTop = createGuideline(ConstraintLayout.LayoutParams.VERTICAL, 1);
        return this;
    }

    private Guideline createGuideline(int orientation, float percentage) {
        Guideline guideline = new Guideline(context);
        ConstraintLayout.LayoutParams guidelineLayoutParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        guidelineLayoutParams.guidePercent = percentage;
        guidelineLayoutParams.orientation = orientation;
        guideline.setLayoutParams(guidelineLayoutParams);
        guideline.setId(View.generateViewId());
        return guideline;
    }

    public void setPercentage(Guideline guideline, float percentage) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) guideline.getLayoutParams();
        layoutParams.guidePercent = percentage;
        guideline.setLayoutParams(layoutParams);
    }

    public void setBegin(Guideline guideline, int begin) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) guideline.getLayoutParams();
        layoutParams.guideBegin = begin;
        guideline.setLayoutParams(layoutParams);
    }

    public void setEnd(Guideline guideline, int end) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) guideline.getLayoutParams();
        layoutParams.guideEnd = end;
        guideline.setLayoutParams(layoutParams);
    }

    public float getPercentage(Guideline guideline) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) guideline.getLayoutParams();
        return layoutParams.guidePercent;
    }

    public void center(Guideline horizontal, Guideline vertical, View view) {
        ConstraintLayout.LayoutParams horizontalLayoutParams = (ConstraintLayout.LayoutParams) horizontal.getLayoutParams();
        int horizontalOrientation = horizontalLayoutParams.orientation;
        ConstraintLayout.LayoutParams verticalLayoutParams = (ConstraintLayout.LayoutParams) vertical.getLayoutParams();
        int verticalOrientation = verticalLayoutParams.orientation;

        if (!(horizontalOrientation == ConstraintLayout.LayoutParams.HORIZONTAL ||
                verticalOrientation == ConstraintLayout.LayoutParams.VERTICAL)) {
            Log.e("ez.guide.center(gh, gv, view)", "Invalid orientation");
            return;
        }

        ConstraintLayout.LayoutParams viewLayoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        viewLayoutParams.topToTop = horizontal.getId();
        viewLayoutParams.leftToLeft = vertical.getId();
        viewLayoutParams.rightToRight = vertical.getId();
        viewLayoutParams.bottomToBottom = horizontal.getId();
        view.setLayoutParams(viewLayoutParams);
    }

    public void center(Guideline guideline, View view) {

        ConstraintLayout.LayoutParams guidelineLayoutParams = (ConstraintLayout.LayoutParams) guideline.getLayoutParams();
        int orientation = guidelineLayoutParams.orientation;

        ConstraintLayout.LayoutParams viewLayoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        if (orientation == ConstraintLayout.LayoutParams.HORIZONTAL) {
            viewLayoutParams.topToTop = guideline.getId();
            viewLayoutParams.bottomToBottom = guideline.getId();
        } else if (orientation == ConstraintLayout.LayoutParams.VERTICAL) {
            viewLayoutParams.leftToLeft = guideline.getId();
            viewLayoutParams.rightToRight = guideline.getId();
        } else {
            Log.w("ez.guide.center(g, view)", "Invalid orientation");
        }
        view.setLayoutParams(viewLayoutParams);
    }

    /**
     * <h4>Get Percentage Pos</h4>
     * <p>Calculates and returns value of either X or Y co-ordinate of a guideline based on its percentage</p>
     *
     * @param guideline Input guideline
     * @return X or Y coordinate
     */
    public float getPos(Guideline guideline) {

        // Get guideline percentage
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) guideline.getLayoutParams();
        float percentage = layoutParams.guidePercent;

        // Get screen dimensions
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float screenHeight = (float) dm.heightPixels - 48f; // Minus 48 as there's a 48px discrepancy for some reason...
        float screenWidth = (float) dm.widthPixels;

        // Calculate position based on guideline orientation
        float pos = 0f;
        switch (layoutParams.orientation) {
            case ConstraintLayout.LayoutParams.HORIZONTAL:
                pos = percentage * screenHeight;
                break;
            case ConstraintLayout.LayoutParams.VERTICAL:
                pos = percentage * screenWidth;
                break;
            default:
                break;
        }
        return pos;
    }

    public int getBegin(Guideline guideline) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) guideline.getLayoutParams();
        return layoutParams.guideBegin;
    }

    public int getEnd(Guideline guideline) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) guideline.getLayoutParams();
        return layoutParams.guideEnd;
    }

}
