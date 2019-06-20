package com.example.fds75.weather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DdlActivity extends AppCompatActivity{
    private final String TAG = "DdlActivity";
    private String text1 = "", text2 = "", text3 = "", text4 = "", text5 = "", text6 = "";

    Handler handler;
    TextView show1,show2,show3,show4,show5,show6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ddl);

        show1 = (TextView) findViewById(R.id.textV1);
        show2 = (TextView) findViewById(R.id.textV2);
        show3 = (TextView) findViewById(R.id.textV3);
        show4 = (TextView) findViewById(R.id.textV4);
        show5 = (TextView) findViewById(R.id.textV5);
        show6 = (TextView) findViewById(R.id.textV6);

        SharedPreferences sharedPreferences = getSharedPreferences("myddl",Activity.MODE_PRIVATE);
        //另：SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(this);
        text1 = sharedPreferences.getString("t_1","");
        text2 = sharedPreferences.getString("t_2","");
        text3 = sharedPreferences.getString("t_3","");
        text4 = sharedPreferences.getString("t_4","");
        text5 = sharedPreferences.getString("t_5","");
        text6 = sharedPreferences.getString("t_6","");

    }

    public void newBtn(View btn){
        //获取用户输入内容
        String tet1 = show1.getText().toString();
        String tet2 = show2.getText().toString();
        String tet3 = show3.getText().toString();
        String tet4 = show4.getText().toString();
        String tet5 = show5.getText().toString();
        String tet6 = show6.getText().toString();

        show1.setText(String.format(text1));
        show2.setText(String.format(text2));
        show3.setText(String.format(text3));
        show4.setText(String.format(text4));
        show5.setText(String.format(text5));
        show6.setText(String.format(text6));
    }

    public void ddl(View btn){
            //打开一个页面Activity
        Log.i("open", "ddl");

        //用intent对象传参数
        Intent config = new Intent(this, ChangeDdlActivity.class);
//        Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jd.com"));
//        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"));
        //调用方法，传到下个页面
        config.putExtra("t1", text1);
        config.putExtra("t2", text2);
        config.putExtra("t3", text3);
        config.putExtra("t4", text4);
        config.putExtra("t5", text5);
        config.putExtra("t6", text6);

        Log.i(TAG, "ddl:text1=" + text1);

        //startActivity(config);
        startActivityForResult(config, 1);//表示打开这个窗口是为了带回数据,"1"是请求代码
    }



    //处理带回数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //requestCode区分是谁返回的数据，resultCode区分返回的数据按什么格式拆分
        if(requestCode==1 && resultCode==2){
            Bundle bundle = data.getExtras();
            text1 = bundle.getString("key_t1");//在bundle中获取新的值
            text2 = bundle.getString("key_t2");
            text3 = bundle.getString("key_t3");
            text4 = bundle.getString("key_t4");
            text5 = bundle.getString("key_t5");
            text6 = bundle.getString("key_t6");

            Log.i(TAG,"onAcitivityResult:text1="+text1);

            //将新写入的ddl写到SharedPreference里
            SharedPreferences sharedPreferences = getSharedPreferences("myddl",Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("t_1",text1);
            editor.putString("t_2",text2);
            editor.putString("t_3",text3);
            editor.putString("t_4",text4);
            editor.putString("t_5",text5);
            editor.putString("t_6",text6);
            editor.commit();
            Log.i(TAG,"onActivityResult:数据以保存到SharedPreferences");


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}


