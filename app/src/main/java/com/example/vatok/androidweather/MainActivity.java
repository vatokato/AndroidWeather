package com.example.vatok.androidweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vatok.androidweather.WeatherItem.WeatherItem;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements DataGetter {
    CitiesFragment.OnCitySelectedListener citySelectedListener = new CitiesFragment.OnCitySelectedListener() {
        @Override
        public void onCitySelected(int cityId) {
            data.setCurrentCityId(cityId);
            detailsFragment = (DetailsFragment) DetailsFragment.newInstance(data.getCurrentCityId());
            detailsFragment.setFavoriteClickListener(favoriteClickListener);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_detail, detailsFragment)
                    .addToBackStack(data.getCurrentCityId()+"")
                    .commit();

            CityInfo city = data.getInfo(cityId);
            OpenWeatherApp.getApi()
                    .getData(city.getOwID(), OpenWeatherApp.getAPPID(), "ru", "metric")
                    .enqueue(new Callback<WeatherItem>() {
                        @Override
                        public void onResponse(Call<WeatherItem> call, Response<WeatherItem> response) {
                            if(response.body()!=null) {

                                detailsFragment.setValues(
                                        response.body().getMain().getTemp().intValue(),
                                        response.body().getMain().getPressure(),
                                        response.body().getMain().getHumidity(),
                                        response.body().getWeather().get(0).getDescription(),
                                        response.body().getWeather().get(0).getIcon(),
                                        response.body().getWind().getSpeed()
                                );
                                Toast.makeText(MainActivity.this, "Данные обновлены",Toast.LENGTH_SHORT ).show();
                                return;
                            }
                            Toast.makeText(MainActivity.this, "Ошибка обновления данных",Toast.LENGTH_SHORT ).show();
                            System.err.println(response.code()+": "+response.message());
                        }
                        @Override
                        public void onFailure(Call<WeatherItem> call, Throwable t) {
                            System.err.println(t);
                        }
                    });

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
            iv.getDrawable().setTint(getResources().getColor(R.color.colorAccent2Light));
            data.save();
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
                Timber.d("home pressed");
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

        //если есть детали при перевороте
        if(getSupportFragmentManager().findFragmentById(R.id.fl_detail) instanceof DetailsFragment) {
            //отшагнем и пересоздадим
            getSupportFragmentManager().popBackStack();
            detailsFragment = (DetailsFragment) DetailsFragment.newInstance(data.getCurrentCityId());
            detailsFragment.setFavoriteClickListener(favoriteClickListener);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_detail, detailsFragment)
                    .addToBackStack(data.getCurrentCityId()+"")
                    .commit();
        }
    }

}
