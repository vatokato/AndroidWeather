package com.example.vatok.androidweather;

import java.io.Serializable;
import java.util.Random;

public class UserData implements Serializable {
    private String name;
    private String city;

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
        this.weatherTemperature = new Random().nextInt(60)-30;
    }

    public String getWeatherType() {
        return this.weatherType;
    }

    public void setWeatherType(String[] weatherTypes) {
        this.weatherType = weatherTypes[new Random().nextInt(weatherTypes.length)];
    }
}
