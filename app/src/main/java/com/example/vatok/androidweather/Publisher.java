package com.example.vatok.androidweather;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Publisher implements Serializable{
    private List<Observer> observers; // Все обозреватели
    private int currentCity;

    public Publisher() {
        observers = new ArrayList<>();
        currentCity = 0;
    }
    // Подписать
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    // Отписать
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    // Разослать событие
    public void notify(int currentCity) {
        this.currentCity = currentCity;
        for (Observer observer : observers) {
            observer.updateList(currentCity);
        }
    }

    public int getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(int currentCity) {
        this.currentCity = currentCity;
    }
}

