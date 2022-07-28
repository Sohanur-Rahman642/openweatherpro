package com.example.openweathertest.view.adapter;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.example.openweathertest.constants.Constants;
import com.example.openweathertest.databinding.ActivityItemBinding;
import com.example.openweathertest.databinding.ActivityMainBinding;
import com.example.openweathertest.room.data.DbWeatherList;
import com.example.openweathertest.utils.DateFormatUtils;

import java.util.ArrayList;
import java.util.List;

public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.WeatherListViewHolder>{

    private ActivityItemBinding activityItemBinding;
    private List<DbWeatherList> dbWeatherLists = new ArrayList<>();
    private int layoutWeatherListId;
    private Context context;

    public WeatherListAdapter(Context context, int layoutWeatherListId, List<DbWeatherList> dbWeatherLists) {
        this.context = context;
        this.layoutWeatherListId = layoutWeatherListId;
        this.dbWeatherLists = dbWeatherLists;
    }


    @NonNull
    @Override
    public WeatherListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        activityItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(context),
                        layoutWeatherListId, parent, false);

        return new WeatherListViewHolder(activityItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherListViewHolder holder, int position) {
        DbWeatherList dbWeatherList = dbWeatherLists.get(position);
        holder.activityItemBinding.setDbWeather(dbWeatherList);

        DrawableCrossFadeFactory factory =
                new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();

        Glide.with(context)
                .load(Constants.BASE_IMAGE_URL+dbWeatherList.getIcon()+".png")
                .transition(withCrossFade(factory))
                .into(activityItemBinding.icon);
        String day = DateFormatUtils.getInstance(context).getDay(dbWeatherList.getDateTimeText());
        String time = DateFormatUtils.getInstance(context).getConvertedTime(dbWeatherList.getDateTimeText());
        holder.activityItemBinding.day.setText(day);
        holder.activityItemBinding.time.setText(time);
    }


    @Override
    public int getItemCount() {
        if(dbWeatherLists != null && !dbWeatherLists.isEmpty()){
            return dbWeatherLists.size();
        }

        return 0;
    }

    public void setDbWeatherLists(List<DbWeatherList> dbWeatherLists) {
        this.dbWeatherLists = dbWeatherLists;
        notifyDataSetChanged();
    }


    class WeatherListViewHolder extends RecyclerView.ViewHolder {
        ActivityItemBinding activityItemBinding;

        public WeatherListViewHolder(@NonNull ActivityItemBinding activityItemBinding) {
            super(activityItemBinding.getRoot());

            this.activityItemBinding = activityItemBinding;
        }
    }
}
