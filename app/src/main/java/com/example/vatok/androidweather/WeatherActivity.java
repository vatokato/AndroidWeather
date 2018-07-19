package com.example.vatok.androidweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import timber.log.Timber;

import java.util.Random;

public class WeatherActivity extends AppCompatActivity implements GreetingStrings {
    private static final int USER_DATA_REQUEST_ID = 591;
    private static String TAG = "WeatherActivity";
    UserData userData;

    TextView greetingTextView;
    TextView cityTextView;
    TextView weatherTempTextView;
    TextView weatherTypeTextView;
    BackTimer backTimer;
    Button button;
    String[] weatherTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_weather);

        weatherTypes = getResources().getStringArray(R.array.weatherTypes);
        userData =(UserData) getIntent().getSerializableExtra("userData");

        button = findViewById(R.id.button);
        greetingTextView = findViewById(R.id.greeting);
        cityTextView = findViewById(R.id.city);
        weatherTempTextView = findViewById(R.id.weatherTemperature);
        weatherTypeTextView = findViewById(R.id.weatherType);

        if(savedInstanceState==null) {
            userData.setWeatherTemperature();
            userData.setWeatherType(weatherTypes);
            Log.d(TAG, "OnCreate first run");
        }
        else {
            Log.d(TAG, "OnCreate recreate");
        }

        initData();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this, MainActivity.class);
                intent.putExtra("userData", userData);
                startActivityForResult(intent, USER_DATA_REQUEST_ID);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if (requestCode == USER_DATA_REQUEST_ID && resultCode == Activity.RESULT_OK)
        {
            userData = (UserData) data.getSerializableExtra("userData");
            //если город новый, то заданим новую температуру
            if( !cityTextView.getText().toString().equals(userData.getCity()) ) {
                userData.setWeatherTemperature();
                userData.setWeatherType(weatherTypes);
            }
            initData();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    private void initData()
    {
        greetingTextView.setText(new GreetingBuilder(this).getText());
        cityTextView.setText(userData.getCity());
        weatherTempTextView.setTextColor(userData.getWeatherTemperatureColor());
        weatherTempTextView.setText(userData.getWeatherTemperature());
        weatherTypeTextView.setText(userData.getWeatherType());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        userData =(UserData) savedInstanceState.getSerializable("userData");
        initData();
        Log.d(TAG,"onRestoreInstanceState()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putSerializable("userData", userData);
        Log.d(TAG,"onSaveInstanceState()");
    }

    @Override
    public void onBackPressed() {
        if(BackTimer.isBackPressed()==false){
            Toast.makeText(this, getString(R.string.toastBackText), Toast.LENGTH_SHORT).show();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public String getNow() {
        return getString(R.string.now);
    }

    @Override
    public String getGreeter() {
        return userData.getName();
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
