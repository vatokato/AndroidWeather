package com.example.vatok.androidweather;

import android.content.Context;
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
    Publisher publisher;
    Data data;

    public static Fragment newInstance(int currentCity, Data data)
    {
        CitiesFragment fragment = new CitiesFragment();
        Bundle args = new Bundle();
        args.putInt("currentCity", currentCity);
        args.putSerializable("data", data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Timber.d("onCreateView");
        data = (Data) getArguments().getSerializable("data");
        data.setCurrentCityId( getArguments().getInt("currentCity") );
        View view = inflater.inflate(R.layout.fragment_cities, null);
        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_activated_1);
        adapter.addAll(data.getCities());
        setListAdapter(adapter);
        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        Timber.d("onAttach");
        super.onAttach(context);
        publisher = ((PublishGetter) context).getPublisher();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(data.isMasterDetail()) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            getListView().setItemChecked(data.getCurrentCityId(), true);
        }

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        publisher.notify(position);
    }

}
