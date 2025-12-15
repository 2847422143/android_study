package com.example.android_study.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.os.Process;
import androidx.annotation.Nullable;

import com.example.android_study.IMyAidlInterface;


//服务端，接收
public class AidlService extends Service {

    public final String TAG = this.getClass().getSimpleName();

    private final IMyAidlInterface.Stub iMyAidlInterface = new IMyAidlInterface.Stub() {
        @Override
        public String getServiceMsg(String clientMsg) throws RemoteException {
            int currentPid = Process.myPid();
            Log.d(TAG, "getServiceMsg 执行，进程ID：" + currentPid);
            return "服务端（独立进程）收到消息：" + clientMsg;
        }
        @Override
        public int add(int a, int b) throws RemoteException {
            Log.d(TAG,"a = "+a+" b = "+b);
            return a+b;
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iMyAidlInterface;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        int servicePid = Process.myPid();
        Log.d(TAG, "AidlService 创建成功，进程ID：" + servicePid);
        // 可选：打印进程名（更直观）
        String processName = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            processName = android.os.Process.myProcessName();
        }
        Log.d(TAG, "AidlService 进程名：" + processName);
    }
}
