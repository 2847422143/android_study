package com.example.android_study.observer;


import java.util.ArrayList;
import java.util.List;

public class ObserverEX implements  ObserverIN{

    List<Observer> observers = new ArrayList<>();

    @Override
    public void add(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void delete(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver(String b) {
        for(Observer a : observers){
            a.change(b);
        }
    }
}
