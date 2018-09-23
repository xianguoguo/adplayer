package com.adshow.player.service;

import com.adshow.player.service.response.WeatherResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RestWeatherApi {

    @GET("/api/weather/city/{cityId}")
    Call<WeatherResult> getWeather(@Path("cityId") String cityId);

}
