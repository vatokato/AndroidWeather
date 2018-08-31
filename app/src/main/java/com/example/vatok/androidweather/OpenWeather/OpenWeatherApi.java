package com.example.vatok.androidweather.OpenWeather;

import com.example.vatok.androidweather.WeatherItem.WeatherItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//http://api.openweathermap.org/data/2.5/weather?q=Moscow&APPID=fdac0291ad48feaa00664fe96728a7ec&lang=ru
public interface OpenWeatherApi {
//    @GET("/weather/{city}/")
//    Call<WeatherItem> getData(@Path("city") String cityName);

    @GET("/data/2.5/weather")
    Call<WeatherItem> getData(@Query("id") int cityId, @Query("APPID") String appid, @Query("lang") String lang, @Query("units") String units);

    @GET("/data/2.5/weather")
    Call<WeatherItem> getData(@Query("q") String cityName, @Query("APPID") String appid, @Query("lang") String lang, @Query("units") String units);
    @GET("/data/2.5/weather")
    Call<WeatherItem> getData(@Query("q") String cityName, @Query("APPID") String appid);
}
