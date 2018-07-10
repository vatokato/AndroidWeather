package com.example.vatok.androidweather;

import java.util.Calendar;

public class GreetingBuilder {
    private int currentHour;
    GreetingRes greetingRes;

    public GreetingBuilder(GreetingRes greetingRes) {
        currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        this.greetingRes = greetingRes;
    }

    public GreetingBuilder(GreetingRes greetingRes, int hour) {
        currentHour = hour;
        this.greetingRes = greetingRes;
    }

    public String getText() {
        if (5 <= currentHour && currentHour < 12)
        {
            return String.format("%s, %s. %s %s", greetingRes.getMorning(), greetingRes.getGreeter(), greetingRes.getNow(), currentHour);
        }
        else if (12 <= currentHour && currentHour < 18)
        {
            return String.format("%s, %s. %s %s", greetingRes.getAfternoon(), greetingRes.getGreeter(), greetingRes.getNow(), currentHour);
        }
        else if (18 <= currentHour && currentHour < 21)
        {
            return String.format("%s, %s. %s %s", greetingRes.getEvening(), greetingRes.getGreeter(), greetingRes.getNow(), currentHour);
        }
        else
        {
            return String.format("%s, %s. %s %s", greetingRes.getNight(), greetingRes.getGreeter(), greetingRes.getNow(), currentHour);
        }
    }
}
