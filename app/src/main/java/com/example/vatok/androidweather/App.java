package com.example.vatok.androidweather;

import android.app.Application;

import io.paperdb.Paper;
import timber.log.Timber;

public class App extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        if (BuildConfig.DEBUG)
        {
            Timber.plant(new Timber.DebugTree());
            Paper.init(this);
        }
    }
}
