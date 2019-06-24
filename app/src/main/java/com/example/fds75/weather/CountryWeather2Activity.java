package com.example.fds75.weather;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.fds75.weather.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CountryWeather2Activity extends ListActivity implements Runnable,AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {

    private String TAG = "mylist2";
    String data[] = {"wait..."};
    Handler handler;
    private List<HashMap<String,String>> listItems;//存放文字、图片信息
    private SimpleAdapter listItemAdapter;//适配器


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.list_item);
        initListView();
        this.setListAdapter(listItemAdapter);

        ListAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);//当前对象，布局，数据
        setListAdapter(adapter);

        Thread t = new Thread(this);
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 7) {
//                    List<HashMap<String, String>> list2 = (List<HashMap<String, String>>) msg.obj;
//                    listItemAdapter = new SimpleAdapter(MyList2Activity.this, list2,//listitems数据源
                    listItems  = (List<HashMap<String, String>>) msg.obj;
                    listItemAdapter = new SimpleAdapter(CountryWeather2Activity.this, listItems,//listitems数据源
                            R.layout.list_item,//Listitem的XML布局实现
                            new String[]{"ItemTitle", "ItemDetail"},
                            new int[]{R.id.itemTitle, R.id.itemDetail}
                    );
                    setListAdapter(listItemAdapter);
                }
                super.handleMessage(msg);
            }
        };
        getListView().setOnItemClickListener(this);//获取当前listView控件
        getListView().setOnItemLongClickListener(this);//长按时获取
    }


    private void initListView(){
        listItems = new ArrayList<HashMap<String, String>>();
        for(int i=0;i<10;i++){
            HashMap<String,String>map = new HashMap<String, String>();
            map.put("ItemTitle","City: "+i);//标题文字
            map.put("ItemDetail","detail"+i);//详情描述
            listItems.add(map);
        }
        //生成适配器的item和动态数组对应的元素
        listItemAdapter = new SimpleAdapter(this,listItems,//listitems数据源
                R.layout.list_item,//Listitem的XML布局实现
                new String[]{"ItemTitle","ItemDetail"},
                new int[]{R.id.itemTitle,R.id.itemDetail}
        );
    }


    @Override
    public void run() {
        //获取网络数据，放入list带回到主线程中
        List<HashMap<String,String>> retList = new ArrayList<HashMap<String, String>>();
        Document doc = null;
        try {
            Thread.sleep(500);
            doc = Jsoup.connect("http://www.weather.com.cn/textFC/sichuan.shtml").get();
            //doc = Jsoup.parse(html);
            Log.i(TAG, "run: "+doc.title());
            Elements tables = doc.getElementsByTag("table");

            //获取td中的数据,通过for循环获取到21个table中的数据
            for(int j=1;j<=21;j++) {
                Element table2 = tables.get(j);
                Elements tds = table2.getElementsByTag("td");

                for (int i = 1; i < tds.size(); i += 8) {
                    Element maincity = tds.get(0);
                    Element city = tds.get(i);
                    Element daytime = tds.get(i + 1);
                    Element daywind = tds.get(i + 2);
                    Element max = tds.get(i + 3);
                    Element nighttime = tds.get(i + 4);
                    Element nightwind = tds.get(i + 5);
                    Element min = tds.get(i + 6);
                    String s = tds.get(i+7).select("a").attr("href");

                    Log.i(TAG, "run: text=" + city.text() + ">白天：" + daytime.text() + ">夜间：" + nighttime.text() + ">>最高：" + max.text() + ">最低：" + min.text()+"详情网址"+s);
                    HashMap<String,String> map = new HashMap<String, String>();
                    map.put("ItemTitle",maincity.text()+"——>>"+city.text());
                    map.put("ItemDetail","温度变化："+max.text()+"℃~"+min.text()+"℃");
                    map.put("city",city.text());
                    map.put("daytime",daytime.text());
                    map.put("daywind",daywind.text());
                    map.put("max",max.text());
                    map.put("nighttime",nighttime.text());
                    map.put("nightwind",nightwind.text());
                    map.put("min",min.text());
                    map.put("html",s);
                    retList.add(map);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        Message msg = handler.obtainMessage(7);
        msg.obj = retList;
        handler.sendMessage(msg);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.i(TAG,"onItemClick:parent="+parent);
        Log.i(TAG,"onItemClick:view="+view);
        Log.i(TAG,"onItemClick:position="+position);
        Log.i(TAG,"onItemClick:id="+id);
        HashMap<String,String>map = (HashMap<String, String>) getListView().getItemAtPosition(position);//从数据中获得控件
        String titleStr = map.get("city");
        String daytimeStr = map.get("daytime");
        String daywindStr = map.get("daywind");
        String maxStr = map.get("max");
        String nighttimeStr = map.get("nighttime");
        String nightwindStr = map.get("nightwind");
        String minStr = map.get("min");
        String sStr = map.get("html");
        Log.i(TAG, "onItemClick: city="+titleStr);
        Log.i(TAG, "onItemClick: daytimeStr="+daytimeStr);
        Log.i(TAG, "onItemClick: daywindStr="+daywindStr);
        Log.i(TAG, "onItemClick: maxStr="+maxStr);
        Log.i(TAG, "onItemClick: nighttimeStr="+nighttimeStr);
        Log.i(TAG, "onItemClick: nightwindStr="+nightwindStr);
        Log.i(TAG, "onItemClick: minStr="+minStr);
        Log.i(TAG, "onItemClick: sStr="+sStr);


        TextView title = (TextView) view.findViewById(R.id.itemTitle);
        TextView detail = (TextView) view.findViewById(R.id.itemDetail);
        String title2 = String.valueOf(title.getText());//从控件中获得
        String detail2 = String.valueOf(detail.getText());

        //打开新的页面传递参数
        Intent rateCalc = new Intent(this,WeatherDetailActivity.class);
        rateCalc.putExtra("title",titleStr);
        rateCalc.putExtra("daytime",daytimeStr);
        rateCalc.putExtra("daywind",daywindStr);
        rateCalc.putExtra("max",maxStr);
        rateCalc.putExtra("nighttime",nighttimeStr);
        rateCalc.putExtra("nightwind",nightwindStr);
        rateCalc.putExtra("min",minStr);
        rateCalc.putExtra("html",sStr);
          startActivity(rateCalc);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
        Log.i(TAG, "onItemLongClick: 长按position="+position);

        //构造AlertDialog对话框进行确认操作
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //基本结构：builder.setTitle("提示").setMessage("请确认是否删除当前数据").setPositiveButton("是",null).setNegativeButton("否",null);
        builder.setTitle("提示").setMessage("请确认是否删除当前数据").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "onClick: 对话框事件处理");
                listItems.remove(position);
                listItemAdapter.notifyDataSetChanged();
            }
        }).setNegativeButton("否",null);
        builder.create().show();

        Log.i(TAG, "onItemLongClick: size="+listItems.size());//观察数据长度，观察是否删除

        return true;//true表示长按后不会再执行短按操作内容，false则表示长按后将执行短按的操作
    }
}
