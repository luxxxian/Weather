package com.example.fds75.weather;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CountryWeatherActivity extends AppCompatActivity implements Runnable{
    String data[] = {"wait..."};
    private final String TAG = "weather";
    Handler handler;
    private String logDate = "";
    private final String DATE_SP_KEY = "lastDateStr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_weather);//(父类已包含布局，此处不需要)

        SharedPreferences sp = getSharedPreferences("myrate",Context.MODE_PRIVATE);
        logDate = sp.getString(DATE_SP_KEY,"");
        Log.i("list", "lastDateStr="+logDate);

        List<String> list1 = new ArrayList<String>();
        for(int i=1;i<=200;i++){
            list1.add("item"+i);
        }

        final ListView listView = (ListView)findViewById(R.id.city);

        Thread t = new Thread(this);
        t.start();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==7){
                    List<String> list2 = (List<String>) msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(CountryWeatherActivity.this,android.R.layout.simple_list_item_1,list2);//当前对象，布局，数据
                    listView.setAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };

    }

    @Override
    public void run() {
        //获取网络数据，放入list带回到主线程
        List<String> retList = new ArrayList<String>();
        //获得当前日期
        String curDAteStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
        Log.i("run", "curDateStr: " + curDAteStr + "logDate:" + logDate);

        if (curDAteStr.equals(logDate)) {
            //如果日期相等，则不从网络中获取数据
            Log.i("run", "日期相等，不从网络中获取数据，从数据库中获取数据 ");
//            WeaManager manager = new WeaManager(this);
//            for (RateItem item : manager.listAll()){
//                retList.add(item.getCurName()+"-->"+item.getCurRate());
//            }

        } else {
            //从网络中获得数据
            Log.i("run", "日期不相等，从网络中获取数据 ");
            Document doc = null;
            try {
                Thread.sleep(1000);
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
                        Element maincity = tds.get(0);
                        Element city = tds.get(i);
                        Element daytime = tds.get(i + 1);
                        Element max = tds.get(i + 3);
                        Element nighttime = tds.get(i + 4);
                        Element min = tds.get(i + 6);

                        Log.i(TAG, "run: text=" + city.text() + ">白天：" + daytime.text() + ">夜间：" + nighttime.text() + ">>最高：" + max.text() + ">最低：" + min.text());
                        //retList.add(city.text() + ">白天：" + daytime.text() + ">夜间：" + nighttime.text() + ">>最高：" + max.text() + ">最低：" + min.text());
                        retList.add(maincity.text() + ">>>" + city.text() + ">>最高：" + max.text() + ">最低：" + min.text());

                    }
                }

//                //把数据写入数据库
//                RateManager manager = new RateManager(this);
//                manager.deleteAll();
//                manager.addAll(rateList);
//
//                //更新记录日期
//                SharedPreferences sp = getSharedPreferences("myrate",Context.MODE_PRIVATE);
//                SharedPreferences.Editor edit = sp.edit();
//                edit.putString(DATE_SP_KEY,curDAteStr);
//                edit.commit();
//                Log.i("run", "更新日期结束: "+curDAteStr);

            } catch (IOException e) {
                e.printStackTrace();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Message msg = handler.obtainMessage(7);
        msg.obj = retList;
        handler.sendMessage(msg);//将msg发送到队列里


    }
}
