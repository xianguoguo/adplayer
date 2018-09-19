package com.adshow.player.activitys.fullscreen;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;

import com.adshow.player.R;
import com.adshow.player.widgets.ExoVideoViewWrapper;
import com.adshow.player.widgets.ImageSliderViewWrapper;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.jarvanmo.exoplayerview.ui.ExoVideoView;

public class PlayerActivity extends AppCompatActivity {

    private ExoVideoPlayerObserver exoVideoPlayerObserver;
    private ExoVideoViewWrapper videoView;

    private ExoVideoPlayerObserver exoAudioPlayerObserver;
    private ExoVideoViewWrapper audioView;

    private ImageSliderViewWrapper sliderView;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiti_player);
//        videoView = findViewById(R.id.videoPlayerView);
//        exoVideoPlayerObserver = new ExoVideoPlayerObserver(this.getApplicationContext(), videoView);
//
//        exoVideoPlayerObserver.onCreate(savedInstanceState);

        audioView = findViewById(R.id.audioPlayerView);
        exoAudioPlayerObserver = new ExoVideoPlayerObserver(this.getApplicationContext(), audioView);
        exoAudioPlayerObserver.onCreate(savedInstanceState);

        sliderView = findViewById(R.id.sliderView);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT > 23) {
            audioView.resume();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ((Build.VERSION.SDK_INT <= 23)) {
            audioView.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT <= 23) {
            audioView.pause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT > 23) {
            audioView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        audioView.releasePlayer();
    }


}
