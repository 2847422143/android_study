// IMyAidlInterface.aidl
package com.example.android_study;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
    String getServiceMsg(String clientMsg);
    int add(int a, int b);
}