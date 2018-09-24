package com.adshow.player.widgets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;

import com.adshow.player.activitys.MainActivity;
import com.adshow.player.service.ADPlayerBackendService;
import com.adshow.player.service.response.RestResult;
import com.adshow.player.service.response.WeatherResult;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherTextViewWrapper extends AppCompatTextView {

    private static final int TIME_TICK = 1;

    private String cityId;

    private String cityName;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .build();

        final Request request = new Request.Builder()
                .url("http://t.weather.sojson.com/api/weather/city/" + cityId)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                String responseString = response.body().string();
                System.out.println("weather===> "+responseString);
                WeatherResult weatherBean = new Gson().fromJson(responseString, WeatherResult.class);
                StringBuilder weatherTextBuilder = new StringBuilder();
                WeatherResult.DataBean.ForecastBean forecast = weatherBean.getData().getForecast().get(0);

                weatherTextBuilder.append(cityName).append(" ")
                        .append(forecast.getType()).append(" ")
                        .append(forecast.getLow().split(" ")[1]).append("~").append(forecast.getHigh().split(" ")[1]);

                Message message = Message.obtain(handler, TIME_TICK, 0, 0, weatherTextBuilder.toString());
                message.sendToTarget();
            }
        });
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == TIME_TICK) {
                setText(msg.obj.toString());
            }

        }
    };


    public WeatherTextViewWrapper(Context context) {
        this(context, null);
    }

    public WeatherTextViewWrapper(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherTextViewWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setId(View.generateViewId());
    }


}
