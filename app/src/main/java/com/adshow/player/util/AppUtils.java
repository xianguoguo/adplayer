package com.adshow.player.util;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.Guideline;
import android.view.View;

import com.adshow.player.activitys.fullscreen.ADMaterial;
import com.adshow.player.widgets.DateTimeTextViewWrapper;
import com.adshow.player.widgets.ExoVideoViewWrapper;
import com.adshow.player.widgets.ImageSliderViewWrapper;
import com.adshow.player.widgets.ImageViewWrapper;
import com.adshow.player.widgets.ScrollTextViewWrapper;
import com.adshow.player.widgets.WeatherTextViewWrapper;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AppUtils {

    private static String basePath = "file:////sdcard/Advertising";

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static String getMacAddress(Context context) {
        String macAddress = null;
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                int type = mNetworkInfo.getType();
                if (type == ConnectivityManager.TYPE_ETHERNET) {

//                    EthernetManager mEthManager = (EthernetManager)context.getSystemService(Context.ETHERNET_SERVICE);
//                    macAddress = mEthManager.getDevInfo().getHwaddr().toUpperCase();
                } else if (type == ConnectivityManager.TYPE_WIFI) {
                    WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    macAddress = mWifiManager.getConnectionInfo().getMacAddress().toUpperCase();
                }
            }
        }
        return macAddress;
    }


    public static int stringTransInt(String color) {
        int startAlpha = Integer.parseInt(color.substring(2, 4), 16);
        int startRed = Integer.parseInt(color.substring(4, 6), 16);
        int startGreen = Integer.parseInt(color.substring(6, 8), 16);
        int startBlue = Integer.parseInt(color.substring(8), 16);
        return Color.argb(startAlpha, startRed, startGreen, startBlue);
    }


    public static ExoVideoViewWrapper addExoVideoView(Context context, ConstraintLayout playerLayout, ADMaterial material) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        if (Class.forName(material.getClazz()) != ExoVideoViewWrapper.class) {
            return null;
        }

        ExoVideoViewWrapper videoView = new ExoVideoViewWrapper(context);
        videoView.setVideoPath(basePath.substring(8) + material.getAttrs().get("videoPath").toString());
        if (material.getAttrs().get("videoPath").toString().endsWith(".mp3")) {
            videoView.setVisibility(View.INVISIBLE);
        }

        AppUtils.addViewWithConstraint(context, playerLayout, videoView, material.getPercentageTop(), material.getPercentageBottom(), material.getPercentageLeft(), material.getPercentageRight());
        return videoView;
    }


    public static ImageSliderViewWrapper addImageSliderView(Context context, ConstraintLayout playerLayout, ADMaterial material) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        if (Class.forName(material.getClazz()) != ImageSliderViewWrapper.class) {
            return null;
        }

        ImageSliderViewWrapper sliderView = new ImageSliderViewWrapper(context);
        String imageUrls = material.getAttrs().get("imageUrls").toString();
        List<String> imageList = new ArrayList<String>();
        if (imageUrls != null && imageUrls.length() != 0) {
            for (String tmp : imageUrls.split(",")) {
                imageList.add(basePath + tmp);
            }
        }
        sliderView.init(imageList);

        AppUtils.addViewWithConstraint(context, playerLayout, sliderView, material.getPercentageTop(), material.getPercentageBottom(), material.getPercentageLeft(), material.getPercentageRight());
        return sliderView;
    }


    public static ImageViewWrapper addImageView(Context context, ConstraintLayout playerLayout, ADMaterial material) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        if (Class.forName(material.getClazz()) != ImageViewWrapper.class) {
            return null;
        }

        ImageViewWrapper imageView = new ImageViewWrapper(context);
        imageView.setImageUrl(basePath.substring(8) + material.getAttrs().get("imageUrl"));

        AppUtils.addViewWithConstraint(context, playerLayout, imageView, material.getPercentageTop(), material.getPercentageBottom(), material.getPercentageLeft(), material.getPercentageRight());
        return imageView;
    }

    public static ScrollTextViewWrapper addScrollTextView(Context context, ConstraintLayout playerLayout, ADMaterial material) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        if (Class.forName(material.getClazz()) != ScrollTextViewWrapper.class) {
            return null;
        }

        ScrollTextViewWrapper scrollTextView = new ScrollTextViewWrapper(context);
        scrollTextView.setText(material.getAttrs().get("text").toString());
        scrollTextView.setTextSize(Double.valueOf(material.getAttrs().get("textSize").toString()).intValue());
        scrollTextView.setTextColor(stringTransInt(material.getAttrs().get("textColor").toString()));
        scrollTextView.setSpeed(Double.valueOf(material.getAttrs().get("speed").toString()).intValue());
        scrollTextView.setTimes(Integer.MAX_VALUE);

        AppUtils.addViewWithConstraint(context, playerLayout, scrollTextView, material.getPercentageTop(), material.getPercentageBottom(), material.getPercentageLeft(), material.getPercentageRight());
        return scrollTextView;
    }


    public static DateTimeTextViewWrapper addDateTimeTextView(Context context, ConstraintLayout playerLayout, ADMaterial material) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        if (Class.forName(material.getClazz()) != DateTimeTextViewWrapper.class) {
            return null;
        }

        DateTimeTextViewWrapper dateTimeTextView = new DateTimeTextViewWrapper(context);
        dateTimeTextView.setTextSize(Double.valueOf(material.getAttrs().get("textSize").toString()).intValue());
        dateTimeTextView.setTextColor(stringTransInt(material.getAttrs().get("textColor").toString()));

        AppUtils.addViewWithConstraint(context, playerLayout, dateTimeTextView, material.getPercentageTop(), material.getPercentageBottom(), material.getPercentageLeft(), material.getPercentageRight());
        return dateTimeTextView;
    }


    public static WeatherTextViewWrapper addWeatherTextView(Context context, ConstraintLayout playerLayout, ADMaterial material) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        if (Class.forName(material.getClazz()) != WeatherTextViewWrapper.class) {
            return null;
        }

        WeatherTextViewWrapper weatherTextView = new WeatherTextViewWrapper(context);
        weatherTextView.setCityId(material.getAttrs().get("cityId").toString());
        weatherTextView.setCityName(material.getAttrs().get("cityName").toString());
        weatherTextView.setTextSize(Double.valueOf(material.getAttrs().get("textSize").toString()).intValue());
        weatherTextView.setTextColor(stringTransInt(material.getAttrs().get("textColor").toString()));

        AppUtils.addViewWithConstraint(context, playerLayout, weatherTextView, material.getPercentageTop(), material.getPercentageBottom(), material.getPercentageLeft(), material.getPercentageRight());
        return weatherTextView;
    }

    public static void addViewWithConstraint(Context context, ConstraintLayout constraintLayout, View view,
                                             float percentageTop, float percentageBottom, float percentageLeft, float percentageRight) {

        Guideline guidelineTop = createGuideline(context, ConstraintLayout.LayoutParams.HORIZONTAL, percentageTop);
        Guideline guidelineBottom = createGuideline(context, ConstraintLayout.LayoutParams.HORIZONTAL, percentageBottom);
        Guideline guidelineLeft = createGuideline(context, ConstraintLayout.LayoutParams.VERTICAL, percentageLeft);
        Guideline guidelineRight = createGuideline(context, ConstraintLayout.LayoutParams.VERTICAL, percentageRight);
        constraintLayout.addView(guidelineTop);
        constraintLayout.addView(guidelineLeft);
        constraintLayout.addView(guidelineBottom);
        constraintLayout.addView(guidelineRight);
        constraintLayout.addView(view);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(view.getId(), ConstraintSet.TOP, guidelineTop.getId(), ConstraintSet.TOP);
        constraintSet.connect(view.getId(), ConstraintSet.LEFT, guidelineLeft.getId(), ConstraintSet.LEFT);
        constraintSet.connect(view.getId(), ConstraintSet.BOTTOM, guidelineBottom.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(view.getId(), ConstraintSet.RIGHT, guidelineRight.getId(), ConstraintSet.RIGHT);
        constraintSet.constrainWidth(view.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSet.constrainHeight(view.getId(), ConstraintSet.MATCH_CONSTRAINT);

        constraintSet.applyTo(constraintLayout);
    }

    private static Guideline createGuideline(Context context, int orientation, float percentage) {
        Guideline guideline = new Guideline(context);
        ConstraintLayout.LayoutParams guidelineLayoutParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        guidelineLayoutParams.guidePercent = percentage;
        guidelineLayoutParams.orientation = orientation;
        guideline.setLayoutParams(guidelineLayoutParams);
        guideline.setId(View.generateViewId());
        return guideline;
    }
}
