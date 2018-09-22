package com.adshow.player.activitys.fullscreen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.adshow.player.R;
import com.adshow.player.widgets.ExoVideoViewWrapper;
import com.adshow.player.widgets.ImageViewWrapper;

public class ImageViewObserver extends AbstractObserver {

    private ImageViewWrapper imageView;

    private Context context;

    public ImageViewObserver(Context context, ImageViewWrapper imageView) {
        this.imageView = imageView;
        this.context = context;
    }


    @Override
    void onCreate(Bundle savedInstanceState) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        options.inScaled = true;
        Bitmap bm = BitmapFactory.decodeFile(imageView.getImageUrl(), options);
        imageView.setImageBitmap(bm);
    }

    @Override
    void onStart() {

    }

    @Override
    void onResume() {

    }

    @Override
    void onPause() {

    }

    @Override
    void onStop() {

    }

    @Override
    void onDestroy() {

    }

}
