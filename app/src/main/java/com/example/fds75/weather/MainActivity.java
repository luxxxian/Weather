package com.example.fds75.weather;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openOne(View btn){
        //打开一个页面Activity
        Log.i("open","openOne");
        //用intent对象传参数
        Intent config = new Intent(this,CountryWeather2Activity.class);
        startActivity(config);

    }

    public void mine(View btn){
        //打开一个页面Activity
        Log.i("open","openOne");
        //用intent对象传参数
        Intent config = new Intent(this,MyActivity.class);
        startActivity(config);
    }

    public void Myddl(View btn){
        //打开一个页面Activity
        Log.i("open","openOne");
        //用intent对象传参数
        Intent config = new Intent(this,DdlActivity.class);
        startActivity(config);
    }

    public void MyDay(View btn){
        //打开一个页面Activity
        Log.i("open","openOne");
        //用intent对象传参数
        Intent config = new Intent(this,MyDailyActivity.class);
        startActivity(config);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search2,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent config;
        if (item.getItemId()==R.id.menu_search){
            config = new Intent(this,SearchActivity.class);
            startActivity(config);
        }

        return super.onOptionsItemSelected(item);
    }

}
