package com.example.vatok.androidweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;

import io.paperdb.Paper;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements DataGetter {
    CitiesFragment.OnCitySelectedListener citySelectedListener = new CitiesFragment.OnCitySelectedListener() {
        @Override
        public void onCitySelected() {
            detailsFragment = (DetailsFragment) DetailsFragment.newInstance(data.getCurrentCityId());
            detailsFragment.setFavoriteClickListener(favoriteClickListener);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_detail, detailsFragment)
                    .addToBackStack(data.getCurrentCityId()+"")
                    .commit();
        }
    };

    CitiesFragment.SettingsClickListener settingsClickListener = new CitiesFragment.SettingsClickListener() {
        @Override
        public void onSettingsClick() {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_settings, SettingsFragment.newInstance())
                    .addToBackStack("settings")
                    .commit();
        }
    };

    DetailsFragment.OnFavoriteClickListener favoriteClickListener = new DetailsFragment.OnFavoriteClickListener() {
        @Override
        public void onFavoriteClick(ImageView iv, CityInfo cityInfo) {
            if(cityInfo.isFavorite()) {
                cityInfo.setFavorite(false);
                iv.setImageResource(R.drawable.ic_star_border_black_24dp);
            }
            else {
                cityInfo.setFavorite(true);
                iv.setImageResource(R.drawable.ic_star_black_24dp);
            }
            Data.saveDataRv(data.getCityInfoArrayList());
        }
    };


    private Data data;
    DetailsFragment detailsFragment;
    CitiesFragment citiesFragment;

    @Override
    public Data getData() {
        return data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // если мы из авторизации и в интенте есть имя - инициализируем дату
        if(getIntent().hasExtra("name")) {
            data = new Data(getIntent().getStringExtra("name"), this);
            data.save();
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
        showCities();

        if(savedInstanceState==null){

        }
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
        Timber.d("onRestoreInstanceState");
        //showCities();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        data.save();
        Paper.book().write("currentCityId", data.getCurrentCityId());
        Timber.d("onSaveInstanceState");
    }

    public void showCities() {
        citiesFragment =(CitiesFragment) CitiesFragment.newInstance(data.getCurrentCityId());
        citiesFragment.setCitySelectedListener(citySelectedListener);
        citiesFragment.setSettingsClickListener(settingsClickListener);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_master, citiesFragment)
                .commit();
    }

}
