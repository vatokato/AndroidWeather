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
        args.putSerializable("data", data);
        fragment.setArguments(args);
        return fragment;
    }

    TextView temperatureTextVeiw;
    TextView typeTextVeiw;
    Data data;
    Publisher publisher;

    @Override
    public void onAttach(Context context)
    {
        Timber.d("onAttach");
        super.onAttach(context);
        publisher = ((PublishGetter) context).getPublisher();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Timber.d("onCreateView Details");
        data = (Data) getArguments().getSerializable("data");

        View view = inflater.inflate(R.layout.fragment_details, null);
        temperatureTextVeiw = view.findViewById(R.id.tv_temperature);
        typeTextVeiw = view.findViewById(R.id.tv_type);

        CityInfo cityInfo = data.getInfo();
        temperatureTextVeiw.setText(""+cityInfo.getTemperatureString());
        temperatureTextVeiw.setTextColor(cityInfo.getTemperatureColor());
        typeTextVeiw.setText(""+cityInfo.getType());

        return view;
    }
}