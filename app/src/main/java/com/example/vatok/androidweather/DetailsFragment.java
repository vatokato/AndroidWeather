package com.example.vatok.androidweather;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
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

    OnFavoriteClickListener favoriteClickListener;
    public interface OnFavoriteClickListener
    {
        void onFavoriteClick(ImageView context, CityInfo cityInfo);
    }
    public void setFavoriteClickListener(OnFavoriteClickListener citySelectedListener)
    {
        this.favoriteClickListener = citySelectedListener;
    }

    ImageView logoImageView;
    ImageView favoriteImageVeiw;
    TextView temperatureTextVeiw;
    TextView cityTextVeiw;

    TextView typeTextVeiw;
    TextView windTextVeiw;
    TextView pressureTextVeiw;
    TextView humidityTextVeiw;
    Data data;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        data = ((DataGetter) getActivity()).getData();
        data.setCurrentCityId( getArguments().getInt("position") );

        View view = inflater.inflate(R.layout.fragment_details, null);
        logoImageView = view.findViewById(R.id.iv_image);
        favoriteImageVeiw = view.findViewById(R.id.iv_favor);
        temperatureTextVeiw = view.findViewById(R.id.tv_temperature);
        typeTextVeiw = view.findViewById(R.id.tv_type);
        windTextVeiw = view.findViewById(R.id.tv_wind);
        pressureTextVeiw = view.findViewById(R.id.tv_pressure);
        humidityTextVeiw = view.findViewById(R.id.tv_humidity);

        if(data.getCurrentCityId()<0)
            return view;

        final CityInfo cityInfo = data.getInfo();

        if(cityInfo.isFavorite()) {
            favoriteImageVeiw.setImageResource(R.drawable.ic_star_black_24dp);
        }
        else {
            favoriteImageVeiw.setImageResource(R.drawable.ic_star_border_black_24dp);
        }
        favoriteImageVeiw.getDrawable().setTint(view.getResources().getColor(R.color.colorAccent2));
        favoriteImageVeiw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(favoriteClickListener!= null) {
                    favoriteClickListener.onFavoriteClick(favoriteImageVeiw, cityInfo);
                }
            }
        });
        temperatureTextVeiw.setText(""+cityInfo.getTemperatureString());
        temperatureTextVeiw.setTextColor(cityInfo.getTemperatureColor());

        cityTextVeiw = view.findViewById(R.id.tv_city);
        if(cityTextVeiw!=null) {
            cityTextVeiw.setText(cityInfo.getTitle());
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

        if(data.isShowLogo()) {
            humidityTextVeiw.setText(cityInfo.getHumidity());

            Glide.with(view.getContext())
                    .load(cityInfo.getImageUrl())
                    .into(logoImageView);
        }



        return view;
    }
}
