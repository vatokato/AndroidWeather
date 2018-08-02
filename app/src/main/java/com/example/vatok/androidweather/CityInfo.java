package com.example.vatok.androidweather;

import java.io.Serializable;

public class CityInfo implements Serializable {
    private String title;
    private int id;
    private int owID;
    private int temperature;
    private String type;
    private String typePic;
    private int wind;
    private int pressure;
    private int humidity;
    private boolean favorite;
    private boolean active;
    private String logoUrl;
    private String picUrl;

    public void setWind(int wind) {
        this.wind = wind;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public CityInfo(int position, MainActivity context) {
        String[] cities = context.getResources().getStringArray(R.array.cities);
        int[] owIds = context.getResources().getIntArray(R.array.cityIdsOWM);
        this.id = position;
        this.title = cities[position];
        this.owID = owIds[position];
        this.temperature = -100;
        this.type = "unknown";
        this.typePic = "";
        this.wind = -100;
        this.pressure = -100;
        this.humidity = -100;
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

    public int getOwID() {
        return owID;
    }

    public void setOwID(int owID) {
        this.owID = owID;
    }

    public String getTypePic() {
        return typePic;
    }

    public void setTypePic(String typePic) {
        this.typePic = typePic;
    }
}
