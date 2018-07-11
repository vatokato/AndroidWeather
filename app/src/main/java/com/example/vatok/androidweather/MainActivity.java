package com.example.vatok.androidweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements GreetingStrings {
    private static String TAG = "MainActivity";
    TextView city;
    EditText enterCityField;
    Button enterCityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        city = findViewById(R.id.city);
        enterCityField = findViewById(R.id.enterCityField);
        enterCityButton = findViewById(R.id.enterCityButton);

        TextView greetingTextView = findViewById(R.id.greeting);
        GreetingBuilder greetingBuilder = new GreetingBuilder(this);
        greetingTextView.setText( greetingBuilder.getText() );

        if(savedInstanceState==null) {
            Toast.makeText(this, "OnCreate first run", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "OnCreate first run");
        }
        else {
            Toast.makeText(this, "OnCreate recreate", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "OnCreate recreate");
        }

        enterCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!enterCityField.getText().toString().isEmpty()) {
                    city.setText( enterCityField.getText().toString() );
                }
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Toast.makeText(this, "onStart()", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"onStart()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        city.setText( savedInstanceState.getString("currentCity") );
        Toast.makeText(this, "onRestoreInstanceState()", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"onRestoreInstanceState()");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Toast.makeText(this, "onResume()", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"onResume()");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Toast.makeText(this, "onPause()", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"onPause()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString("currentCity", city.getText().toString());
        Toast.makeText(this, "onSaveInstanceState()", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"onSaveInstanceState()");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Toast.makeText(this, "onStop()", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"onStop()");
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Toast.makeText(this, "onRestart()", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"onRestart()");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Toast.makeText(this, "onDestroy()", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"onDestroy()");
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
