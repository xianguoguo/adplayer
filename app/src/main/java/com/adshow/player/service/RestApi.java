package com.adshow.player.service;

import com.adshow.player.service.request.LoginRequest;
import com.adshow.player.service.response.RestResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface RestApi {

    @FormUrlEncoded
    @POST("/auth/login")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Call<RestResult<Object>> login(@Header("Device-Mac") String mac, @Field("username") String username, @Field("password") String password);


    @Headers("Content-Type:application/json; charset=utf-8")
    @POST("/auth/user/appLogin")
    Call<RestResult<Object>> getPlayList(@Body LoginRequest loginRequest);


}
