package com.example.vatok.androidweather;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import timber.log.Timber;

public class DetailsFragment extends Fragment
{
    public static Fragment newInstance(int position)
    {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnFavoriteClickListener
    {
        void onFavoriteClick(ImageView context, CityInfo cityInfo);
    }
    OnFavoriteClickListener favoriteClickListener;

    public void setFavoriteClickListener(OnFavoriteClickListener citySelectedListener)
    {
        this.favoriteClickListener = citySelectedListener;
    }

    Toolbar toolbar;
    Data data;
    CityInfo cityInfo;

    ImageView logoImageView;
    ImageView bgImageView;
    ImageView favoriteImageVeiw;

    TextView temperatureTextView;
    TextView typeTextVeiw;
    ImageView typeImageView;
    TextView windTextVeiw;
    TextView pressureTextVeiw;
    TextView humidityTextVeiw;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        data = ((DataGetter) getActivity()).getData();
        data.setCurrentCityId( getArguments().getInt("position") );

        View view = inflater.inflate(R.layout.fragment_details, null);

        Timber.d("onCreateView");
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_details);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(data.getCurrentCityId()<0)
            return view;

        cityInfo = data.getInfo();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle( cityInfo.getTitle() );

        logoImageView = view.findViewById(R.id.iv_logo);
        Glide.with(view.getContext())
                .load(cityInfo.getLogoUrl())
                .into(logoImageView);

        bgImageView = view.findViewById(R.id.iv_bg);
        Glide.with(view.getContext())
                .load(cityInfo.getPicUrl())
                .into(bgImageView);


        favoriteImageVeiw = view.findViewById(R.id.iv_favor);
        if(cityInfo.isFavorite()) {
            favoriteImageVeiw.setImageResource(R.drawable.ic_star_black_24dp);
        }
        else {
            favoriteImageVeiw.setImageResource(R.drawable.ic_star_border_black_24dp);
        }
        favoriteImageVeiw.getDrawable().setTint(view.getResources().getColor(R.color.colorAccent2Light));

        favoriteImageVeiw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(favoriteClickListener!= null) {
                    favoriteClickListener.onFavoriteClick(favoriteImageVeiw, cityInfo);
                }
            }
        });

        temperatureTextView = view.findViewById(R.id.tv_temperature);
        typeTextVeiw = view.findViewById(R.id.tv_type);
        typeImageView = view.findViewById(R.id.iv_type);
        windTextVeiw = view.findViewById(R.id.tv_wind);
        pressureTextVeiw = view.findViewById(R.id.tv_pressure);
        humidityTextVeiw = view.findViewById(R.id.tv_humidity);
        updateValues();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.d("onViewCreated");
    }

    public void updateValues() {
        temperatureTextView.setText(cityInfo.getTemperatureString());
        //((GradientDrawable)temperatureTextView.getBackground()).setColor( Color.rgb(255,0,0) );
        int color =  getActivity().getResources().getColor(R.color.colorPrimary) ;
        if(cityInfo.getTemperature()>18)
            color = getActivity().getResources().getColor(R.color.colorGreen);
        if(cityInfo.getTemperature()>24)
            color = getActivity().getResources().getColor(R.color.colorAccent);
        ((GradientDrawable)temperatureTextView.getBackground()).setColor( color);

        if(data.isShowType()) {
            typeTextVeiw.setText(cityInfo.getType());
            typeTextVeiw.setVisibility(View.VISIBLE);
        }
        if(data.isShowTypePic() && !cityInfo.getTypePic().isEmpty()) {
            Glide.with(getContext())
                    .load(String.format("http://openweathermap.org/img/w/%s.png", cityInfo.getTypePic()))
                    .into(typeImageView);
            typeImageView.setVisibility(View.VISIBLE);
        }
        if(data.isShowWind()) {
            windTextVeiw.setText(cityInfo.getWind());
            windTextVeiw.setVisibility(View.VISIBLE);
        }
        if(data.isShowPressure()) {
            pressureTextVeiw.setText(cityInfo.getPressure());
            pressureTextVeiw.setVisibility(View.VISIBLE);
        }
        if(data.isShowHumidity()) {
            humidityTextVeiw.setText(cityInfo.getHumidity());
            humidityTextVeiw.setVisibility(View.VISIBLE);
        }
    }

    public void setValues(int temp, int pressure, int humidity, String type, String typePic, int wind) {
        cityInfo.setTemperature(temp);
        cityInfo.setPressure(pressure);
        cityInfo.setHumidity(humidity);
        cityInfo.setWind(wind);
        cityInfo.setType(type);
        cityInfo.setTypePic(typePic);
        data.save();
        updateValues();
    }

    public CityInfo getCityInfo() {
        return cityInfo;
    }
}
