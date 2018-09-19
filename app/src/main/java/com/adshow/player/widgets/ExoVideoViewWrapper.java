package com.adshow.player.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.media.MediaCodec;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.SurfaceView;

import com.adshow.player.R;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.FileDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.jarvanmo.exoplayerview.ui.ExoVideoView;

public class ExoVideoViewWrapper extends ExoVideoView {

    private Context context;

    private String path;

    public ExoVideoViewWrapper(Context context) {
        this(context, null);
    }

    public ExoVideoViewWrapper(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExoVideoViewWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ExoVideoViewWrapper, 0, 0);
        for (int i = 0; i < ta.getIndexCount(); i++) {
            int attr = ta.getIndex(i);
            if (attr == R.styleable.ExoVideoViewWrapper_video_src) {
                path = ta.getString(attr);
            }
        }
    }

    public String getPath() {
        return path;
    }
}
