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
    int currentCityId;

    public static Fragment newInstance(Data data, int currentCityId)
    {
        CitiesFragment fragment = new CitiesFragment();
        Bundle args = new Bundle();
        args.putSerializable("data", data);
        args.putInt("currentCity", currentCityId); // сохраняем интовый выбранный город для бекстека
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        data = (Data) getArguments().getSerializable("data");

        currentCityId = getArguments().getInt("currentCity");
        data.setCurrentCityId(currentCityId);

        View view = inflater.inflate(R.layout.fragment_cities, null);
        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_activated_1);
        adapter.addAll(data.getCities());
        setListAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.d(currentCityId+"");
//        if(currentCityId>=0 && getActivity().findViewById(R.id.fl_detail)!=null) {
        if(currentCityId>=0) {
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
