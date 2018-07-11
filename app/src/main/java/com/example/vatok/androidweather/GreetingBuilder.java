package com.example.vatok.androidweather;

import java.util.Calendar;

public class GreetingBuilder {
    private int currentHour;
    GreetingStrings greetingStrings;

    public GreetingBuilder(GreetingStrings greetingStrings) {
        currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        this.greetingStrings = greetingStrings;
    }

    public GreetingBuilder(GreetingStrings greetingStrings, int hour) {
        currentHour = hour;
        this.greetingStrings = greetingStrings;
    }

    public String getText() {
        if (5 <= currentHour && currentHour < 12)
        {
            return String.format("%s, %s. %s %s", greetingStrings.getMorning(), greetingStrings.getGreeter(), greetingStrings.getNow(), currentHour);
        }
        else if (12 <= currentHour && currentHour < 18)
        {
            return String.format("%s, %s. %s %s", greetingStrings.getAfternoon(), greetingStrings.getGreeter(), greetingStrings.getNow(), currentHour);
        }
        else if (18 <= currentHour && currentHour < 21)
        {
            return String.format("%s, %s. %s %s", greetingStrings.getEvening(), greetingStrings.getGreeter(), greetingStrings.getNow(), currentHour);
        }
        else
        {
            return String.format("%s, %s. %s %s", greetingStrings.getNight(), greetingStrings.getGreeter(), greetingStrings.getNow(), currentHour);
        }
    }
}
