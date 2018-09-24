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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.adshow.player.R;
import com.adshow.player.event.MyEvent;
import com.adshow.player.event.PlayEvent;
import com.adshow.player.util.AppUtils;
import com.adshow.player.util.FileUtils;
import com.adshow.player.widgets.DateTimeTextViewWrapper;
import com.adshow.player.widgets.ExoVideoViewWrapper;
import com.adshow.player.widgets.ImageSliderViewWrapper;
import com.adshow.player.widgets.ImageViewWrapper;
import com.adshow.player.widgets.ScrollTextViewWrapper;
import com.adshow.player.widgets.WeatherTextViewWrapper;
import com.devbrackets.android.exomedia.core.video.scale.ScaleType;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.google.android.exoplayer2.Player;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;

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

    private ScrollTextViewWrapper scrollTextView;

    private DateTimeTextViewWrapper dateTimeTextView;

    private WeatherTextViewWrapper weatherTextView;

    private ConstraintLayout playerLayout;

    private Bundle savedInstanceState;


    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiti_player);
        playerLayout = findViewById(R.id.playerLayout);

        EventBus.getDefault().register(this);
        MyEvent event = new MyEvent("playerReady");
        EventBus.getDefault().post(event);
    }


    @Subscribe(threadMode = ThreadMode.MAIN, priority = 1)
    public void onReceivePlayEvent(PlayEvent event) throws JSONException {
        Log.d("loadAdContent", "播放广告:" + event.advertisingPath);
        List<ADMaterial> materialList = FileUtils.readAdvertisingConfig(event.advertisingPath);
        addViewByConfig(savedInstanceState, materialList);
    }


    private void addViewByConfig(Bundle savedInstanceState, List<ADMaterial> materialList) {
        playerLayout.removeAllViews();
        imageViewObserverList.clear();
        imageViewList.clear();
        exoVideoPlayerObserver = null;
        videoView = null;
        exoAudioPlayerObserver = null;
        audioView = null;
        sliderView = null;
        scrollTextView = null;
        dateTimeTextView = null;
        weatherTextView = null;
        for (ADMaterial material : materialList) {
            try {
                if (Class.forName(material.getClazz()) == ExoVideoViewWrapper.class) {
                    videoView = AppUtils.addExoVideoView(this, playerLayout, material);
                }
                if (Class.forName(material.getClazz()) == ImageSliderViewWrapper.class) {
                    sliderView = AppUtils.addImageSliderView(this, playerLayout, material);
                }
                if (Class.forName(material.getClazz()) == ImageViewWrapper.class) {
                    ImageViewWrapper imageView = AppUtils.addImageView(this, playerLayout, material);
                    imageViewList.add(imageView);

                    ImageViewObserver imageViewObserver = new ImageViewObserver(this.getApplicationContext(), imageView);
                    imageViewObserver.onCreate(savedInstanceState);
                    imageViewObserverList.add(imageViewObserver);
                }
                if (Class.forName(material.getClazz()) == ScrollTextViewWrapper.class) {
                    scrollTextView = AppUtils.addScrollTextView(this, playerLayout, material);
                }
                if (Class.forName(material.getClazz()) == DateTimeTextViewWrapper.class) {
                    dateTimeTextView = AppUtils.addDateTimeTextView(this, playerLayout, material);
                }
                if (Class.forName(material.getClazz()) == WeatherTextViewWrapper.class) {
                    weatherTextView = AppUtils.addWeatherTextView(this, playerLayout, material);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void initAdvertisingA(Bundle savedInstanceState) {

        List<ADMaterial> materialList = new ArrayList<>();

        ADMaterial material = new ADMaterial("com.adshow.player.widgets.ExoVideoViewWrapper", 1, 0, 1, 0, 1);
        material.getAttrs().put("videoPath", "/4303/Music/201801101738276686.mp3");
        materialList.add(material);

        material = new ADMaterial("com.adshow.player.widgets.ImageSliderViewWrapper", 2, 0, 1, 0, 1);
        material.getAttrs().put("imageUrls", "/4303/Images/201807280906560146.jpg,/4303/Images/201807280906563946.jpg,/4303/Images/201807280906585846.jpg,/4303/Images/201807280907326347.jpg,/4303/Images/201807280907344747.jpg");
        materialList.add(material);

        System.out.println(new Gson().toJson(materialList));

        addViewByConfig(savedInstanceState, materialList);
    }


    private void initAdvertisingB(Bundle savedInstanceState) {

        List<ADMaterial> materialList = new ArrayList<>();

        ADMaterial material = new ADMaterial("com.adshow.player.widgets.ImageViewWrapper", 1, 0, 1, 0, 1);
        material.getAttrs().put("imageUrl", "/465/Images/201808242121107020.jpg");
        materialList.add(material);

        material = new ADMaterial("com.adshow.player.widgets.ImageViewWrapper", 2, 0.122222222F, 0.7847222F, 0.0265625F, 0.28203125F);
        material.getAttrs().put("imageUrl", "/465/Images/201808251641536863.jpg");
        materialList.add(material);

        material = new ADMaterial("com.adshow.player.widgets.ExoVideoViewWrapper", 3, 0.1138888888888889F, 0.8152777777777778F, 0.30943125F, 0.97890625F);
        material.getAttrs().put("videoPath", "/465/Video/201808242102251863.mp4");
        materialList.add(material);

        material = new ADMaterial("com.adshow.player.widgets.ScrollTextViewWrapper", 4, 0.8361111111111111F, 1F, 0.24375F, 0.8125F);
        material.getAttrs().put("text", "广告招商热线：18100780990");
        material.getAttrs().put("textSize", 35);
        material.getAttrs().put("textColor", "0xFFFF0000");
        material.getAttrs().put("speed", 2);
        materialList.add(material);

        material = new ADMaterial("com.adshow.player.widgets.DateTimeTextViewWrapper", 5, 0.0263888888888889F, 0.0847222222222222F, 0.11171875F, 0.54453125F);
        material.getAttrs().put("textSize", 22);
        material.getAttrs().put("textColor", "0xFF000000");
        materialList.add(material);

        material = new ADMaterial("com.adshow.player.widgets.WeatherTextViewWrapper", 6, 0.0263888888888889F, 0.0847222222222222F, 0.54140625F, 0.9890625F);
        material.getAttrs().put("cityId", "101280601");
        material.getAttrs().put("cityName", "深圳");
        material.getAttrs().put("textSize", 22);
        material.getAttrs().put("textColor", "0xFF000000");
        materialList.add(material);

        System.out.println(new Gson().toJson(materialList));

        addViewByConfig(savedInstanceState, materialList);
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
