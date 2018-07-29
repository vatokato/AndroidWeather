package com.example.vatok.androidweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import io.paperdb.Paper;
import timber.log.Timber;

public class AuthActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText nameEditText;
    Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        nameEditText = findViewById(R.id.et_name);
        okButton = findViewById(R.id.btn_ok);

        okButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
            String name = nameEditText.getText().toString().trim();
            if(name.isEmpty())
            {
                Timber.d("setOnClickListener");
                Toast.makeText(AuthActivity.this, "Пожалуйста заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(AuthActivity.this, MainActivity.class);
            intent.putExtra("name" , name);
            startActivity(intent );
            finish();
            }
        });
    }

}
