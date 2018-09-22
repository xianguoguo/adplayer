package com.adshow.player.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.devbrackets.android.exomedia.core.video.scale.ScaleType;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.google.android.exoplayer2.Player;

public class ExoVideoViewWrapper extends VideoView {

    public ExoVideoViewWrapper(Context context) {
        this(context, null);
    }

    public ExoVideoViewWrapper(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExoVideoViewWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setId(View.generateViewId());
        this.setScaleType(ScaleType.FIT_XY);
        this.setRepeatMode(Player.REPEAT_MODE_ALL);
        VideoView videoView = this;
        videoView.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared() {
                videoView.start();
            }
        });
    }
}
