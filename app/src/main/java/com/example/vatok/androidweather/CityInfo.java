package com.example.vatok.androidweather;

import java.io.Serializable;
import java.util.Random;

public class CityInfo implements Serializable {
    private String title;
    private int id;
    private int temperature;
    private String type;
    private int wind;
    private int pressure;
    private int humidity;
    private boolean favorite;
    private boolean active;
    private String logoUrl;
    private String picUrl;

    public CityInfo(int position, MainActivity context) {
        String[] cities = context.getResources().getStringArray(R.array.cities);
        this.id = position;
        this.title = cities[position];
        this.temperature = new Random().nextInt(70)-30;
        String[] types = context.getResources().getStringArray(R.array.weatherTypes);
        this.type = types[new Random().nextInt(types.length)];
        this.wind = new Random().nextInt(30);
        this.pressure = new Random().nextInt(100)+700;
        this.humidity = new Random().nextInt(80)+20;
        this.favorite = false;
        this.active = false;

        String[] logos = context.getResources().getStringArray(R.array.cityLogos);
        this.logoUrl = logos[position];

        String[] pics = context.getResources().getStringArray(R.array.cityPics);
        this.picUrl = pics[position];
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getTemperatureString() {
        return String.format("%s%s%s", temperature >= 0 ? "+" : "", temperature, "°");
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWind() {
        return String.format("%s %s", wind, "м/c");
    }

    public String getPressure() {
        return String.format("%s %s", pressure, "мм.рт.ст.");
    }

    public String getHumidity() {
        return String.format("%s %s", humidity, "%");
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
    public void setPicUrl(String picUrl) {this.picUrl = picUrl;  }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getId() {
        return id;
    }
}
