package com.example.openweathertest.model.submodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainSection {
    @SerializedName("temp")
    @Expose
    private float temp;
    @SerializedName("feels_like")
    @Expose
    private float feelsLike;
    @SerializedName("temp_min")
    @Expose
    private float tempMin;
    @SerializedName("temp_max")
    @Expose
    private float tempMax;
    @SerializedName("pressure")
    @Expose
    private int pressure;
    @SerializedName("sea_level")
    @Expose
    private int seaLevel;
    @SerializedName("grnd_level")
    @Expose
    private int groundLevel;
    @SerializedName("humidity")
    @Expose
    private int humidity;
    @SerializedName("temp_kf")
    @Expose
    private float tempKF;

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(float feelsLike) {
        this.feelsLike = feelsLike;
    }

    public float getTempMin() {
        return tempMin;
    }

    public void setTempMin(float tempMin) {
        this.tempMin = tempMin;
    }

    public float getTempMax() {
        return tempMax;
    }

    public void setTempMax(float tempMax) {
        this.tempMax = tempMax;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(int seaLevel) {
        this.seaLevel = seaLevel;
    }

    public int getGroundLevel() {
        return groundLevel;
    }

    public void setGroundLevel(int groundLevel) {
        this.groundLevel = groundLevel;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public float getTempKF() {
        return tempKF;
    }

    public void setTempKF(float tempKF) {
        this.tempKF = tempKF;
    }
}
