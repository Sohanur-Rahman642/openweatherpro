package com.example.openweathertest.view;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.example.openweathertest.constants.Constants.PERMISSION_REQUEST_CODE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.example.openweathertest.R;
import com.example.openweathertest.constants.Constants;
import com.example.openweathertest.databinding.ActivityMainBinding;
import com.example.openweathertest.repo.WeatherCacheRepository;
import com.example.openweathertest.room.data.DbWeatherList;
import com.example.openweathertest.utils.DateFormatUtils;
import com.example.openweathertest.view.adapter.WeatherListAdapter;
import com.example.openweathertest.viewmodel.WeatherListViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private WeatherCacheRepository weatherCacheRepository;
    private WeatherListViewModel weatherListViewModel;
    private static final String TAG = "MainActivity";
    private WeatherListAdapter weatherListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        weatherCacheRepository = new WeatherCacheRepository(getApplicationContext());

        WeatherListViewModel.Factory factory = new WeatherListViewModel.Factory(weatherCacheRepository);

        weatherListViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) factory)
                .get(WeatherListViewModel.class);

        if (!checkLocationPermission()) {
            ActivityCompat.requestPermissions(this,
                    new String[]{ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }

        createNotificationChannel();

        observeWeatherListFetchTask();

        observeWeatherForNotification();

    }


    private void observeWeatherForNotification() {
        weatherListViewModel.getListOfWeathers().observe(this, dbWeatherLists -> {
            if(dbWeatherLists != null && !dbWeatherLists.isEmpty()){
                for (DbWeatherList dbWeatherList : dbWeatherLists){
                    if(dbWeatherList.getWeatherMain().equalsIgnoreCase("Rain")){
                        String datetime = dbWeatherList.getDateTimeText();
                        boolean isTime = DateFormatUtils.getInstance(this).isTimeForNotification(datetime);
                        if(isTime){
                            createNotification(dbWeatherList);
                        }
                    }
                }
            }
        });
    }

    private void createNotification(DbWeatherList dbWeatherList) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, Constants.CHANNEL_ID)
                .setSmallIcon(R.drawable.cloudyrain)
                .setContentTitle("Rain Alert!")
                .setContentText("There are possibilities of raining at " +
                        DateFormatUtils.getInstance(this).getConvertedTime(dbWeatherList.getDateTimeText()))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    private void observeWeatherListFetchTask() {
        Log.e(TAG, "observeWeatherListFetchTask: 11111111111111" );

        weatherListViewModel.startPeriodicWorker(this);


        weatherListViewModel.getListOfWeathers().observe(this, dbWeatherLists -> {
            if(dbWeatherLists != null && !dbWeatherLists.isEmpty()){
                LinearLayoutManager llm = new LinearLayoutManager(this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                weatherListAdapter = new WeatherListAdapter(MainActivity.this, R.layout.item_list_view, dbWeatherLists);
                binding.weatherListView.setAdapter(weatherListAdapter);
                binding.weatherListView.setLayoutManager(llm);
                weatherListAdapter.setDbWeatherLists(dbWeatherLists);
            }
        });
    }


    private boolean checkLocationPermission() {
        int result3 = ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION);
        int result4 = ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION);
        return result3 == PackageManager.PERMISSION_GRANTED &&
                result4 == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean coarseLocation = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean fineLocation = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (coarseLocation && fineLocation)
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    Constants.CHANNEL_ID,
                    "Example Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}