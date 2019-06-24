package com.example.fds75.weather;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MyDailyActivity extends AppCompatActivity {
    private String TAG = "Daily";
    EditText my_name,my_date,my_detail;
    String my_name1,my_date1,my_detail1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_daily);

        Intent intent = getIntent();//获得当前的intent对象
        String my1 = intent.getStringExtra("my1");
        String my2 = intent.getStringExtra("my2");
        String my3 = intent.getStringExtra("my3");

        my_name = findViewById(R.id.add_main);
        my_date = findViewById(R.id.add_date);
        my_detail = findViewById(R.id.add_detail);

        my_name.setText(String.valueOf(my1));
        my_date.setText(String.valueOf(my2));
        my_detail.setText(String.valueOf(my3));
    }

    public void onSave(View btn){


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确认要保存吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface arg0, int arg1){
                Toast.makeText(MyDailyActivity.this,"确定成功！",Toast.LENGTH_SHORT).show();
                String newmy1 = String.valueOf(my_name.getText().toString());
                String newmy2 = String.valueOf(my_date.getText().toString());
                String newmy3 = String.valueOf(my_detail.getText().toString());

                DailyItem item1 = new DailyItem(""+newmy1+"",""+newmy2+"",""+newmy3+"");
                DBManager manager = new DBManager(MyDailyActivity.this);

                manager.add(item1);
                Log.i(TAG, "onSave: 写入数据完毕");

                // 查询所有数据
                List<DailyItem> testList =manager.listAll();
                for (DailyItem i : testList){
                    Log.i(TAG, "onSave: 取出数据id="+i.getId()+">>Name="+i.getCurName()+">>Date="+i.getCurDate()+">>Detail="+i.getCurDetail());
                }
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface arg0,int arg1){
                Toast.makeText(MyDailyActivity.this, "取消成功！", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }

    public void onLook(View btn){
        //打开一个页面Activity
        Log.i("open","onLook");
        //用intent对象传参数
        Intent look = new Intent(this,MyDailySQLActivity.class);

        my_name1 = String.valueOf(my_name.getText().toString());
        my_date1 = String.valueOf(my_date.getText().toString());
        my_detail1 = String.valueOf(my_detail.getText().toString());
        look.putExtra("my1", my_name1);
        look.putExtra("my1", my_date1);
        look.putExtra("my1", my_detail1);
        startActivity(look);
    }

}
