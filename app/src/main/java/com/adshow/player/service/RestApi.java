package com.adshow.player.service;

import com.adshow.player.service.request.LoginRequest;
import com.adshow.player.service.response.RestResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface RestApi {


    @POST("/auth/user/appLogin")
    Call<RestResult<Object>> login(@Body LoginRequest loginRequest);


    @POST("/auth/user/appLogin")
    Call<RestResult<Object>> getPlayList(@Body LoginRequest loginRequest);


}
