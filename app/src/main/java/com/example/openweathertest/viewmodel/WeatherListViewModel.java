package com.example.openweathertest.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.openweathertest.model.WeatherModel;
import com.example.openweathertest.repo.WeatherCacheRepository;
import com.example.openweathertest.repo.WeatherListRepository;
import com.example.openweathertest.room.data.DbWeatherList;
import com.example.openweathertest.room.model.Weather;
import com.example.openweathertest.worker.WeatherWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WeatherListViewModel extends ViewModel {

    private WeatherListRepository weatherListRepository;
    private WeatherCacheRepository weatherCacheRepository;
    private MutableLiveData<List<WeatherModel>> mutableLiveData;
    private final MutableLiveData<String> myLiveData = new MutableLiveData<String>();
    private static final String TAG = "WeatherListViewModel";
    private LiveData<List<DbWeatherList>> dbWeatherListLiveData;

    public WeatherListViewModel(WeatherCacheRepository weatherCacheRepository) {
        this.weatherCacheRepository = weatherCacheRepository;
    }



    public void startPeriodicWorker(LifecycleOwner lifecycleOwner) {
        PeriodicWorkRequest periodicWork = new PeriodicWorkRequest.Builder(WeatherWorker.class, 12, TimeUnit.HOURS)
                .addTag(TAG)
                .build();

        WorkManager mWorkManager = WorkManager.getInstance();
        mWorkManager.enqueueUniquePeriodicWork("Weather", ExistingPeriodicWorkPolicy.REPLACE, periodicWork);
    }


    public LiveData<List<DbWeatherList>> getListOfWeathers(){
        return weatherCacheRepository.getWeatherDataFromRoom();
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private final WeatherCacheRepository weatherCacheRepository;


        public Factory(WeatherCacheRepository weatherCacheRepository) {
            this.weatherCacheRepository = weatherCacheRepository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new WeatherListViewModel(weatherCacheRepository);
        }
    }
}
