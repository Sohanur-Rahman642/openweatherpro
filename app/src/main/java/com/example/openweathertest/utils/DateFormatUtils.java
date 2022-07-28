package com.example.openweathertest.utils;

import android.content.Context;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatUtils {
    private Context context;
    private static DateFormatUtils instance;
    private static final String TAG = "DateFormatUtils";

    public DateFormatUtils(Context context) {
        this.context = context;
    }

    public static DateFormatUtils getInstance(Context context){
        if (instance == null){
            instance = new DateFormatUtils(context);
        }
        return instance;
    }

    public String getDay(String dateTime){
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            String dayWeekText = new SimpleDateFormat("EEEE").format(date);
            return dayWeekText;
        }catch (ParseException e){
            return "Not found :(";
        }
    }

    public String getConvertedTime(String datetime){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(datetime);
            SimpleDateFormat sdf2 = new SimpleDateFormat("hh.mm aa");

            return sdf2.format(date);
        }catch(ParseException e){
            return ":(";
        }
    }

    public boolean isTimeForNotification(String datetime){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, 5);
            String newTime = sdf.format(cal.getTime());
            return newTime.equalsIgnoreCase(datetime);
        }catch(Exception e){
            return false;
        }
    }


    public boolean isValidModel(String datetime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mms");
        try {
            Calendar cal = Calendar.getInstance();

            Date d1 = sdf.parse(String.valueOf(cal.getTime()));
            Date d2 = sdf.parse(datetime);

            long differenceInTime = d1.getTime() - d2.getTime();
            long differenceInHours = (differenceInTime / (1000 * 60 * 60)) % 24;

            Log.e(TAG, "isValidModel: differenceInHours " +differenceInHours );
            return differenceInHours <= 3;

        }catch (ParseException e){
            return false;
        }
    }

}
