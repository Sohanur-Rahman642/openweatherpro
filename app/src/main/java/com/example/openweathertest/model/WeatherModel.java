package com.example.openweathertest.model;

import com.example.openweathertest.model.submodel.CloudSection;
import com.example.openweathertest.model.submodel.MainSection;
import com.example.openweathertest.model.submodel.RainSection;
import com.example.openweathertest.model.submodel.WeatherSection;
import com.example.openweathertest.model.submodel.WindSection;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherModel {
    @SerializedName("dt")
    @Expose
    private long timeStamp;
    @SerializedName("main")
    @Expose
    private MainSection mainSection;
    @SerializedName("weather")
    @Expose
    private List<WeatherSection> weatherSections;
    @SerializedName("clouds")
    @Expose
    private CloudSection cloudSection;
    @SerializedName("wind")
    @Expose
    private WindSection windSection;
    @SerializedName("visibility")
    @Expose
    private int visibility;
    @SerializedName("pop")
    @Expose
    private float pop;
    @SerializedName("rain")
    @Expose
    private RainSection rainSection;
    @SerializedName("dt_txt")
    @Expose
    private String dateTimeText;

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public MainSection getMainSection() {
        return mainSection;
    }

    public void setMainSection(MainSection mainSection) {
        this.mainSection = mainSection;
    }

    public List<WeatherSection> getWeatherSections() {
        return weatherSections;
    }

    public void setWeatherSections(List<WeatherSection> weatherSections) {
        this.weatherSections = weatherSections;
    }

    public CloudSection getCloudSection() {
        return cloudSection;
    }

    public void setCloudSection(CloudSection cloudSection) {
        this.cloudSection = cloudSection;
    }

    public WindSection getWindSection() {
        return windSection;
    }

    public void setWindSection(WindSection windSection) {
        this.windSection = windSection;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public float getPop() {
        return pop;
    }

    public void setPop(float pop) {
        this.pop = pop;
    }

    public RainSection getRainSection() {
        return rainSection;
    }

    public void setRainSection(RainSection rainSection) {
        this.rainSection = rainSection;
    }

    public String getDateTimeText() {
        return dateTimeText;
    }

    public void setDateTimeText(String dateTimeText) {
        this.dateTimeText = dateTimeText;
    }

    @Override
    public String toString() {
        return "WeatherModel{" +
                "timeStamp=" + timeStamp +
                ", mainSection=" + mainSection +
                ", weatherSections=" + weatherSections +
                ", cloudSection=" + cloudSection +
                ", windSection=" + windSection +
                ", visibility=" + visibility +
                ", pop=" + pop +
                ", rainSection=" + rainSection +
                ", dateTimeText='" + dateTimeText + '\'' +
                '}';
    }
}
