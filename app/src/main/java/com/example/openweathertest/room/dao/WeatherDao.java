package com.example.openweathertest.room.dao;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.openweathertest.room.data.DbWeatherList;

import java.util.List;

@Dao
public interface WeatherDao {

    @Insert()
    void insertWeatherData(DbWeatherList dbWeatherList);

    @Query("SELECT * FROM dbWeatherList")
    List<DbWeatherList> findAlreadySavedData();

    @Query("SELECT * FROM dbWeatherList")
    LiveData<List<DbWeatherList>> fetchWeathersFromRoom();

    @Query("SELECT Count(*) FROM dbWeatherList")
    int totalWeatherData();

    @Delete
    void deleteWeatherInfo(DbWeatherList dbWeatherList);
}
