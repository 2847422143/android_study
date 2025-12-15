package com.example.android_study.observer;



public interface ObserverIN {

    //添加订阅者
    void add(Observer observer);

    //删除订阅者
    void delete(Observer observer);

    //通知
    void notifyObserver(String name);
}
