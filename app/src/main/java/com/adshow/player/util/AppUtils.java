package com.adshow.player.util;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.Guideline;
import android.view.View;

public class AppUtils {

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    public static void addViewWithConstraint(Context context, ConstraintLayout constraintLayout, View view,
                                             float percentageTop, float percentageBottom, float percentageLeft, float percentageRight) {

        Guideline guidelineTop = createGuideline(context, ConstraintLayout.LayoutParams.HORIZONTAL, percentageTop);
        Guideline guidelineBottom = createGuideline(context, ConstraintLayout.LayoutParams.HORIZONTAL, percentageBottom);
        Guideline guidelineLeft = createGuideline(context, ConstraintLayout.LayoutParams.VERTICAL, percentageLeft);
        Guideline guidelineRight = createGuideline(context, ConstraintLayout.LayoutParams.VERTICAL, percentageRight);
        constraintLayout.addView(guidelineTop);
        constraintLayout.addView(guidelineLeft);
        constraintLayout.addView(guidelineBottom);
        constraintLayout.addView(guidelineRight);
        constraintLayout.addView(view);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(view.getId(), ConstraintSet.TOP, guidelineTop.getId(), ConstraintSet.TOP);
        constraintSet.connect(view.getId(), ConstraintSet.LEFT, guidelineLeft.getId(), ConstraintSet.LEFT);
        constraintSet.connect(view.getId(), ConstraintSet.BOTTOM, guidelineBottom.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(view.getId(), ConstraintSet.RIGHT, guidelineRight.getId(), ConstraintSet.RIGHT);
        constraintSet.constrainWidth(view.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSet.constrainHeight(view.getId(), ConstraintSet.MATCH_CONSTRAINT);

        constraintSet.applyTo(constraintLayout);
    }

    private static Guideline createGuideline(Context context, int orientation, float percentage) {
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
}
