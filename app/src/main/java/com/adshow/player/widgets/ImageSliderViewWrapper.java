package com.adshow.player.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.adshow.player.R;
import com.adshow.player.activitys.fullscreen.FadeInTransformer;
import com.adshow.player.activitys.fullscreen.FullScreenPlayActivity;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

import static com.youth.banner.BannerConfig.NOT_INDICATOR;

public class ImageSliderViewWrapper extends Banner {

    private Context context;

    private String imageSrc;

    public ImageSliderViewWrapper(Context context) {
        this(context, null);
    }

    public ImageSliderViewWrapper(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageSliderViewWrapper(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this.context = context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ImageSliderViewWrapper, 0, 0);
        for (int i = 0; i < ta.getIndexCount(); i++) {
            int attr = ta.getIndex(i);
            if (attr == R.styleable.ImageSliderViewWrapper_img_list) {
                imageSrc = ta.getString(attr);
            }
        }
        ta.recycle();

        ArrayList imageList = new ArrayList<>();
        if (imageSrc != null && imageSrc.length() != 0) {
            for (String tmp : imageSrc.split(",")) {
                imageList.add("file:///" + tmp);
            }
        }
        this.setDelayTime(3000);
        //设置图片加载器
        this.setImageLoader(new PicassoImageLoader())
                //添加图片
                .setImages(imageList)
                //banner加点
                .setBannerStyle(NOT_INDICATOR)
                //点居中
                //.setIndicatorGravity(CENTER)
                //设置动画
                .setBannerAnimation(FadeInTransformer.class)
                .start();
    }


    public class PicassoImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Picasso.with(context).load(String.valueOf(path)).into(imageView);
        }
    }
}
