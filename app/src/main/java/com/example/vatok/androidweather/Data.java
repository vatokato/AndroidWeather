package com.example.vatok.androidweather;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import timber.log.Timber;

public class Data implements Serializable {
    private String name;
    private boolean showType;
    private boolean showWind;
    private boolean showPressure;
    private boolean showHumidity;
    private boolean showLogo;

    private String[] cities;
    private List<CityInfo> cityInfoArrayList;
    private boolean isMasterDetail;
    private int currentCityId;


    public Data(String name, MainActivity context) {
        this.name = name;
        this.cityInfoArrayList = new ArrayList<>();
        this.cities = context.getResources().getStringArray(R.array.cities);
        this.currentCityId = -1;

        this.showType=true;
        this.showWind=false;
        this.showPressure=false;
        this.showHumidity=false;
        this.showLogo=true;

        Timber.d("gen");

        if(Paper.book().contains("dataRV"))
        {
            cityInfoArrayList = Paper.book().read("dataRV");
        }
        else {
            for (int i = 0; i < cities.length; i++) {
                cityInfoArrayList.add(new CityInfo(
                                i,
                                context
                        )
                );
            }
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

    public List<CityInfo> getCityInfoArrayList() {
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

    public boolean isShowLogo() {
        return showLogo;
    }

    public void setShowLogo(boolean showLogo) {
        this.showLogo = showLogo;
    }

    public static void saveData(List<CityInfo> items)
    {
        Paper.book().write("dataRV", items);
    }

}
