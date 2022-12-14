package com.example.openweathertest.application;

import android.app.Application;
import android.util.Log;

import com.example.openweathertest.constants.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class OpenWeather extends Application {
    final static String TAG = OpenWeather.class.getSimpleName();
    private static OpenWeather mInstance;
    private static Retrofit retrofit = null;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized OpenWeather getInstance(){
        return mInstance;
    }

    public static Retrofit getRetrofitClient() {

        if (retrofit == null) {
            okhttp3.OkHttpClient client = new okhttp3.OkHttpClient.Builder().build();

            retrofit = new Retrofit.Builder()
                    .client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Constants.BASE_URL)
                    .build();
        }

        Log.e(TAG, "getRetrofitClient: " +retrofit.baseUrl());
        return retrofit;
    }
}
