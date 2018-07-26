package com.example.vatok.androidweather;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements PublishGetter, Observer {
    private Publisher publisher = new Publisher();
    private Data data;
    boolean firstTime;

    @Override
    public Publisher getPublisher() {
        return publisher;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
