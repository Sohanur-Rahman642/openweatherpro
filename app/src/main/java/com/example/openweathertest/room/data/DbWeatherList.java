package com.example.openweathertest.room.data;



import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dbWeatherList")
public class DbWeatherList {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="dt")
    private long timeStamp;
    @ColumnInfo(name = "temp")
    private float temp;
    @ColumnInfo(name = "feels_like")
    private float feelsLike;
    @ColumnInfo(name = "pressure")
    private int pressure;
    @ColumnInfo(name = "sea_level")
    private int seaLevel;
    @ColumnInfo(name = "ground_level")
    private int groundLevel;
    @ColumnInfo(name = "humidity")
    private int humidity;
    @ColumnInfo(name = "weather_main")
    private String weatherMain;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "icon")
    private String icon;
    @ColumnInfo(name = "cloudiness")
    private int cloudiness;
    @ColumnInfo(name = "wind_speed")
    private float windSpeed;
    @ColumnInfo(name = "pop")
    private float pop;
    @ColumnInfo(name = "3h")
    private float rainVolume;
    @ColumnInfo(name = "datetime")
    private String dateTimeText;
    @ColumnInfo(name = "city_name")
    private String cityName;
    @ColumnInfo(name = "country")
    private String country;
    @ColumnInfo(name = "sunrise")
    private long sunrise;
    @ColumnInfo(name = "sunset")
    private long sunset;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

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

    public String getWeatherMain() {
        return weatherMain;
    }

    public void setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(int cloudiness) {
        this.cloudiness = cloudiness;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public float getPop() {
        return pop;
    }

    public void setPop(float pop) {
        this.pop = pop;
    }

    public float getRainVolume() {
        return rainVolume;
    }

    public void setRainVolume(float rainVolume) {
        this.rainVolume = rainVolume;
    }

    public String getDateTimeText() {
        return dateTimeText;
    }

    public void setDateTimeText(String dateTimeText) {
        this.dateTimeText = dateTimeText;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }
}
