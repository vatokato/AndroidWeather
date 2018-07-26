package com.example.vatok.androidweather;

import android.graphics.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import timber.log.Timber;

public class Data implements Serializable {
    private String name;
    private boolean showType;
    private boolean showWind;
    private boolean showPressure;
    private boolean showHumidity;

    private String[] cities;
    private ArrayList<CityInfo> cityInfoArrayList;
    private boolean isMasterDetail;
    private int currentCityId;

    public Data(String[] cities, String[] weatherTypes) {
        cityInfoArrayList = new ArrayList<>();
        this.cities = cities;


        showType=true;
        showWind=false;
        showPressure=false;
        showHumidity=false;

        for (int i = 0; i < cities.length; i++) {
            cityInfoArrayList.add(new CityInfo(
                    cities[i],
                    new Random().nextInt(70)-30,
                    weatherTypes[new Random().nextInt(weatherTypes.length)])
            );
        }
    }

    public boolean isMasterDetail() {
        return isMasterDetail;
    }

    public void setMasterDetail(boolean masterDetail) {
        isMasterDetail = masterDetail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getCities() {
        return cities;
    }


    public void setCityInfoArrayList(ArrayList<CityInfo> cityInfoArrayList) {
        this.cityInfoArrayList = cityInfoArrayList;
    }

    public ArrayList<CityInfo> getCityInfoArrayList() {
        return cityInfoArrayList;
    }

    public CityInfo getInfo() {
        for(int i = 0; i < cityInfoArrayList.size(); i++)
        {
            if(i==this.currentCityId)
            {
                return cityInfoArrayList.get(i);
            }
        }
        return null;
    }

    public int getCurrentCityId() {
        return currentCityId;
    }

    public void setCurrentCityId(int currentCityId) {
        this.currentCityId = currentCityId;
    }

    public boolean isShowType() {
        return showType;
    }

    public void setShowType(boolean showType) {
        this.showType = showType;
    }

    public boolean isShowWind() {
        return showWind;
    }

    public void setShowWind(boolean showWind) {
        this.showWind = showWind;
    }

    public boolean isShowPressure() {
        return showPressure;
    }

    public void setShowPressure(boolean showPressure) {
        this.showPressure = showPressure;
    }

    public boolean isShowHumidity() {
        return showHumidity;
    }

    public void setShowHumidity(boolean showHumidity) {
        this.showHumidity = showHumidity;
    }
}
