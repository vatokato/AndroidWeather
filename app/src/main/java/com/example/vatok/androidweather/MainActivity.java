package com.example.vatok.androidweather;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";

    EditText nameEditText;
    AutoCompleteTextView cityAutoText;
    Button button;

    UserData userData;

    BackTimer backTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.et_name);
        cityAutoText = findViewById(R.id.et_city);
        button = findViewById(R.id.button);

        String[] cities = {"Москва", "Екатеринбург", "Санкт Петербург", "Сочи", "Мурманск", "Москва 2"};
        cityAutoText.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,cities));

        if(getIntent().hasExtra("userData"))
        {
            userData = (UserData) getIntent().getSerializableExtra("userData");
            nameEditText.setText(userData.getName());
            cityAutoText.setText(userData.getCity());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String city = cityAutoText.getText().toString().trim();

                if(name.isEmpty() || city.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Пожалуйста заполните все поля", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(getCallingActivity() != null)
                {
                    userData.setName(name);
                    userData.setCity(city);
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("userData", userData);
                    setResult(Activity.RESULT_OK, resultIntent);
                }
                else {
                    Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                    intent.putExtra("userData", new UserData(name, city));
                    startActivity(intent);
                }
                finish();
            }
        });
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
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        userData = (UserData) savedInstanceState.getSerializable("userData");
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putSerializable("userData",userData);
    }
}
