package com.adshow.player.activitys.fullscreen;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaCodec;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Toast;

import com.adshow.player.R;
import com.adshow.player.widgets.ExoVideoViewWrapper;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.jarvanmo.exoplayerview.extension.MultiQualitySelectorAdapter;
import com.jarvanmo.exoplayerview.media.ExoMediaSource;
import com.jarvanmo.exoplayerview.media.SimpleMediaSource;
import com.jarvanmo.exoplayerview.media.SimpleQuality;
import com.jarvanmo.exoplayerview.ui.ExoVideoPlaybackControlView;
import com.jarvanmo.exoplayerview.ui.ExoVideoView;

import java.util.ArrayList;
import java.util.List;

import static com.jarvanmo.exoplayerview.orientation.OnOrientationChangedListener.SENSOR_LANDSCAPE;
import static com.jarvanmo.exoplayerview.orientation.OnOrientationChangedListener.SENSOR_PORTRAIT;

public class ExoVideoPlayerObserver extends AbstractObserver {

    private ExoVideoViewWrapper videoView;

    private Context context;

    public ExoVideoPlayerObserver(Context context, ExoVideoViewWrapper videoView) {
        this.videoView = videoView;
        this.context = context;
    }

    @Override
    void onCreate(Bundle savedInstanceState) {
        initVideoView();
    }


    @Override
    protected void onStart() {
//        super.onStart();
//        if (Build.VERSION.SDK_INT > 23) {
//            videoView.resume();
//        }
    }

    @Override
    protected void onResume() {
//        super.onResume();
//        if ((Build.VERSION.SDK_INT <= 23)) {
//            videoView.resume();
//        }
    }

    @Override
    public void onPause() {
//        super.onPause();
//        if (Build.VERSION.SDK_INT <= 23) {
//            videoView.pause();
//        }
    }

    @Override
    public void onStop() {
//        super.onStop();
//        if (Build.VERSION.SDK_INT > 23) {
//            videoView.pause();
//        }
    }

    @Override
    protected void onDestroy() {
//        super.onDestroy();
//        videoView.releasePlayer();
    }


    private void initVideoView() {

        SimpleMediaSource mediaSource = new SimpleMediaSource("file:///" + ((ExoVideoViewWrapper) videoView).getPath());
        mediaSource.setDisplayName("Apple HLS");
        videoView.setRepeatToggleModes(Player.REPEAT_MODE_ALL);
        videoView.play(mediaSource, true);
    }

}
