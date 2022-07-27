package com.example.openweathertest.model.submodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WindSection {
    @SerializedName("speed")
    @Expose
    private float speed;
    @SerializedName("deg")
    @Expose
    private int degree;
    @SerializedName("gust")
    @Expose
    private float gust;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public float getGust() {
        return gust;
    }

    public void setGust(float gust) {
        this.gust = gust;
    }
}
