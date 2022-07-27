package com.example.openweathertest.model.submodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CloudSection {
    @SerializedName("all")
    @Expose
    private int cloudiness;

    public int getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(int cloudiness) {
        this.cloudiness = cloudiness;
    }
}
