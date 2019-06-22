package com.example.fds75.weather;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MyDailySQLActivity extends ListActivity implements Runnable{
    String data[] = {"wait..."};
    private final String TAG = "Rate";
    Handler handler;
    private String logDate = "";
    private final String DATE_SP_KEY = "lastRateDateStr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_rate_list);(父类已包含布局，此处不需要)

        SharedPreferences sp = getSharedPreferences("mydaily",Context.MODE_PRIVATE);

        List<String> list1 = new ArrayList<String>();
        for(int i=1;i<=100;i++){
            list1.add("item"+i);
        }

        ListAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);//当前对象，布局，数据
        setListAdapter(adapter);

        Thread t = new Thread(this);
        t.start();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==7){
                    List<String> list2 = (List<String>) msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(MyDailySQLActivity.this,android.R.layout.simple_list_item_1,list2);//当前对象，布局，数据
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };

    }

    @Override
    public void run() {
        //获取数据，放入list带回到主线程
        List<String> retList = new ArrayList<String>();

            //获取数据
        Log.i("run", "从数据库中获取数据 ");
        DBManager manager = new DBManager(this);
        for (DailyItem item : manager.listAll()){
            retList.add(item.getCurName()+"-->"+item.getCurDate()+"-->"+item.getCurDetail());
        }
        

        Message msg = handler.obtainMessage(7);
        msg.obj = retList;
        handler.sendMessage(msg);//将msg发送到队列里
    }

}
