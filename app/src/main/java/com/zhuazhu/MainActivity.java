package com.zhuazhu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import zhuazhu.spider.view.SpiderView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SpiderView spiderView = findViewById(R.id.spider);
//        double[] values = {0.3,0.8,0.5,1,0.6,0.9};
//        spiderView.setValue(values);
    }
}
