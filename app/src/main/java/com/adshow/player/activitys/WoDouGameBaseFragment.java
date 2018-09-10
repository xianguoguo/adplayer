package com.adshow.player.activitys;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.adshow.player.R;

public class WoDouGameBaseFragment extends Fragment {


    public View.OnFocusChangeListener mFocusChangeListener = new View.OnFocusChangeListener() {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            /* 禁用放大缩小效果
            int focus = hasFocus ? R.anim.enlarge : R.anim.decrease;
            Animation mAnimation = AnimationUtils.loadAnimation(getActivity().getApplication(), focus);
            mAnimation.setBackgroundColor(Color.TRANSPARENT);
            mAnimation.setFillAfter(hasFocus);
            v.startAnimation(mAnimation);
            mAnimation.start();*/
            //v.bringToFront();
        }
    };

    protected void showLongToast(String pMsg) {
        Toast.makeText(getActivity(), pMsg, Toast.LENGTH_LONG).show();
    }

    protected void showShortToast(String pMsg) {
        Toast.makeText(getActivity(), pMsg, Toast.LENGTH_SHORT).show();
    }
}
