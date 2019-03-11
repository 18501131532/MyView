package com.example.duanxiaqingqq.myapplication;

import android.app.ActivityThread;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(MainActivity.this, RecyclerActivity.class));
//        CustomView customView = new CustomView(this);
    }
}
