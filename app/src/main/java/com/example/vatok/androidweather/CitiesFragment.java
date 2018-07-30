package com.example.vatok.androidweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class CitiesFragment extends Fragment
{
    OnCitySelectedListener citySelectedListener;
    public interface OnCitySelectedListener
    {
        void onCitySelected(int position);
    }
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

        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        items = data.getCityInfoArrayList();
        adapter = new RVAdapter(items, R.layout.item, new RVAdapter.OnItemClickListener()
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
                if(citySelectedListener != null)
                {
                    citySelectedListener.onCitySelected(pos);
                }
                Data.saveData(items);
            }
            @Override
            public void onItemButtonClick(CityInfo item, int pos)
            {
                if(item.isFavorite()) {
                    item.setFavorite(false);
                }
                else {
                    item.setFavorite(true);
                }
                Data.saveData(items);
                adapter.notifyDataSetChanged();

            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10));

/*
        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_activated_1);
        adapter.addAll(data.getCities());
        setListAdapter(adapter);
*/
        return view;
    }



    public void setCitySelectedListener(OnCitySelectedListener citySelectedListener)
    {
        this.citySelectedListener = citySelectedListener;
    }

}
