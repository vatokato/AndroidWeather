package com.example.vatok.androidweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements GreetingRes {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView greetingTextView = findViewById(R.id.greeting);
        GreetingBuilder greetingBuilder = new GreetingBuilder(this);
        greetingTextView.setText( greetingBuilder.getText() );
    }

    @Override
    public String getNow() {
        return getString(R.string.now);
    }

    @Override
    public String getGreeter() {
        return getString(R.string.greeter);
    }

    @Override
    public String getMorning() {
        return getString(R.string.goodMorning);
    }

    @Override
    public String getAfternoon() {
        return getString(R.string.goodAfternoon);
    }

    @Override
    public String getEvening() {
        return getString(R.string.goodEvening);
    }

    @Override
    public String getNight() {
        return getString(R.string.goodNight);
    }
}
