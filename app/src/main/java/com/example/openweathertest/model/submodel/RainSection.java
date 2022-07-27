package com.example.openweathertest.model.submodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RainSection {
    @SerializedName("3h")
    @Expose
    private float rainVolume;

    public float getRainVolume() {
        return rainVolume;
    }

    public void setRainVolume(float rainVolume) {
        this.rainVolume = rainVolume;
    }
}
