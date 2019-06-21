package com.example.fds75.weather;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WeatherDetailActivity extends AppCompatActivity {
    String TAG = "WeatherDetail";
    EditText inp2;
    Button Ss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);
        String title = getIntent().getStringExtra("title");
        String daytime = getIntent().getStringExtra("daytime");
        String daywind = getIntent().getStringExtra("daywind");
        String max = getIntent().getStringExtra("max");
        String nighttime = getIntent().getStringExtra("nighttime");
        String nightwind = getIntent().getStringExtra("nightwind");
        String min = getIntent().getStringExtra("min");
        String s = getIntent().getStringExtra("html");


        ((TextView)findViewById(R.id.ontitle)).setText(title);
        ((TextView)findViewById(R.id.daytimetext)).setText(daytime);
        ((TextView)findViewById(R.id.daywindtext)).setText(daywind);
        ((TextView)findViewById(R.id.maxtext)).setText(max);
        ((TextView)findViewById(R.id.nighttimetext)).setText(nighttime);
        ((TextView)findViewById(R.id.nightwindtext)).setText(nightwind);
        ((TextView)findViewById(R.id.mintext)).setText(min);

        Ss = findViewById(R.id.res);

        Ss.setText(String.valueOf(s));

    }

    //用button转到网页，获取更多信息
    public void btnMore(View btn){
        //打开一个页面Activity
        Log.i("open","openOne");

        String newS = String.valueOf(Ss.getText().toString());
        //用intent对象传参数
        Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse(""+newS+""));
        startActivity(web);
    }
}
