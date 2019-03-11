package com.example.duanxiaqingqq.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerActivity extends AppCompatActivity {

    @BindView(R.id.rlv_recyclerac)
    RecyclerView rlvRecyclerac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        ButterKnife.bind(this);

        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add("萌哈哈" + i);
        }

        ArrayList<Bean> arrayLists = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            arrayLists.add(new Bean(strings,"扣脚"+i));
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlvRecyclerac.setLayoutManager(linearLayoutManager);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(strings, arrayLists,this);
        rlvRecyclerac.setAdapter(recyclerAdapter);
    }
}
