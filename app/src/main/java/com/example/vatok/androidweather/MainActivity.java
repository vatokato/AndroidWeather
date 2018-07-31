package com.example.vatok.androidweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import io.paperdb.Paper;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements DataGetter {

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
    Toolbar toolbar;
    ImageView settingsButton;
    boolean firstTime;
    DetailsFragment detailsFragment;
    AppCompatActivity that;



    RecyclerView recyclerView;
    RVAdapter adapter;
    List<CityInfo> items;

    @Override
    public Data getData() {
        return data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        that = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        settingsButton = findViewById(R.id.iv_settings);

        // если мы из авторизации и в интенте есть имя - инициализируем дату
        if(getIntent().hasExtra("name")) {
            data = new Data(getIntent().getStringExtra("name"), this);
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
        Timber.d("oncreate");

        if(savedInstanceState==null){
            showCities();
        }

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
                        .replace(R.id.fl_settings, SettingsFragment.newInstance())
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
        Timber.d("onRestoreInstanceState");
        showCities();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        Paper.book().write("data", data);
        Paper.book().write("currentCityId", data.getCurrentCityId());
        Timber.d("onSaveInstanceState");
    }

    public void showCities() {
        //чистим детаил фрагмент
        Fragment fr = getSupportFragmentManager().findFragmentById(R.id.fl_detail);
        if(fr!=null) {
            getSupportFragmentManager().beginTransaction()
                    .remove(fr)
                    .commit();
        }

        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        items = data.getCityInfoArrayList();
        adapter = new RVAdapter(data, items, R.layout.item, new RVAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(CityInfo item, int pos)
            {
                for(int i=0; i<items.size();i++){
                    items.get(i).setActive(false);
                    if(i==pos) {
                        items.get(i).setActive(true);
                    }
                }

                data.setCurrentCityId(pos);

                detailsFragment = (DetailsFragment) DetailsFragment.newInstance(pos);
                detailsFragment.setFavoriteClickListener(favoriteClickListener);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_detail, detailsFragment)
                        .addToBackStack(""+pos)
                        .commit();

                Data.saveDataRv(items);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10));
    }

}
