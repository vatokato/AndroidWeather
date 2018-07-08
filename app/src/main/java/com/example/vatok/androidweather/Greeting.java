package com.example.vatok.androidweather;

import android.content.Context;
import java.util.Calendar;

public class Greeting {
    private int currentHour;
    Context context;

    public Greeting(Context context) {
        currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        this.context = context;
    }

    //для тестов, так и не получилось прикрутить юнит тесты, т.к. не смог передать туда контекст
    public Greeting(Context context, int hour) {
        currentHour = hour;
        this.context = context;
    }

    public String getText() {
        if (5 <= currentHour && currentHour < 12)
        {
            return String.format("%s %s", context.getString(R.string.goodMorning) , currentHour);
        }
        else if (12 <= currentHour && currentHour < 18)
        {
            return String.format("%s %s", context.getString(R.string.goodAfternoon) , currentHour);
        }
        else if (18 <= currentHour && currentHour < 21)
        {
            return String.format("%s %s", context.getString(R.string.goodEvening) , currentHour);
        }
        else
        {
            return String.format("%s %s", context.getString(R.string.goodNight) , currentHour);
        }
    }
}
