package com.example.android_study.broadcast;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

//动态广播接收器
public class DynamicBroadcase extends BroadcastReceiver {
    private static final String TAG = "DynamicBroadcase";
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG,"DynamicBroadcase 收到广播 ：" +
                "\n Action = " + intent.getAction() +
                "\n name = " + intent.getStringExtra("name") +
                "\n message = " + intent.getStringExtra("message"));
    }
}
