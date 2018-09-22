package com.adshow.player.activitys.fullscreen;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.adshow.player.R;
import com.adshow.player.util.AppUtils;
import com.adshow.player.widgets.ExoVideoViewWrapper;
import com.adshow.player.widgets.ImageSliderViewWrapper;
import com.adshow.player.widgets.ImageViewWrapper;
import com.devbrackets.android.exomedia.core.video.scale.ScaleType;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.google.android.exoplayer2.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerActivity extends AppCompatActivity {

    private List<ImageViewObserver> imageViewObserverList = new ArrayList<>();
    private List<ImageViewWrapper> imageViewList = new ArrayList<>();

    private ExoVideoPlayerObserver exoVideoPlayerObserver;
    private ExoVideoViewWrapper videoView;

    private ExoVideoPlayerObserver exoAudioPlayerObserver;
    private ExoVideoViewWrapper audioView;

    private ImageSliderViewWrapper sliderView;

    private ConstraintLayout playerLayout;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiti_player);
        playerLayout = findViewById(R.id.playerLayout);

        /*
        audioView = new ExoVideoViewWrapper(this);
        audioView.setId(View.generateViewId());
        audioView.setVisibility(View.INVISIBLE);
        audioView.setUrl("/sdcard/Advertising/4303/Music/201801101738276686.mp3");
        AppUtils.addViewWithConstraint(getApplicationContext(), playerLayout, audioView, 0, 1, 0, 1);

        sliderView = new ImageSliderViewWrapper(this);
        sliderView.setId(View.generateViewId());
        String imageUrls = "/sdcard/Advertising/4303/Images/201807280906560146.jpg,/sdcard/Advertising/4303/Images/201807280906563946.jpg,/sdcard/Advertising/4303/Images/201807280906585846.jpg,/sdcard/Advertising/4303/Images/201807280907326347.jpg,/sdcard/Advertising/4303/Images/201807280907344747.jpg";
        List<String> imageList = new ArrayList<String>();
        if (imageUrls != null && imageUrls.length() != 0) {
            for (String tmp : imageUrls.split(",")) {
                imageList.add("file:///" + tmp);
            }
        }
        sliderView.init(imageList);
        AppUtils.addViewWithConstraint(getApplicationContext(), playerLayout, sliderView, 0, 1, 0, 1);
        */

        ImageViewWrapper imageView = new ImageViewWrapper(this);
        imageView.setImageUrl("/sdcard/Advertising/465/Images/201808242121107020.jpg");
        AppUtils.addViewWithConstraint(getApplicationContext(), playerLayout, imageView, 0, 1, 0, 1);
        imageViewList.add(imageView);

        imageView = new ImageViewWrapper(this);
        imageView.setImageUrl("/sdcard/Advertising/465/Images/201808251641536863.jpg");
        AppUtils.addViewWithConstraint(getApplicationContext(), playerLayout, imageView, 0.122222222F, 0.7847222F, 0.0265625F, 0.28203125F);
        imageViewList.add(imageView);

        if (imageViewList != null && imageViewList.size() > 0) {
            for (ImageViewWrapper image : imageViewList) {
                ImageViewObserver imageViewObserver = new ImageViewObserver(this.getApplicationContext(), image);
                imageViewObserver.onCreate(savedInstanceState);
                imageViewObserverList.add(imageViewObserver);
            }
        }

//        videoView = new ExoVideoViewWrapper(this);
//        videoView.setId(View.generateViewId());
//        videoView.setUrl("/sdcard/Advertising/465/Video/201808242102251863.mp4");
//        AppUtils.addViewWithConstraint(getApplicationContext(), playerLayout, videoView, 0.1138888888888889F, 0.8152777777777778F, 0.30943125F, 0.97890625F);
//
//        if (videoView != null) {
//            exoVideoPlayerObserver = new ExoVideoPlayerObserver(this.getApplicationContext(), videoView);
//            exoVideoPlayerObserver.onCreate(savedInstanceState);
//        }


        videoView = new ExoVideoViewWrapper(this);
        videoView.setVideoPath("/sdcard/Advertising/465/Video/201808242102251863.mp4");
        AppUtils.addViewWithConstraint(getApplicationContext(), playerLayout, videoView, 0.1138888888888889F, 0.8152777777777778F, 0.30943125F, 0.97890625F);

//        if (Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
//            decorView.setSystemUiVisibility(uiOptions);
//        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (exoVideoPlayerObserver != null) {
            exoVideoPlayerObserver.onStart();
        }

        //开始轮播
        if (sliderView != null) {
            sliderView.startAutoPlay();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (exoVideoPlayerObserver != null) {
            exoVideoPlayerObserver.onResume();
        }
        if (sliderView != null) {
            sliderView.startAutoPlay();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (exoVideoPlayerObserver != null) {
            exoVideoPlayerObserver.onStop();
        }
        if (sliderView != null) {
            sliderView.stopAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (exoVideoPlayerObserver != null) {
            exoVideoPlayerObserver.onStop();
        }
        if (sliderView != null) {
            sliderView.stopAutoPlay();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (exoVideoPlayerObserver != null) {
            exoVideoPlayerObserver.onDestroy();
        }
        if (sliderView != null) {
            sliderView.releaseBanner();
        }
    }

}
