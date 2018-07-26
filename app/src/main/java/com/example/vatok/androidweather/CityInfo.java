package com.example.vatok.androidweather;

import android.graphics.Color;
import java.io.Serializable;
import java.util.Random;

public class CityInfo implements Serializable {
    private String name;
    private int temperature;
    private int temperatureColor;
    private String type;
    private int wind;
    private int pressure;
    private int humidity;

    public CityInfo(String name, String[] types) {
        this.name = name;
        this.temperature = new Random().nextInt(70)-30;
        this.type = types[new Random().nextInt(types.length)];
        this.wind = new Random().nextInt(30);
        this.pressure = new Random().nextInt(100)+700;
        this.humidity = new Random().nextInt(80)+20;

        int red = 0, green = 0, blue = 0;
        if(this.temperature>24) {
            red = (int)((float)255/40*(float)this.temperature);
        }
        else if(15 < this.temperature && this.temperature < 25) {
            green = 255;
        }
        else {
            blue = (int)((float)255/45*(float)-(this.temperature-15));
        }
        this.temperatureColor = Color.rgb(red, green, blue);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getTemperatureColor() {
        return temperatureColor;
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

}
