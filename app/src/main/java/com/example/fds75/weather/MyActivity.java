package com.example.fds75.weather;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

public class MyActivity extends AppCompatActivity implements Runnable{
    private String TAG="MyActivity";
    private String Scity,Sclim1,Sclim2,Scity2,Sclim12,Sclim22,Scity3,Sclim13,Sclim23;

    TextView city,clim1,clim2;
    Handler handler;
    private String updateDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        city = (TextView)findViewById(R.id.city);
        clim1 = (TextView)findViewById(R.id.climate1);
        clim2 = (TextView)findViewById(R.id.climate2);

        SharedPreferences sharedPreferences =getSharedPreferences("my",Activity.MODE_PRIVATE);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Scity = sharedPreferences.getString("city-date","");
        Sclim1 = sharedPreferences.getString("day-date","");
        Sclim2 = sharedPreferences.getString("temper-date","");

        Scity2 = sharedPreferences.getString("city-date2","");
        Sclim12 = sharedPreferences.getString("day-date2","");
        Sclim22 = sharedPreferences.getString("temper-date2","");

        Scity3 = sharedPreferences.getString("city-date3","");
        Sclim13 = sharedPreferences.getString("day-date3","");
        Sclim23 = sharedPreferences.getString("temper-date3","");

        //获取当前系统时间
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//将时间转换成字符串
        final String todayStr = sdf.format(today);

        Log.i(TAG, "onCreate: Scity"+Scity);
        Log.i(TAG, "onCreate: Sclim1"+Sclim1);
        Log.i(TAG, "onCreate: Sclim2"+Sclim2);
        Log.i(TAG, "onCreate: updateDate="+updateDate);

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
                    Scity = bdl.getString("city-date");
                    Sclim1 = bdl.getString("day-date");
                    Sclim2 = bdl.getString("temper-date");

                    Scity2 = bdl.getString("city-date2");
                    Sclim12 = bdl.getString("day-date2");
                    Sclim22 = bdl.getString("temper-date2");

                    Scity3 = bdl.getString("city-date3");
                    Sclim13 = bdl.getString("day-date3");
                    Sclim23 = bdl.getString("temper-date3");

//                    Log.i(TAG, "handleMessage: date"+Scity);
//                    Log.i(TAG, "handleMessage: date"+Sclim1);
//                    Log.i(TAG, "handleMessage: date"+Sclim2);

                    //保存更新的日期
                    SharedPreferences sharedPreferences = getSharedPreferences("myrate",Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("city-date",Scity);
                    editor.putString("day-date",Sclim1);
                    editor.putString("temper-date",Sclim2);

                    editor.putString("city-date2",Scity2);
                    editor.putString("day-date2",Sclim12);
                    editor.putString("temper-date2",Sclim22);

                    editor.putString("city-date3",Scity3);
                    editor.putString("day-date3",Sclim13);
                    editor.putString("temper-date3",Sclim23);

                    editor.putString("update_date",todayStr);
                    editor.apply();//保存数据

                    Toast.makeText(MyActivity.this,"气温已更新",Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);
            }
        };

    }

    public void onClick(View btn){
//        String Scity = city.getText().toString();
//        String Sclim1 = clim1.getText().toString();
//        String Sclim2 = clim2.getText().toString();

        if(btn.getId()==R.id.btn_cd){
            city.setText(String.format(Scity));
            clim1.setText(String.format(Sclim1));
            clim2.setText(String.format(Sclim2));
        } else if(btn.getId()==R.id.btn_nc){
            city.setText(String.format(Scity2));
            clim1.setText(String.format(Sclim12));
            clim2.setText(String.format(Sclim22));
        }else {
            city.setText(String.format(Scity3));
            clim1.setText(String.format(Sclim13));
            clim2.setText(String.format(Sclim23));
        }



    }


    @Override
    public void run() {
        Log.i(TAG, "run:run... ");
        for(int i=1;i<3;i++){//加上一个空循环做延时操作
            Log.i(TAG,"run:i"+i);
            try {//由于此方法容易产生异常，就需捕获它
                Thread.sleep(1000);//让这个线程停止两秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //获取网络数据
//        URL url = null;
//        try {
//            url = new URL("http://www.weather.com.cn/textFC/sichuan.shtml");
//            HttpURLConnection http = (HttpURLConnection) url.openConnection();
//            InputStream in = http.getInputStream();
//
//            String html = inputStream2String(in);
//            Log.i(TAG, "run: ="+html);
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //用于保存获取的天气数据
        Bundle bundle = new Bundle();


        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.weather.com.cn/textFC/sichuan.shtml").get();
            //doc = Jsoup.parse(html);
            Log.i(TAG, "run: "+doc.title());
            Elements tables = doc.getElementsByTag("table");
//            int i=1;
//            for(Element table :tables){
//                Log.i(TAG, "run: table["+i+"]="+table);
//            }

//            Element table2 = tables.get(1);
//            Element table3 = tables.get(2);
            //Log.i(TAG, "run: table2"+table2);



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

                    if ("成都".equals(city.text())){
                        bundle.putString("city-date",city.text());
                        bundle.putString("day-date","日间；"+daytime.text()+">>夜间："+nighttime.text());
                        bundle.putString("temper-date","最高；"+max.text()+"℃  >>最低："+min.text()+"℃");
                    }
                    else if ("南充".equals(city.text())){
                        bundle.putString("city-date2",city.text());
                        bundle.putString("day-date2","日间；"+daytime.text()+">>夜间："+nighttime.text());
                        bundle.putString("temper-date2","最高；"+max.text()+"℃  >>最低："+min.text()+"℃");
                    }else if ("温江".equals(city.text())){
                        bundle.putString("city-date3",city.text());
                        bundle.putString("day-date3","日间；"+daytime.text()+">>夜间："+nighttime.text());
                        bundle.putString("temper-date3","最高；"+max.text()+"℃  >>最低："+min.text()+"℃");
                    }
                }
            }
//            for (Element td : tds){
//                Log.i(TAG, "run: td="+td);
//                Log.i(TAG, "run: html="+td.html());
//            }

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
