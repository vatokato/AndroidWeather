package com.example.vatok.androidweather;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import io.paperdb.Paper;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements DataGetter {
    CitiesFragment.OnCitySelectedListener citySelectedListener = new CitiesFragment.OnCitySelectedListener() {
        @Override
        public void onCitySelected(int position) {
            updateFragments(position);
        }
    };
    private Data data;
    Toolbar toolbar;
    ImageView settingsButton;
    boolean firstTime;
    CitiesFragment citiesFragment;
    DetailsFragment detailsFragment;
    SettingsFragment settingsFragment;

    @Override
    public Data getData() {
        return data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        firstTime = true;
        if(savedInstanceState==null){
            updateFragments(data.getCurrentCityId());
        }

        //фрагмент настроек
        settingsFragment =(SettingsFragment) SettingsFragment.newInstance(data);
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
                        .replace(R.id.fl_settings, settingsFragment)
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
        updateFragments(data.getCurrentCityId());
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        Paper.book().write("data", data);
        Paper.book().write("currentCityId", data.getCurrentCityId());
    }

    public void updateFragments(int position) {
        data.setCurrentCityId(position);
        data.setMasterDetail( getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE );

        //фрагменты городов
        citiesFragment = (CitiesFragment) CitiesFragment.newInstance(data);
        citiesFragment.setCitySelectedListener(citySelectedListener);
        detailsFragment = (DetailsFragment) DetailsFragment.newInstance(data);

        if(firstTime) {
            if(data.isMasterDetail()) {
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
            firstTime = false;
        }
        else {
            if(data.isMasterDetail()) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_master, citiesFragment)
                        .replace(R.id.fl_detail, detailsFragment)
                        .addToBackStack(""+position)
                        .commit();
            }
            else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_detail, detailsFragment)
                        .addToBackStack(""+position)
                        .commit();
            }
        }
    }
}
