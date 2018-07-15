package com.example.vatok.androidweather;

public class BackTimer extends Thread {
    public static boolean backPressed = false;

    public void run() {
        backPressed = true;
        try {
            Thread.sleep(2000);
            backPressed = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean isBackPressed() {
        return backPressed;
    }
}
