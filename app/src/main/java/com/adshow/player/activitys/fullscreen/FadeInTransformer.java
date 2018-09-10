package com.adshow.player.activitys.fullscreen;

import android.view.View;
import android.view.animation.AlphaAnimation;

import com.youth.banner.transformer.ABaseTransformer;

public class FadeInTransformer extends ABaseTransformer {

    @Override
    protected void onTransform(View view, float position) {
        final float normalizedposition = Math.abs(Math.abs(position) - 1);
        view.setAlpha(normalizedposition);

//        alphaAnimation.setDuration(1000);    //深浅动画持续时间
//        alphaAnimation.setFillAfter(true);
//        view.setAlpha(1.0f);
//        view.setAnimation(alphaAnimation);
//        alphaAnimation.start();
    }

}
