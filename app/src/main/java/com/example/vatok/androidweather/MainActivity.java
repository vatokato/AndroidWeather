package com.example.vatok.androidweather;

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
            Timber.d("onCreate first");
            data = new Data(getResources().getStringArray(R.array.cities),  getResources().getStringArray(R.array.weatherTypes));
            updateList(0);
        }
        else {
            Timber.d("onCreate second");
            data = (Data) savedInstanceState.getSerializable("data");
        }

        data.setMasterDetail(findViewById(R.id.fl_detail) != null);
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

        if(data.getName() == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_start, AuthFragment.newInstance(data))
                    .commit();
            return;
        }

        if(data.isMasterDetail()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_detail, DetailsFragment.newInstance(data))
                    .replace(R.id.fl_master, CitiesFragment.newInstance(currentCity, data))
                    .addToBackStack(""+currentCity)
                    .commit();
        }
        else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_master, CitiesFragment.newInstance(currentCity, data))
                    .addToBackStack(""+currentCity)
                    .commit();

            if(!firstTime) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_master, DetailsFragment.newInstance(data))
                        .addToBackStack(""+currentCity)
                        .commit();
            }
            firstTime = false;
        }

    }
}
