package com.example.openweathertest.worker;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.openweathertest.application.OpenWeather;
import com.example.openweathertest.constants.Constants;
import com.example.openweathertest.model.City;
import com.example.openweathertest.model.WeatherModel;
import com.example.openweathertest.model.response.WeatherResponse;
import com.example.openweathertest.room.AppDatabase;
import com.example.openweathertest.room.data.DbWeatherList;
import com.example.openweathertest.room.executors.AppExecutors;
import com.example.openweathertest.server.ApiInterface;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

public class WeatherWorker extends Worker {

    private static final String TAG = "WeatherWorker";
    public static final String MY_KEY_DATA_FROM_WORKER = "MY_KEY_DATA_FROM_WORKER";
    private FusedLocationProviderClient locationClient;
    private Location location;
    private LocationCallback locationCallback;
    private Map<String, Double> locationMap;

    public WeatherWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        locationMap = new HashMap<>();
    }

    @NonNull
    @Override
    public Result doWork() {
        getUpdatedLatLong();
        return Result.success();
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


    private void getUpdatedLatLong() {
        locationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
            }
        };

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(Constants.UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(Constants.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        try {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationClient
                    .getLastLocation()
                    .addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if(task.isSuccessful()){
                                location = task.getResult();
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();
                                onCallWeatherApi(latitude, longitude);
                            }
                            locationClient.removeLocationUpdates(locationCallback);
                        }
                    });
        }
        catch (Exception e){
            Log.e(TAG, "getUpdatedLatLong:e " +e.getMessage());
        }

        try {
            locationClient.requestLocationUpdates(mLocationRequest, null);
        } catch (SecurityException unlikely) {
            //Utils.setRequestingLocationUpdates(this, false);
            Log.e(TAG, "Lost location permission. Could not request updates. " + unlikely);
        }
    }

    private void onCallWeatherApi(double latitude, double longitude) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    ApiInterface apiInterface = OpenWeather.getRetrofitClient().create(ApiInterface.class);
                    Response<WeatherResponse> response = apiInterface.getWeatherForecastsOfFiveDays(latitude, longitude, Constants.API_KEY).execute();
                    if(response.isSuccessful()) {
                        WeatherResponse weatherResponse = response.body();

                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                clearWeathersFromRoom();

                                addWeathersIntoRoom(weatherResponse);

                            }
                        });
                    }
                }catch (IOException e){
                    Log.e(TAG, "onCallWeatherApi: e" +e.getMessage());
                }
            }
        });

    }
}
