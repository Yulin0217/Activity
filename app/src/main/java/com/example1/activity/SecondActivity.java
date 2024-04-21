package com.example1.activity;


import static com.example1.activity.HuaweiIOT.getProperties;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class SecondActivity extends AppCompatActivity {

    //声明控件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //这是从服务器获得的JSON响应

        /*String jsonStr =
        *//*String jsonStr = "";*//*
        try {
            String[] values = HuaweiIOT.getProperties(jsonStr);
            ((TextView) findViewById(R.id.tvTemperature)).setText(String.valueOf(values[0]));
            ((TextView) findViewById(R.id.tvAir)).setText(String.valueOf(values[1]));
            ((TextView) findViewById(R.id.tvSoil)).setText(String.valueOf(values[2]));
            ((TextView) findViewById(R.id.tvLight)).setText(String.valueOf(values[3]));
        } catch (Exception e) {
            e.printStackTrace();
            //处理错误情况
        }*/
    }
}

