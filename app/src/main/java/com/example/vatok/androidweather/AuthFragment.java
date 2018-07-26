package com.example.vatok.androidweather;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.paperdb.Paper;
import timber.log.Timber;

public class AuthFragment extends Fragment
{
    Publisher publisher;
    Fragment self;
    Data data;

    EditText nameEditText;
    Button okButton;

    public static Fragment newInstance(Data data)
    {
        AuthFragment fragment = new AuthFragment();
        Bundle args = new Bundle();
        args.putSerializable("data", data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        publisher = ((PublishGetter) context).getPublisher();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_auth, null);
        data = (Data) getArguments().getSerializable("data");
        self = this;

        nameEditText = view.findViewById(R.id.et_name);
        okButton = view.findViewById(R.id.btn_ok);

        if(data.getName()!=null) {
            nameEditText.setText( data.getName() );
        }

        okButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = nameEditText.getText().toString().trim();
                if(name.isEmpty())
                {
                    Timber.d("setOnClickListener");
                    Toast.makeText(getActivity(), "Пожалуйста заполните все поля", Toast.LENGTH_SHORT).show();
                    return;
                }

                data.setName(name);
                publisher.notify(0);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .remove(self)
                        .commit();
            }
        });
        return view;

    }

}
