package com.example.vatok.androidweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import timber.log.Timber;

public class CitiesFragment extends Fragment
{

    public interface OnCitySelectedListener
    {
        void onCitySelected(int cityId);
    }
    OnCitySelectedListener citySelectedListener;

    public interface SettingsClickListener
    {
        void onSettingsClick();
    }
    SettingsClickListener settingsClickListener;

    Toolbar toolbar;
    ImageView settingsButton;
    Data data;
    RecyclerView recyclerView;
    RVAdapter adapter;
    List<CityInfo> items;

    public static Fragment newInstance(int position)
    {
        CitiesFragment fragment = new CitiesFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        data = ((DataGetter) getActivity()).getData();
        data.setCurrentCityId(getArguments().getInt("position"));
        View view = inflater.inflate(R.layout.fragment_cities, null);

        Timber.d("onCreateView");
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_cities);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.app_name);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        settingsButton = view.findViewById(R.id.iv_settings);
        settingsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(settingsClickListener!=null){
                    settingsClickListener.onSettingsClick();
                }
            }
        });

        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
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
                if(citySelectedListener != null)
                {
                    citySelectedListener.onCitySelected(pos);
                }
            }

        });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10));

        return view;
    }



    public void setCitySelectedListener(OnCitySelectedListener citySelectedListener)
    {
        this.citySelectedListener = citySelectedListener;
    }

    public void setSettingsClickListener(SettingsClickListener settingsClickListener)
    {
        this.settingsClickListener = settingsClickListener;
    }

}
