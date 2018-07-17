package com.example.vatok.androidweather;

import java.util.Date;
public class BackTimer  {
    public  static long lastTime;

    public static boolean isBackPressed() {
        Date date = new Date();
        long newTime = date.getTime();
        if(newTime-lastTime<2000)
            return true;

        lastTime = newTime;
        return false;
    }
}
