package com.example.vatok.androidweather;

import android.app.Application;

import com.example.vatok.androidweather.OpenWeather.OpenWeatherApi;

import io.paperdb.Paper;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class OpenWeatherApp extends Application {

    private static OpenWeatherApi openWeatherApi;
    private Retrofit retrofit;
    static final String APPID = "fdac0291ad48feaa00664fe96728a7ec";

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        openWeatherApi = retrofit.create(OpenWeatherApi.class);


        if (BuildConfig.DEBUG)
        {
            Timber.plant(new Timber.DebugTree());
            Paper.init(this);
        }
    }


    public static OpenWeatherApi getApi() {
        return openWeatherApi;
    }

    public static String getAPPID() {
        return APPID;
    }
}