package com.example.openweathertest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.example.openweathertest.databinding.ActivityMainBinding;
import com.example.openweathertest.model.City;
import com.example.openweathertest.model.WeatherModel;
import com.example.openweathertest.repo.WeatherCacheRepository;
import com.example.openweathertest.repo.WeatherListRepository;
import com.example.openweathertest.room.data.DbWeatherList;
import com.example.openweathertest.room.model.Weather;
import com.example.openweathertest.viewmodel.WeatherListViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private WeatherCacheRepository weatherCacheRepository;
    private WeatherListViewModel weatherListViewModel;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        weatherCacheRepository = new WeatherCacheRepository(getApplicationContext());

        WeatherListViewModel.Factory factory = new WeatherListViewModel.Factory(weatherCacheRepository);

        weatherListViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) factory)
                .get(WeatherListViewModel.class);

        observeWeatherListFetchTask();
    }

    private void observeWeatherListFetchTask() {
        Log.e(TAG, "observeWeatherListFetchTask: 11111111111111" );

        weatherListViewModel.loadDataFromWorker(this);
        weatherListViewModel.getListOfWeathers().observe(this, dbWeatherLists -> {
            City city = new City();
            city.setName(dbWeatherLists.get(0).getCityName());
            city.setCountry(dbWeatherLists.get(0).getCountry());
            city.setSunrise(dbWeatherLists.get(0).getSunrise());
            city.setSunset(dbWeatherLists.get(0).getSunset());
        });
    }
}