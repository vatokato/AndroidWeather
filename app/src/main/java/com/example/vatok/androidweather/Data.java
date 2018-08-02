package com.example.vatok.androidweather;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class Data implements Serializable {
    private String name;
    private boolean showType;
    private boolean showTypePic;
    private boolean showWind;
    private boolean showPressure;
    private boolean showHumidity;

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
        this.showTypePic=true;
        this.showWind=false;
        this.showPressure=false;
        this.showHumidity=false;

        for (int i = 0; i < cities.length; i++) {
            cityInfoArrayList.add(new CityInfo(
                            i,
                            context
                    )
            );
        }
        this.save();
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
    public CityInfo getInfo(int cityId) {
        for(int i = 0; i < cityInfoArrayList.size(); i++)
        {
            if(i==cityId)
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

    public boolean isShowTypePic() {
        return showTypePic;
    }

    public void setShowTypePic(boolean showTypePic) {
        this.showTypePic = showTypePic;
    }

    public void save()
    {
        Paper.book().write("data", this);
    }

}
