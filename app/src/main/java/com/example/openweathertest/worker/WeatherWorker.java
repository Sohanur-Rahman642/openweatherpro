package com.example.openweathertest.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.openweathertest.application.OpenWeather;
import com.example.openweathertest.constants.Constants;
import com.example.openweathertest.model.WeatherModel;
import com.example.openweathertest.model.response.WeatherResponse;
import com.example.openweathertest.room.AppDatabase;
import com.example.openweathertest.room.data.DbWeatherList;
import com.example.openweathertest.room.executors.AppExecutors;
import com.example.openweathertest.server.ApiInterface;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class WeatherWorker extends Worker {

    public static final String MY_KEY_DATA_FROM_WORKER = "MY_KEY_DATA_FROM_WORKER";

    public WeatherWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        ApiInterface apiInterface = OpenWeather.getRetrofitClient().create(ApiInterface.class);
        try {
            Response<WeatherResponse> response = apiInterface.getWeatherForecastsOfFiveDays(35, 139, Constants.API_KEY).execute();
            if(response.isSuccessful()){
                WeatherResponse weatherResponse = response.body();

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        clearWeathersFromRoom();

                        addWeathersIntoRoom(weatherResponse);

                    }
                });


//                Data output = new Data.Builder()
//                            .putString(MY_KEY_DATA_FROM_WORKER, json)
//                        .build();

                return Result.success();
            }else {
                return Result.retry();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    private void addWeathersIntoRoom(WeatherResponse weatherResponse) {
        DbWeatherList dbWeatherList = new DbWeatherList();
        for(WeatherModel weatherModel : weatherResponse.getWeatherModelList()){
            dbWeatherList.setTemp(weatherModel.getMainSection().getTemp());
            dbWeatherList.setFeelsLike(weatherModel.getMainSection().getFeelsLike());
            dbWeatherList.setPressure(weatherModel.getMainSection().getPressure());
            dbWeatherList.setSeaLevel(weatherModel.getMainSection().getSeaLevel());
            dbWeatherList.setGroundLevel(weatherModel.getMainSection().getGroundLevel());
            dbWeatherList.setHumidity(weatherModel.getMainSection().getHumidity());
            dbWeatherList.setWeatherMain(weatherModel.getWeatherSections().get(0).getLabel());
            dbWeatherList.setDescription(weatherModel.getWeatherSections().get(0).getDescription());
            dbWeatherList.setIcon(weatherModel.getWeatherSections().get(0).getIcon());
            dbWeatherList.setCloudiness(weatherModel.getCloudSection().getCloudiness());
            dbWeatherList.setWindSpeed(weatherModel.getWindSection().getSpeed());
            dbWeatherList.setDateTimeText(weatherModel.getDateTimeText());
            dbWeatherList.setRainVolume(weatherModel.getRainSection() != null ? weatherModel.getRainSection().getRainVolume() : 0f);
            dbWeatherList.setPop(weatherModel.getPop());

            dbWeatherList.setCityName(weatherResponse.getCity().getName());
            dbWeatherList.setCountry(weatherResponse.getCity().getCountry());
            dbWeatherList.setSunrise(weatherResponse.getCity().getSunrise());
            dbWeatherList.setSunset(weatherResponse.getCity().getSunset());

            AppDatabase.getInstance(getApplicationContext()).weatherDao().insertWeatherData(dbWeatherList);

        }
    }

    private void clearWeathersFromRoom() {
        int count = AppDatabase.getInstance(getApplicationContext()).weatherDao().totalWeatherData();

        if(count > 0){
            List<DbWeatherList> dbWeatherLists = AppDatabase.getInstance(getApplicationContext()).weatherDao().findAlreadySavedData();
            if(!dbWeatherLists.isEmpty()){
                for(DbWeatherList dbWeatherList : dbWeatherLists){
                    AppDatabase.getInstance(getApplicationContext()).weatherDao().deleteWeatherInfo(dbWeatherList);
                }
            }
        }
    }
}
