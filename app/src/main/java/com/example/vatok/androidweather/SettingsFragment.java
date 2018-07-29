package com.example.vatok.androidweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import timber.log.Timber;

public class SettingsFragment extends Fragment {
    public static Fragment newInstance(Data data)
    {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putSerializable("data", data);
        fragment.setArguments(args);
        return fragment;
    }

    Data data;
    EditText nameEditText;
    Switch typeSwitch;
    Switch windSwitch;
    Switch pressureSwitch;
    Switch humiditySwitch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Timber.d("onCreateView");
        View view = inflater.inflate(R.layout.fragment_settings, null);

        nameEditText = view.findViewById(R.id.et_name);
        typeSwitch= view.findViewById(R.id.sw_type);
        windSwitch= view.findViewById(R.id.sw_wind);
        pressureSwitch= view.findViewById(R.id.sw_pressure);
        humiditySwitch= view.findViewById(R.id.sw_humidity);

        data = (Data) getArguments().getSerializable("data");

        String name = data.getName() != null ? data.getName() : "";
        nameEditText.setText(name);
        typeSwitch.setChecked(data.isShowType());
        windSwitch.setChecked(data.isShowWind());
        pressureSwitch.setChecked(data.isShowPressure());
        humiditySwitch.setChecked(data.isShowHumidity());

        typeSwitch.setOnCheckedChangeListener(new ChangeListener());
        windSwitch.setOnCheckedChangeListener(new ChangeListener());
        pressureSwitch.setOnCheckedChangeListener(new ChangeListener());
        humiditySwitch.setOnCheckedChangeListener(new ChangeListener());
        nameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                save();
            }
        });

        return view;
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
        data.setShowWind( windSwitch.isChecked() );
        data.setShowPressure( pressureSwitch.isChecked());
        data.setShowHumidity( humiditySwitch.isChecked() );
    }
}



