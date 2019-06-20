package com.example.fds75.weather;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import javax.security.auth.login.LoginException;

public class MyActivity extends AppCompatActivity implements Runnable{
    private String TAG="MyActivity";

    TextView city,clim1,clim2;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        city = (TextView)findViewById(R.id.city);
        clim1 = (TextView)findViewById(R.id.climate1);
        clim2 = (TextView)findViewById(R.id.climate2);

        //开启子线程
        Thread t = new Thread(this);//在此处需加上当前对象
        t.start();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==5){//观察是否与run方法中的what相同
                    String str = (String) msg.obj;
                    Log.i(TAG,"handleMessage:getMessage msg = "+str);
                    city.setText(str);
                    clim1.setText(str);
                    clim2.setText(str);

                    Toast.makeText(MyActivity.this,"气温已更新",Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);
            }
        };

    }

    public void onClick(View btn){
        String Scity = city.getText().toString();
        String Sclim1 = clim1.getText().toString();
        String Sclim2 = clim2.getText().toString();

        city.setText(String.format(Scity));
        clim1.setText(String.format(Sclim1));
        clim2.setText(String.format(Sclim2));

    }


    @Override
    public void run() {
        Log.i(TAG, "run:run... ");
        for(int i=1;i<3;i++){//加上一个空循环做延时操作
            Log.i(TAG,"run:i"+i);
            try {//由于此方法容易产生异常，就需捕获它
                Thread.sleep(2000);//让这个线程停止两秒
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

            Element table2 = tables.get(1);
            //Log.i(TAG, "run: table2"+table2);

            //获取td中的数据
            Elements tds = table2.getElementsByTag("td");
            for (Element td : tds){
                Log.i(TAG, "run: td="+td);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }



        //获取message对象，用于返回主线程
        Message msg = handler.obtainMessage();
        msg.what = 5;//what用于标记当前message的属性，用于整数
        //Message mag = handler.obtainMessage(5); 上面两排可直接通过定义what来写
        msg.obj = "Hello from run()";

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
