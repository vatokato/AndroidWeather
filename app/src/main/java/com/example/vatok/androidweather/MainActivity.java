package com.example.vatok.androidweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements PublishGetter, Observer {

    enum Mode
    {
        AUTH("auth"),
        CITIES("cities"),
        DETAIL("detail"),
        MASTER_DETAIL("master_detail"),
        SETTINGS("settings"),
        UNKNOWN("unknown");
        String id;
        Mode(String id)
        {
            this.id = id;
        }
        public static Mode byId(String id)
        {
            for (Mode mode : Mode.values())
            {
                if (mode.id.equals(id))
                {
                    return mode;
                }
            }
            return UNKNOWN;
        }
    }

    Mode mode = Mode.AUTH;

    AppCompatActivity that;
    private Publisher publisher = new Publisher();
    private Data data;
    boolean firstTime;
    Toolbar toolbar;
    ImageView settingsButton;

    @Override
    public Publisher getPublisher() {
        return publisher;
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

        firstTime = true;
        publisher.subscribe(this);
        if (savedInstanceState == null)
        {
            data = new Data(getResources().getStringArray(R.array.cities),  getResources().getStringArray(R.array.weatherTypes));
            updateList(0);

        }
        else {
            data = (Data) savedInstanceState.getSerializable("data");
        }


        settingsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(getSupportFragmentManager().findFragmentById(R.id.fl_master) instanceof SettingsFragment)
                {
                    System.out.println("settings in master");
                    getSupportFragmentManager().popBackStack();
                    return;
                }

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_master, SettingsFragment.newInstance(data))
                        .addToBackStack("settings")
                        .commit();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        System.out.println(item.getItemId());
        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        Timber.d("onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
        data = (Data) savedInstanceState.getSerializable("data");
        publisher.notify( savedInstanceState.getInt("currentCity") );
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        Timber.d("onSaveInstanceState");
        outState.putSerializable("data", data);
        outState.putInt("currentCity", publisher.getCurrentCity());
    }


    @Override
    public void updateList(int currentCity) {
        Timber.d("updateList");

        data.setCurrentCityId(currentCity);
        data.setMasterDetail(findViewById(R.id.fl_detail) != null);

        if(data.getName() == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_start, AuthFragment.newInstance(data))
                    .commit();
            return;
        }

        if(data.isMasterDetail()) {

            // если мы на альбомной, а мастере лежит артефакт от портрета делаем шаг назад
            if(getSupportFragmentManager().findFragmentById(R.id.fl_master) instanceof DetailsFragment)
            {
                getSupportFragmentManager().popBackStack();
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_detail, DetailsFragment.newInstance(currentCity, data))
                    .replace(R.id.fl_master, CitiesFragment.newInstance(currentCity, data))
                    .addToBackStack(""+currentCity)
                    .commit();
        }
        else {
            if(firstTime) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_master, CitiesFragment.newInstance(currentCity, data))
                        .addToBackStack(""+currentCity)
                        .commit();
            }
            else  {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_master, DetailsFragment.newInstance(currentCity, data))
                        .addToBackStack(""+currentCity)
                        .commit();
            }
            firstTime = false;
        }

    }
}
