package com.example.vatok.androidweather;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import timber.log.Timber;

public class SettingsFragment extends Fragment {
    public static Fragment newInstance()
    {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    Data data;
    EditText nameEditText;
    View settingsView;
    Switch typeSwitch;
    Switch typePicSwitch;
    Switch windSwitch;
    Switch pressureSwitch;
    Switch humiditySwitch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Timber.d("onCreateView");
        View view = inflater.inflate(R.layout.fragment_settings, null);
        nameEditText = view.findViewById(R.id.et_name);
        settingsView = view.findViewById(R.id.v_settings);
        typeSwitch= view.findViewById(R.id.sw_type);
        typePicSwitch= view.findViewById(R.id.sw_type);
        windSwitch= view.findViewById(R.id.sw_wind);
        pressureSwitch= view.findViewById(R.id.sw_pressure);
        humiditySwitch= view.findViewById(R.id.sw_humidity);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        data = ((DataGetter) context).getData();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        data = ((DataGetter) getActivity()).getData();
        String name = data.getName() != null ? data.getName() : "";

        nameEditText.setText(name);
        typeSwitch.setChecked(data.isShowType());
        typePicSwitch.setChecked(data.isShowTypePic());
        windSwitch.setChecked(data.isShowWind());
        pressureSwitch.setChecked(data.isShowPressure());
        humiditySwitch.setChecked(data.isShowHumidity());

        typeSwitch.setOnCheckedChangeListener(new ChangeListener());
        typePicSwitch.setOnCheckedChangeListener(new ChangeListener());
        windSwitch.setOnCheckedChangeListener(new ChangeListener());
        pressureSwitch.setOnCheckedChangeListener(new ChangeListener());
        humiditySwitch.setOnCheckedChangeListener(new ChangeListener());

        nameEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                save();
                return false;
            }
        });

        settingsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    class ChangeListener implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            save();
        }
    }

    private void save() {
        data.setName( nameEditText.getText().toString().trim() );
        data.setShowType( typeSwitch.isChecked());
        data.setShowTypePic( typePicSwitch.isChecked());
        data.setShowWind( windSwitch.isChecked() );
        data.setShowPressure( pressureSwitch.isChecked());
        data.setShowHumidity( humiditySwitch.isChecked() );
        data.save();
    }
}



