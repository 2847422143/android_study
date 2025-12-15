package com.example.android_study.observer;

import android.util.Log;

public class ObserverImpl extends AbstractObserver {
    String name;
    public ObserverImpl(String name){
        this.name = name;
    }
    @Override
    public void change(String b) {
        Log.d("ObserverImpl",name + "收到了" + b);
    }

    @Override
    public void setName(String name1) {
        Log.d("ObserverImpl",name + "改名成" + name1);
        this.name = name1;
    }
}
