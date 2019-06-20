package com.example.fds75.weather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class ChangeDdlActivity extends AppCompatActivity {
    private final String TAG = "ChangeDdlActivity" ;

    EditText text1,text2,text3,text4,text5,text6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_ddl);
        Intent intent = getIntent();//获得当前的intent对象
        String t1 = intent.getStringExtra("t1");//获取数据
        String t2 = intent.getStringExtra("t2");
        String t3 = intent.getStringExtra("t3");
        String t4 = intent.getStringExtra("t4");
        String t5 = intent.getStringExtra("t5");
        String t6 = intent.getStringExtra("t6");

        Log.i("ChangeDdlActivity","onCreat:t1="+t1);

        text1=(EditText)findViewById(R.id.text_V1);
        text2=(EditText)findViewById(R.id.text_V2);
        text3=(EditText)findViewById(R.id.text_V3);
        text4=(EditText)findViewById(R.id.text_V4);
        text5=(EditText)findViewById(R.id.text_V5);
        text6=(EditText)findViewById(R.id.text_V6);

        //显示数据到控件
        text1.setText(String.valueOf(t1));
        text2.setText(String.valueOf(t2));
        text3.setText(String.valueOf(t3));
        text4.setText(String.valueOf(t4));
        text5.setText(String.valueOf(t5));
        text6.setText(String.valueOf(t6));
    }

    public void save(View btn) {
        Log.i(TAG,"save:");
        //获取新的值
        String newText1 = String.valueOf(text1.getText().toString());
        String newText2 = String.valueOf(text2.getText().toString());
        String newText3 = String.valueOf(text3.getText().toString());
        String newText4 = String.valueOf(text4.getText().toString());
        String newText5 = String.valueOf(text5.getText().toString());
        String newText6 = String.valueOf(text6.getText().toString());
        Log.i(TAG,"save:获取到新的值");
        Log.i("ChangeDdlActivity","save:text1="+newText1);

        //保存到Bundle或放入到Extra
        Intent intent = getIntent();
        Bundle bdl = new Bundle();
        bdl.putString("key_t1",newText1);//这里的key可以与上面相同，也可不同
        bdl.putString("key_t2",newText2);
        bdl.putString("key_t3",newText3);
        bdl.putString("key_t4",newText4);
        bdl.putString("key_t5",newText5);
        bdl.putString("key_t6",newText6);
        intent.putExtras(bdl);//打包带回
        setResult(2,intent);//“2”是相应代码

        //返回到调用页面
        finish();
    }


}


