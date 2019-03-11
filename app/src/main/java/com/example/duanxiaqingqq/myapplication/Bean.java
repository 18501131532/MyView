package com.example.duanxiaqingqq.myapplication;

import java.util.ArrayList;

public class Bean {
    ArrayList<String> mStrings;
    String text;

    public ArrayList<String> getStrings() {
        return mStrings;
    }

    public void setStrings(ArrayList<String> strings) {
        mStrings = strings;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Bean(ArrayList<String> strings, String text) {
        mStrings = strings;
        this.text = text;
    }
}
