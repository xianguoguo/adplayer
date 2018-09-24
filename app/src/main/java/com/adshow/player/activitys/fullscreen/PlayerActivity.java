package com.adshow.player.activitys.fullscreen;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.view.View;
import android.widget.ImageView;

import com.adshow.player.R;
import com.adshow.player.util.AppUtils;
import com.adshow.player.widgets.DateTimeTextViewWrapper;
import com.adshow.player.widgets.ExoVideoViewWrapper;
import com.adshow.player.widgets.ImageSliderViewWrapper;
import com.adshow.player.widgets.ImageViewWrapper;
import com.adshow.player.widgets.ScrollTextViewWrapper;
import com.adshow.player.widgets.WeatherTextViewWrapper;
import com.devbrackets.android.exomedia.core.video.scale.ScaleType;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.google.android.exoplayer2.Player;

import org.json.JSONArray;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerActivity extends AppCompatActivity {

    private List<ImageViewObserver> imageViewObserverList = new ArrayList<>();
    private List<ImageViewWrapper> imageViewList = new ArrayList<>();

    private ExoVideoPlayerObserver exoVideoPlayerObserver;
    private ExoVideoViewWrapper videoView;

    private ExoVideoPlayerObserver exoAudioPlayerObserver;
    private ExoVideoViewWrapper audioView;

    private ImageSliderViewWrapper sliderView;

    private ConstraintLayout playerLayout;

    private Bundle savedInstanceState;

    private int index = 1;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            playerLayout.removeAllViews();
            if (index % 2 == 0) {
                System.out.println("============initAdvertisingA============");
                initAdvertisingA(savedInstanceState);
            } else {
                System.out.println("============initAdvertisingB============");
                initAdvertisingB(savedInstanceState);
            }
            index++;
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiti_player);
        playerLayout = findViewById(R.id.playerLayout);

        initAdvertisingA(savedInstanceState);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // (1) 使用handler发送消息
                Message message = new Message();
                message.what = 0;
                handler.sendMessage(message);
            }
        }, 0, 30 * 1000);

    }


    private void initAdvertisingA(Bundle savedInstanceState) {
        audioView = new ExoVideoViewWrapper(this);
        audioView.setVisibility(View.INVISIBLE);
        audioView.setVideoPath("/sdcard/Advertising/4303/Music/201801101738276686.mp3");
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
    }


    private void initAdvertisingB(Bundle savedInstanceState) {
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

        videoView = new ExoVideoViewWrapper(this);
        videoView.setVideoPath("/sdcard/Advertising/465/Video/201808242102251863.mp4");
        AppUtils.addViewWithConstraint(getApplicationContext(), playerLayout, videoView, 0.1138888888888889F, 0.8152777777777778F, 0.30943125F, 0.97890625F);


        ScrollTextViewWrapper scrollTextView = new ScrollTextViewWrapper(this);
        scrollTextView.setText("广告招商热线：18100780990");
        scrollTextView.setTextSize(35);
        scrollTextView.setTextColor(android.graphics.Color.RED);
        scrollTextView.setSpeed(2);
        scrollTextView.setTimes(Integer.MAX_VALUE);
        AppUtils.addViewWithConstraint(getApplicationContext(), playerLayout, scrollTextView, 0.8361111111111111F, 1F, 0.24375F, 0.8125F);


        DateTimeTextViewWrapper dateTimeTextView = new DateTimeTextViewWrapper(this);
        dateTimeTextView.setTextSize(22);
        dateTimeTextView.setTextColor(Color.BLACK);
        AppUtils.addViewWithConstraint(getApplicationContext(), playerLayout, dateTimeTextView, 0.0263888888888889F, 0.0847222222222222F, 0.11171875F, 0.54453125F);


        WeatherTextViewWrapper weatherTextView = new WeatherTextViewWrapper(this);
        weatherTextView.setCityId("101280601");
        weatherTextView.setCityName("深圳");
        weatherTextView.setTextSize(22);
        weatherTextView.setTextColor(Color.BLACK);
        AppUtils.addViewWithConstraint(getApplicationContext(), playerLayout, weatherTextView, 0.0263888888888889F, 0.0847222222222222F, 0.54140625F, 0.9890625F);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (exoVideoPlayerObserver != null) {
            exoVideoPlayerObserver.onStart();
        }
        if (exoAudioPlayerObserver != null) {
            exoAudioPlayerObserver.onStart();
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
        if (exoAudioPlayerObserver != null) {
            exoAudioPlayerObserver.onResume();
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
        if (exoAudioPlayerObserver != null) {
            exoAudioPlayerObserver.onStop();
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
        if (exoAudioPlayerObserver != null) {
            exoAudioPlayerObserver.onStop();
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
        if (exoAudioPlayerObserver != null) {
            exoAudioPlayerObserver.onDestroy();
        }
        if (sliderView != null) {
            sliderView.releaseBanner();
        }
    }

}
