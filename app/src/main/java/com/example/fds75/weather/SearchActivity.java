package com.example.fds75.weather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.security.auth.login.LoginException;

public class SearchActivity extends AppCompatActivity{
    String TAG = "SearchActivity";
    String weather1;
    EditText weatherText;
    TextView city;
    TextView clim1;
    TextView clim2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        city = (TextView)findViewById(R.id.search_of_city);
        clim1 = (TextView)findViewById(R.id.search_climate1);
        clim2 = (TextView)findViewById(R.id.search_climate2);

//       weatherText.setText(String.format(weather1));
    }

    public void search(View btn){
        weatherText = (EditText)findViewById(R.id.search_city);
        weather1 = weatherText.getText().toString();
//        String Scity = city.getText().toString();


        Log.i(TAG, "search: 按钮weatherText"+weatherText);
        Log.i(TAG, "search: 按钮weather1"+weather1);

        if(btn.getId()==R.id.search_weather) {
            WEManager wem = new WEManager(SearchActivity.this);
            city.setText(weather1);
            Log.i(TAG, "search: day"+wem.findDay(weather1));
            clim1.setText(wem.findDay(weather1)+">>"+wem.findNight(weather1));
            clim2.setText(wem.findMAX(weather1)+">>"+wem.findMIN(weather1));
        }

    }
}
