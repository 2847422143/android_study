package com.example.android_study.Messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MessengerService extends Service {
    private static final String TAG = "MessengerService";

    // 服务端 Handler：处理客户端发来的 Message
    private final Handler mServerHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            // 处理客户端消息
            switch (msg.what) {
                case 1001: // 自定义消息标识
                    String clientMsg = msg.getData().getString("client_msg");
                    Log.d(TAG, "收到客户端消息：" + clientMsg);

                    // 回复客户端（通过 msg.replyTo 获取客户端 Messenger）
                    if (msg.replyTo != null) {
                        Message replyMsg = Message.obtain();
                        replyMsg.what = 2001; // 回复消息标识
                        Bundle bundle = new Bundle();
                        bundle.putString("server_reply", "今天天气晴朗！！");
                        replyMsg.setData(bundle);
                        try {
                            // 跨进程回复客户端
                            msg.replyTo.send(replyMsg);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    // 服务端 Messenger：封装 Handler，用于和客户端通信
    private final Messenger mServerMessenger = new Messenger(mServerHandler);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // 返回 Messenger 的 Binder 对象，供客户端绑定
        return mServerMessenger.getBinder();
    }
}