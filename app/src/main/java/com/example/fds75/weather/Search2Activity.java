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

public class Search2Activity extends AppCompatActivity implements Runnable{
    String TAG = "SearchActivity";
    String searchweather;
    String weather1;
    String m;
    EditText weatherText;
    private String Scity,Sclim1,Sclim2;
    TextView city,clim1,clim2,weatherT;

    Handler handler;
    private String updateDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        //city = (TextView)findViewById(R.id.search_of_city);
        clim1 = (TextView)findViewById(R.id.search_climate1);
        clim2 = (TextView)findViewById(R.id.search_climate2);

        SharedPreferences sharedPreferences =getSharedPreferences("mine",SearchActivity.MODE_PRIVATE);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        //weather1 = sharedPreferences.getString("weather-date","");
        Scity = sharedPreferences.getString("city-date","");
        Sclim1 = sharedPreferences.getString("day-date","");
        Sclim2 = sharedPreferences.getString("temper-date","");

//        Scity = sharedPreferences.getString("city-date","");
//        Sclim1 = sharedPreferences.getString("day-date","");
//        Sclim2 = sharedPreferences.getString("temper-date","");

        Log.i(TAG, "onCreate: weather1"+weather1);

        //获取当前系统时间
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//将时间转换成字符串
        final String todayStr = sdf.format(today);


        //判断时间
        if(!todayStr.equals(updateDate)){
            Log.i(TAG,"需要更新");
            //开启子线程
            Thread t = new Thread(this);//在此处需加上当前对象
            t.start();
        }else {
            Log.i(TAG,"不需要更新");
        }

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==5){//观察是否与run方法中的what相同
                    Bundle bdl = (Bundle)msg.obj;
                    weather1 = bdl.getString("weather-daten");
                    Scity = bdl.getString("city-daten");
                    Sclim1 = bdl.getString("day-daten");
                    Sclim2 = bdl.getString("temper-daten");

                    SharedPreferences sharedPreferences = getSharedPreferences("mine",SearchActivity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("weather-daten",weather1);
                    editor.putString("city-daten",Scity);
                    editor.putString("day-daten",Sclim1);
                    editor.putString("temper-daten",Sclim2);

                    editor.putString("update_date",todayStr);
                    editor.apply();

                    Toast.makeText(Search2Activity.this,"气温已获取",Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);
            }
        };
    }

    public void search(View btn){
//        String Scity = city.getText().toString();
//        String Sclim1 = clim1.getText().toString();
//        String Sclim2 = clim2.getText().toString();
        weatherText = (EditText)findViewById(R.id.search_city);
        weather1 = weatherText.getText().toString();

        Log.i(TAG, "search: 按钮weatherText"+weatherText);
        Log.i(TAG, "search: 按钮m"+m);
        Log.i(TAG, "search: 按钮weather1"+weather1);

        if(btn.getId()==R.id.search_weather) {
            // city.setText(Scity);
            clim1.setText(Sclim1);
            clim2.setText(Sclim2);
        }

    }

    @Override
    public void run() {
        Log.i(TAG, "run:run... ");
        for(int i=1;i<3;i++){//加上一个空循环做延时操作
            Log.i(TAG,"run:i"+i);
            try {//由于此方法容易产生异常，就需捕获它
                Thread.sleep(200);//让这个线程停止两秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //用于保存获取的天气数据
        Bundle bundle = new Bundle();

        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.weather.com.cn/textFC/sichuan.shtml").get();
            //doc = Jsoup.parse(html);
            Log.i(TAG, "run: "+doc.title());
            Elements tables = doc.getElementsByTag("table");

            //获取td中的数据,通过for循环获取到21个table中的数据
            for(int j=1;j<=21;j++) {
                Element table2 = tables.get(j);
                Elements tds = table2.getElementsByTag("td");
                for (int i = 1; i < tds.size(); i += 8) {
                    Element city = tds.get(i);
                    Element daytime = tds.get(i + 1);
                    Element max = tds.get(i + 3);
                    Element nighttime = tds.get(i + 4);
                    Element min = tds.get(i + 6);
                    Log.i(TAG, "run: text=" + city.text() + ">白天：" + daytime.text() + ">夜间：" + nighttime.text() + ">>最高：" + max.text() + ">最低：" + min.text());


                    Log.i(TAG, "run: run weather"+weather1);
                    if (weather1.equals(city.text())){
                        bundle.putString("city-daten",city.text());
                        bundle.putString("day-daten","日间；"+daytime.text()+">>夜间："+nighttime.text());
                        bundle.putString("temper-daten","最高；"+max.text()+"℃  >>最低："+min.text()+"℃");
                    }
//                    else{
//                        bundle.putString("city-daten","输入不正确");
//                        bundle.putString("day-daten","");
//                        bundle.putString("temper-daten","");
//                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //bundle中保存所获取的数据

        //获取message对象，用于返回主线程
        Message msg = handler.obtainMessage();
        msg.what = 5;//what用于标记当前message的属性，用于整数
        //Message mag = handler.obtainMessage(5); 上面两排可直接通过定义what来写
        //msg.obj = "Hello from run()";
        msg.obj = bundle;

        handler.sendMessage(msg);//将msg发送到队列里
    }

    private String inputStream2String(InputStream inputStream) throws IOException {//将输入流转换成字符串输出
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "utf-8");//出现异常抛出
        for (; ; ) {
            int rsz = in.read(buffer, 0, buffer.length);//出现异常抛出
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }
}
