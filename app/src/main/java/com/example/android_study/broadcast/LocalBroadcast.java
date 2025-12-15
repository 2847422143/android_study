package com.example.android_study.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

//静态广播接收器
public class LocalBroadcast extends BroadcastReceiver {
    private static final String TAG = "Localbroadcast";
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG,"LocalBroadcast 收到广播 ：" +
                "\n Action = " + intent.getAction() +
                "\n name = " + intent.getStringExtra("name") +
                "\n message = " + intent.getStringExtra("message"));
    }
}
