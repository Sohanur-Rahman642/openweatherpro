package com.example.openweathertest.model.response;

import com.example.openweathertest.model.WeatherModel;
import com.example.openweathertest.model.submodel.CitySection;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {
    @SerializedName("cod")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private int message;
    @SerializedName("cnt")
    @Expose
    private int cnt;
    @SerializedName("list")
    @Expose
    private List<WeatherModel> weatherModelList;
    @SerializedName("city")
    @Expose
    private CitySection city;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<WeatherModel> getWeatherModelList() {
        return weatherModelList;
    }

    public void setWeatherModelList(List<WeatherModel> weatherModelList) {
        this.weatherModelList = weatherModelList;
    }

    public CitySection getCity() {
        return city;
    }

    public void setCity(CitySection city) {
        this.city = city;
    }
}
