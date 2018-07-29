package com.example.vatok.androidweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import io.paperdb.Paper;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    CitiesFragment citiesFragment;
    DetailsFragment detailsFragment;

    CitiesFragment.OnCitySelectedListener citySelectedListener = new CitiesFragment.OnCitySelectedListener() {
        @Override
        public void onCitySelected(int position) {
            data.setCurrentCityId(position);
            Timber.d(""+position);
            citiesFragment = (CitiesFragment) CitiesFragment.newInstance(data);
            citiesFragment.setCitySelectedListener(citySelectedListener);
            detailsFragment = (DetailsFragment) DetailsFragment.newInstance(data);
            updateFragments();
        }
    };
    boolean isLandscape;
    private Data data;
    Toolbar toolbar;
    ImageView settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        isLandscape = findViewById(R.id.fl_detail) != null;
        settingsButton = findViewById(R.id.iv_settings);

        // если мы из авторизации и в интенте есть имя - инициализируем дату
        if(getIntent().hasExtra("name")) {
            data = new Data(
                    getIntent().getStringExtra("name"),
                    getResources().getStringArray(R.array.cities),
                    getResources().getStringArray(R.array.weatherTypes)
            );
            Paper.book().write("data", data);
        }
        // иначе пытаемся получить из базы
        else {
            data=(Data) Paper.book().read("data");
        }

        //если инфы нет, значит запускаем авторизацию
        if (data==null) {
            Intent intent = new Intent(MainActivity.this, AuthActivity.class);
            startActivity( intent );
            finish();
            return;
        }

        //фрагменты городов
        citiesFragment = (CitiesFragment) CitiesFragment.newInstance(data);
        citiesFragment.setCitySelectedListener(citySelectedListener);
        detailsFragment = (DetailsFragment) DetailsFragment.newInstance(data);
        updateFragments();

        //фрагмент настроек
        settingsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(getSupportFragmentManager().findFragmentById(R.id.fl_settings) instanceof SettingsFragment)
                {
                    getSupportFragmentManager().popBackStack();
                    return;
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_settings, SettingsFragment.newInstance(data))
                        .addToBackStack("settings")
                        .commit();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        data = Paper.book().read("data");
        data.setCurrentCityId((int) Paper.book().read("currentCityId") );
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        Paper.book().write("data", data);
        Paper.book().write("currentCityId", data.getCurrentCityId());
    }

    public void updateFragments() {

        if(getSupportFragmentManager().findFragmentById(R.id.fl_master)==null) {
            if(isLandscape) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_master, citiesFragment)
                        .replace(R.id.fl_detail, detailsFragment)
                        .commit();
            }
            else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_master, citiesFragment)
                        .commit();
            }
        }
        else {
            if(getSupportFragmentManager().findFragmentById(R.id.fl_master) instanceof DetailsFragment) {
                getSupportFragmentManager().popBackStack();
            }
            if(isLandscape) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_master, citiesFragment)
                        .replace(R.id.fl_detail, detailsFragment)
                        .addToBackStack(""+data.getCurrentCityId())
                        .commit();
            }
            else {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_master, detailsFragment)
                        .addToBackStack(""+data.getCurrentCityId())
                        .commit();
            }
        }
    }
}
