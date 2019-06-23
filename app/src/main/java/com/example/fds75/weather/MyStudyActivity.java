package com.example.fds75.weather;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyStudyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_study);

    }


    public void studyone(View btn){
        //打开一个页面Activity
        Log.i("open","stutyone");
        Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.swufe.edu.cn/"));
//        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"));
        startActivity(web);
    }
    public void studytwo(View btn){
        //打开一个页面Activity
        Log.i("open","stutytwo");
        Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("https://jwc.swufe.edu.cn/"));
//        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"));
        startActivity(web);
    }
    public void studythree(View btn){
        //打开一个页面Activity
        Log.i("open","stutythree");
        Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("https://jwc.swufe.edu.cn/"));
//        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"));
        startActivity(web);
    }
}
