package com.example.vatok.androidweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import timber.log.Timber;

public class CitiesFragment extends ListFragment
{
    OnCitySelectedListener citySelectedListener;
    public interface OnCitySelectedListener
    {
        void onCitySelected(int position);
    }

    Data data;

    public static Fragment newInstance(Data data)
    {
        CitiesFragment fragment = new CitiesFragment();
        Bundle args = new Bundle();
        args.putSerializable("data", data);
        args.putInt("currentCity", data.getCurrentCityId()); // сохраняем интовый выбранный город для бекстека
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        data = (Data) getArguments().getSerializable("data");
        data.setCurrentCityId( getArguments().getInt("currentCity") );

        View view = inflater.inflate(R.layout.fragment_cities, null);
        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_activated_1);
        adapter.addAll(data.getCities());
        setListAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("resume");
        if(data.getCurrentCityId()>=0) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            getListView().setItemChecked(data.getCurrentCityId(), true);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        if(citySelectedListener != null)
        {
            citySelectedListener.onCitySelected(position);
        }
    }

    public void setCitySelectedListener(OnCitySelectedListener citySelectedListener)
    {
        this.citySelectedListener = citySelectedListener;
    }

}
