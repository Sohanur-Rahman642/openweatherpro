package com.example.openweathertest.repo;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.openweathertest.model.WeatherModel;
import com.example.openweathertest.room.AppDatabase;
import com.example.openweathertest.room.data.DbWeatherList;
import com.example.openweathertest.room.executors.AppExecutors;
import com.example.openweathertest.room.model.Weather;

import java.util.ArrayList;
import java.util.List;

public class WeatherCacheRepository {
    private final String TAG = getClass().getSimpleName();
    private Context context;
    private LiveData<List<DbWeatherList>> data;

    public WeatherCacheRepository(Context context) {
        this.context = context;
    }

    public LiveData<List<DbWeatherList>> getWeatherDataFromRoom(){
      return AppDatabase.getInstance(context).weatherDao().fetchWeathersFromRoom();
    }
}
