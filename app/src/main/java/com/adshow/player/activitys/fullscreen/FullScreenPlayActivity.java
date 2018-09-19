package com.adshow.player.activitys.fullscreen;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.adshow.player.R;
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
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.transformer.ZoomOutSlideTransformer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.youth.banner.BannerConfig.NOT_INDICATOR;

public class FullScreenPlayActivity extends AppCompatActivity {
    private static final String KEY_PLAY_WHEN_READY = "play_when_ready";
    private static final String KEY_WINDOW = "window";
    private static final String KEY_POSITION = "position";
    private static final String LOG_TAG = "VideoPlayerFragment";
    private Context mContext;
    private ActionBar mactionBar;
    @Bind(R.id.banner)
    Banner banner;
    List<String> image;

    MediaPlayer mPlayer = new MediaPlayer();

    private SurfaceView mVideoPlayerView;
    private SimpleExoPlayer mVideoPlayer;

    private Timeline.Window window;
    private DataSource.Factory mediaDataSourceFactory;
    private DefaultTrackSelector trackSelector;
    private boolean shouldAutoPlay;
    private BandwidthMeter bandwidthMeter;

    private boolean playWhenReady;
    private int currentWindow;
    private long playbackPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        mactionBar = getActionBar();
        setContentView(R.layout.activity_fullscreen);
        ButterKnife.bind(this);
        image = new ArrayList<>();
        image.add("file:////sdcard/Advertising/666/Images/201807280906560146.jpg");
        image.add("file:////sdcard/Advertising/666/Images/201807280906563946.jpg");
        image.add("file:////sdcard/Advertising/666/Images/201807280906585846.jpg");
        image.add("file:////sdcard/Advertising/666/Images/201807280907326347.jpg");
        image.add("file:////sdcard/Advertising/666/Images/201807280907344747.jpg");
        banner.setDelayTime(3000);
        //设置图片加载器
        banner.setImageLoader(new PicassoImageLoader())
                //添加图片
                .setImages(image)
                //banner加点
                .setBannerStyle(NOT_INDICATOR)
                //点居中
                //.setIndicatorGravity(CENTER)
                //设置动画
                .setBannerAnimation(FadeInTransformer.class)
                .start();

//        ViewGroup.LayoutParams ll = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams = new AbsoluteLayout.LayoutParams(ll);
//        layoutParams.y = 300;
//        layoutParams.x = 300;
//        layoutParams.height = 300;
//        layoutParams.width = 500;


        if (savedInstanceState == null) {
            playWhenReady = true;
            currentWindow = 0;
            playbackPosition = 0;
        } else {
            playWhenReady = savedInstanceState.getBoolean(KEY_PLAY_WHEN_READY);
            currentWindow = savedInstanceState.getInt(KEY_WINDOW);
            playbackPosition = savedInstanceState.getLong(KEY_POSITION);
        }

        shouldAutoPlay = true;
        bandwidthMeter = new DefaultBandwidthMeter();
        mediaDataSourceFactory = new FileDataSourceFactory((TransferListener<? super DataSource>) bandwidthMeter);
        window = new Timeline.Window();
    }



    private void initializePlayer() {

        mVideoPlayerView = findViewById(R.id.videoPlayerView2);
        mVideoPlayerView.requestFocus();

        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);

        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        mVideoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        //mVideoPlayerView.setPlayer(mVideoPlayer);

        mVideoPlayer.setPlayWhenReady(shouldAutoPlay);
        mVideoPlayer.setRepeatMode(Player.REPEAT_MODE_ALL);
        mVideoPlayer.setVideoScalingMode(MediaCodec.VIDEO_SCALING_MODE_SCALE_TO_FIT);
        mVideoPlayer.setVideoSurfaceView(mVideoPlayerView);

        MediaSource mediaSource = new ExtractorMediaSource.Factory(mediaDataSourceFactory)
                .createMediaSource(Uri.parse("file:////sdcard/Advertising/666/Video/t1-ui.mp4"));
        boolean haveStartPosition = currentWindow != C.INDEX_UNSET;
        if (haveStartPosition) {
            mVideoPlayer.seekTo(currentWindow, playbackPosition);
        }

        mVideoPlayer.prepare(mediaSource, !haveStartPosition, false);

    }

    private void releasePlayer() {
        if (mVideoPlayer != null) {
            updateStartPosition();
            shouldAutoPlay = mVideoPlayer.getPlayWhenReady();
            mVideoPlayer.release();
            mVideoPlayer = null;
            trackSelector = null;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        updateStartPosition();

        outState.putBoolean(KEY_PLAY_WHEN_READY, playWhenReady);
        outState.putInt(KEY_WINDOW, currentWindow);
        outState.putLong(KEY_POSITION, playbackPosition);
        super.onSaveInstanceState(outState);
    }

    private void updateStartPosition() {
        playbackPosition = mVideoPlayer.getCurrentPosition();
        currentWindow = mVideoPlayer.getCurrentWindowIndex();
        playWhenReady = mVideoPlayer.getPlayWhenReady();
    }


    public class PicassoImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Picasso.with(context).load(String.valueOf(path)).into(imageView);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        banner.startAutoPlay();
        if ((Util.SDK_INT <= 23 || mVideoPlayer == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        banner.stopAutoPlay();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

}
