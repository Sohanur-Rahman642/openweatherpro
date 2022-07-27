package com.example.openweathertest.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import com.example.openweathertest.room.dao.WeatherDao;
import com.example.openweathertest.room.data.DbWeatherList;

@Database(entities = {
        DbWeatherList.class
},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";
    private static final String DATABASE_NAME = "weatherdb";
    private static AppDatabase mInstance;

    public static AppDatabase getInstance(Context context) {
        if (mInstance == null) {
            synchronized (AppDatabase.class) {
                mInstance = Room.databaseBuilder(
                        context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return mInstance;
    }


//    // below line is to create a callback for our room database.
//    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            // this method is called when database is created
//            // and below line is to populate our data.
//            new PopulateDbAsyncTask(mInstance).execute();
//        }
//    };
//
//    // we are creating an async task class to perform task in background.
//    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
//        PopulateDbAsyncTask(AppDatabase instance) {
//            WeatherDao dao = instance.weatherDao();
//        }
//        @Override
//        protected Void doInBackground(Void... voids) {
//            return null;
//        }
//    }


    public abstract WeatherDao weatherDao();

}
