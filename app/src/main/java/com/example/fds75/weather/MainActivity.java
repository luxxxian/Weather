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
        Intent config = new Intent(this,CountryWeatherActivity.class);
//        Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jd.com"));
//        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"));
        startActivity(config);

        //startAction(new Intent(Intent.ACTION_VIEW,Uri.parse("www.baidu.com")));跳转网页
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
        getMenuInflater().inflate(R.menu.search,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent config;
        if (item.getItemId()==R.id.menu_search){
            config = new Intent(this,SearchActivity.class);
            startActivity(config);
        }else if (item.getItemId()==R.id.menu_study){
            config = new Intent(this,MyStudyActivity.class);
            startActivity(config);
        }

        return super.onOptionsItemSelected(item);
    }

}
