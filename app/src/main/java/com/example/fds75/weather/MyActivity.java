package com.example.fds75.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MyActivity extends AppCompatActivity {

    TextView city,clim1,clim2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        city = (TextView)findViewById(R.id.city);
        clim1 = (TextView)findViewById(R.id.climate1);
        clim2 = (TextView)findViewById(R.id.climate2);

    }

    public void onClick(View btn){
        String Scity = city.getText().toString();
        String Sclim1 = clim1.getText().toString();
        String Sclim2 = clim2.getText().toString();

        city.setText(String.format(Scity));
        clim1.setText(String.format(Sclim1));
        clim2.setText(String.format(Sclim2));

    }
}
