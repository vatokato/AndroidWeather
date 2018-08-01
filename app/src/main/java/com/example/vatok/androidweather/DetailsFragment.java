package com.example.vatok.androidweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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

    ImageView logoImageView;
    ImageView bgImageView;
    ImageView favoriteImageVeiw;

    TextView temperatureTextView;
    TextView typeTextVeiw;
    TextView windTextVeiw;
    TextView pressureTextVeiw;
    TextView humidityTextVeiw;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        data = ((DataGetter) getActivity()).getData();
        data.setCurrentCityId( getArguments().getInt("position") );

        View view = inflater.inflate(R.layout.fragment_details, null);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if(data.getCurrentCityId()<0)
            return view;

        final CityInfo cityInfo = data.getInfo();

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle( cityInfo.getTitle() );

        temperatureTextView = view.findViewById(R.id.tv_temperature);
        temperatureTextView.setText(cityInfo.getTemperatureString());

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


        if(data.isShowType()) {
            typeTextVeiw = view.findViewById(R.id.tv_type);
            typeTextVeiw.setText(cityInfo.getType());
            typeTextVeiw.setVisibility(View.VISIBLE);
        }
        if(data.isShowWind()) {
            windTextVeiw = view.findViewById(R.id.tv_wind);
            windTextVeiw.setText(cityInfo.getWind());
            windTextVeiw.setVisibility(View.VISIBLE);
        }
        if(data.isShowPressure()) {
            pressureTextVeiw = view.findViewById(R.id.tv_pressure);
            pressureTextVeiw.setText(cityInfo.getPressure());
            pressureTextVeiw.setVisibility(View.VISIBLE);
        }
        if(data.isShowHumidity()) {
            humidityTextVeiw = view.findViewById(R.id.tv_humidity);
            humidityTextVeiw.setText(cityInfo.getHumidity());
            humidityTextVeiw.setVisibility(View.VISIBLE);
        }



        return view;
    }
}
