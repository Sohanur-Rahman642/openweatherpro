package com.example.openweathertest.repo;

import androidx.lifecycle.MutableLiveData;

import com.example.openweathertest.application.OpenWeather;
import com.example.openweathertest.constants.Constants;
import com.example.openweathertest.model.WeatherModel;
import com.example.openweathertest.model.response.WeatherResponse;
import com.example.openweathertest.server.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherListRepository {

    private final String TAG = getClass().getSimpleName();

    public MutableLiveData<List<WeatherModel>> geListOfWeathers(double lat, double lon){
        final MutableLiveData<List<WeatherModel>> mutableLiveData = new MutableLiveData<>();

        ApiInterface apiInterface = OpenWeather.getRetrofitClient().create(ApiInterface.class);

        apiInterface.getWeatherForecastsOfFiveDays(lat, lon, Constants.API_KEY).enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if(response.code() == 200 && response.isSuccessful()){
                    if(response.body().getCode().equalsIgnoreCase("200")){
                        mutableLiveData.setValue(response.body().getWeatherModelList());
                    }else {
                        mutableLiveData.setValue(null);
                    }
                }else{
                    mutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                mutableLiveData.setValue(null);
            }
        });

        return mutableLiveData;
    }

}

