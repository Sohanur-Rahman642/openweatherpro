package com.example.openweathertest.server;

import com.example.openweathertest.model.response.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("2.5/forecast")
    Call<WeatherResponse> getWeatherForecastsOfFiveDays(
        @Query("lat") double lat,
        @Query("lon") double lon,
        @Query("appid") String appid
    );
}
