package com.example.vatok.androidweather;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import timber.log.Timber;

public class DetailsFragment extends Fragment
{
    public static Fragment newInstance(Data data)
    {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt("currentCity", data.getCurrentCityId());
        args.putSerializable("data", data);
        fragment.setArguments(args);
        return fragment;
    }

    TextView temperatureTextVeiw;
    TextView cityTextVeiw;

    TextView typeTextVeiw;
    TextView windTextVeiw;
    TextView pressureTextVeiw;
    TextView humidityTextVeiw;
    Data data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        data = (Data) getArguments().getSerializable("data");
        data.setCurrentCityId( getArguments().getInt("currentCity") );

        View view = inflater.inflate(R.layout.fragment_details, null);
        temperatureTextVeiw = view.findViewById(R.id.tv_temperature);
        typeTextVeiw = view.findViewById(R.id.tv_type);
        windTextVeiw = view.findViewById(R.id.tv_wind);
        pressureTextVeiw = view.findViewById(R.id.tv_pressure);
        humidityTextVeiw = view.findViewById(R.id.tv_humidity);

        if(data.getCurrentCityId()<0)
            return view;

        CityInfo cityInfo = data.getInfo();
        temperatureTextVeiw.setText(""+cityInfo.getTemperatureString());
        temperatureTextVeiw.setTextColor(cityInfo.getTemperatureColor());

        cityTextVeiw = view.findViewById(R.id.tv_city);
        if(cityTextVeiw!=null) {
            cityTextVeiw.setText(cityInfo.getName());
        }

        if(data.isShowType()) {
            typeTextVeiw.setText(cityInfo.getType());
        }
        if(data.isShowWind()) {
            windTextVeiw.setText(cityInfo.getWind());
        }
        if(data.isShowPressure()) {
            pressureTextVeiw.setText(cityInfo.getPressure());
        }
        if(data.isShowHumidity()) {
            humidityTextVeiw.setText(cityInfo.getHumidity());
        }

        return view;
    }
}
