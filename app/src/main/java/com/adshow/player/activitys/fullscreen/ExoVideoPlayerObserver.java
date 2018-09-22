package com.adshow.player.activitys.fullscreen;

import android.content.Context;
import android.os.Bundle;

import com.adshow.player.widgets.ExoVideoViewWrapper;

public class ExoVideoPlayerObserver extends AbstractObserver {

    private ExoVideoViewWrapper videoView;

    private Context context;

    public ExoVideoPlayerObserver(Context context, ExoVideoViewWrapper videoView) {
        this.videoView = videoView;
        this.context = context;
    }

    @Override
    void onCreate(Bundle savedInstanceState) {
    }


    @Override
    protected void onStart() {
        videoView.start();
    }

    @Override
    protected void onResume() {
        videoView.start();
    }

    @Override
    public void onPause() {
        videoView.pause();
    }

    @Override
    public void onStop() {
        videoView.pause();
    }

    @Override
    protected void onDestroy() {
        videoView.pause();
    }

}
