package com.example.vatok.androidweather;

import android.graphics.Color;

import java.io.Serializable;
import java.util.Random;

import timber.log.Timber;

public class UserData implements Serializable {
    private String name;
    private String city;

    private int weatherTemperatureColor;
    private int weatherTemperature;
    private String weatherType;

    public UserData (String name, String city) {
        this.name = name;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWeatherTemperature() {
        return String.format("%s%s%s", weatherTemperature >= 0 ? "+" : "", weatherTemperature, "°");
    }

    public void setWeatherTemperature() {
        this.weatherTemperature = new Random().nextInt(70)-30;

        int red = 0, green = 0, blue = 0;

        if(this.weatherTemperature>24) {
            red = (int)((float)255/40*(float)this.weatherTemperature);
        }
        else if(15 < this.weatherTemperature && this.weatherTemperature < 25) {
            green = 255;
        }
        else {
            blue = (int)((float)255/45*(float)-(this.weatherTemperature-15));
        }
        this.weatherTemperatureColor = Color.rgb(red, green, blue);
    }

    public String getWeatherType() {
        return this.weatherType;
    }

    public void setWeatherType(String[] weatherTypes) {
        this.weatherType = weatherTypes[new Random().nextInt(weatherTypes.length)];
    }

    public int getWeatherTemperatureColor() {
        return weatherTemperatureColor;
    }
}
