package com.example.android_study;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.android_study.Event.InterceptLinearLayout;
import com.example.android_study.observer.Observer;
import com.example.android_study.observer.ObserverEX;
import com.example.android_study.observer.ObserverImpl;
import com.example.android_study.sharedPreferences.sharedPreferencesImpl;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    // AIDL接口实例（用于跨进程调用）
    private IMyAidlInterface mAidlInterface;
    // 标记是否已绑定aidl服务
    private boolean mIsServiceBound = false;

    // 服务端 Messenger：用于向服务端发送消息
    private Messenger mServerMessenger;
    // 客户端 Messenger：用于接收服务端的回复
    private Messenger mClientMessenger;

    private Messenger messenger;
    private ObserverImpl ObserverImpl1 = new ObserverImpl("小周");
    private ObserverImpl ObserverImpl2 = new ObserverImpl("小李");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 事件分发测试用
//        InterceptLinearLayout rootLayout = findViewById(R.id.root_layout);
//        rootLayout.setIntercept(true); // true=拦截，false=不拦截
        // 绑定按钮点击事件
        bindButtonClickEvents();
        // 绑定其他事件
        bindOtherEvents();
    }
    /**
     * 绑定所有按钮的点击事件
     */
    private void bindButtonClickEvents() {
        // 按钮1：观察者模式
        findViewById(R.id.btn_observer).setOnClickListener(v -> observerSend());

        // 按钮2：ContentProvider
        findViewById(R.id.btn_content_provider).setOnClickListener(v -> contentSend());

        // 按钮3：SharedPreferences
        findViewById(R.id.btn_sp).setOnClickListener(v -> sharedPreferencesSend());

        // 按钮4：AIDL通信
        findViewById(R.id.btn_aidl).setOnClickListener(v -> aidlSend());

        // 按钮5：Message通信
        findViewById(R.id.btn_Message).setOnClickListener(v -> MessageSend());
    }


    //分发流程图可见drawable中的activityevent.png
    //Window：它是抽象类，用于表示一个界面窗口，唯一的实现是PhoneWindow。每个 Activity 都包含一个 Window 对象，通过该 Window 对象来管理界面的布局、绘制和交互。
    //DecorView：作为整个界面布局的根节点，负责管理并响应用户的触摸事件。
    //DecorView 的父类是FrameLayout，FrameLayout没有这个方法的实现，所以再去找FrameLayout的父类ViewGroup(我这里是InterceptLinearLayout 继承的 LinearLayout)
    //Activity → PhoneWindow → DecorView → ViewGroup → View 事件就是沿着这条链路一层层往下分发的
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        String actionName = replaceAction(ev.getAction());
//        事件分发测试用
//        Log.d(TAG, "===== Activity 全局分发事件 =====");
//        Log.d(TAG, "事件类型：" + actionName);
//        Log.d(TAG, "绝对屏幕坐标：rawX=" + ev.getRawX() + ", rawY=" + ev.getRawY());
//        Log.d(TAG, "事件分发阶段：Activity.dispatchTouchEvent");
//        Log.d(TAG, "是否是多点触控：" + (ev.getPointerCount() > 1));
//        Log.d(TAG, "================================\n");

        //TODO 在这里可以拦截事件，然后在onTouchEvent方法中消费掉onTouchEvent（ev.getAction()）

        //  执行系统默认的分发逻辑（核心！必须调用super，否则页面所有触摸失效）
        // 返回值：系统默认分发的结果（true=事件被消费，false=未被消费）
        boolean defaultResult = super.dispatchTouchEvent(ev);

//        Log.d(TAG, "Activity.dispatchTouchEvent 分发结果：" + (defaultResult ? "事件已消费" : "事件未消费，向上回传"));

        return defaultResult;
    }


    // 拦截后，事件会进入ViewGroup的onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "MainActivity 消费拦截的事件：" + replaceAction(event.getAction()));
        return true; // 消费拦截的事件
    }


    /**
     * 绑定其他事件
     */
    private void bindOtherEvents() {
        // 1. 设置 OnTouchListener 监听所有触摸事件
        //view 的 事件分发流程可以参考 viewevent.png
        findViewById(R.id.btn_touch).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 获取事件类型
                int action = event.getAction();
                // 获取触摸坐标（相对View本身）
                float x = event.getX();
                float y = event.getY();
                // 获取绝对坐标（相对屏幕）
                float rawX = event.getRawX();
                float rawY = event.getRawY();

                // 解析事件类型并打印详细信息
                String actionName = replaceAction(action);

                // 打印完整的 MotionEvent 信息
                Log.d(TAG, "===== btn_touch 触摸事件 =====");
                Log.d(TAG, "事件类型：" + actionName);
                Log.d(TAG, "相对View坐标：x=" + x + ", y=" + y);
                Log.d(TAG, "绝对屏幕坐标：rawX=" + rawX + ", rawY=" + rawY);
                Log.d(TAG, "事件时间戳：" + event.getEventTime() + "ms");
                Log.d(TAG, "触摸点数量：" + event.getPointerCount());
                Log.d(TAG, "===========================\n");

                // 关键：返回值决定是否消费事件
                // false：不消费，事件会继续传递（比如触发 onClick）
                // true：消费事件，事件终止（onClick 不会触发）
                return false;
            }
        });

        // 2. 补充 OnClickListener（对比 OnTouchListener 优先级）
        findViewById(R.id.btn_touch).setOnClickListener(v -> {
            Log.d(TAG, "btn_touch onClick 触发（说明 OnTouch 未消费事件）");
        });
    }

    private void observerSend(){
        ObserverEX observerEX = new ObserverEX();
        observerEX.add(ObserverImpl1);
        observerEX.add(ObserverImpl2);
        observerEX.notifyObserver("信息");
        observerEX.delete(ObserverImpl1);
        observerEX.notifyObserver("问题");
        ObserverImpl2.setName("小王");
        observerEX.notifyObserver("改名消息");
    }

    private void contentSend(){
        ContentResolver contentResolver =getContentResolver();
        Uri uri = Uri.parse("content://com.human.lxczy/user");
        //插入
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",4);
        contentValues.put("name","ls");
        contentResolver.insert(uri,contentValues);
        Cursor cursor = contentResolver.query(uri, new String[]{"id", "name"}, null, null, null);
        while (cursor.moveToNext()) {
            Log.d(TAG,"insert query info:" + cursor.getInt(0) + " " + cursor.getString(1));
        }
        Log.d(TAG,"新增查询结束");
        //删除
        contentResolver.delete(uri,"id = ?", new String[]{"3"});
        Cursor cursor1 = contentResolver.query(uri, new String[]{"id", "name"}, null, null, null);
        while (cursor1.moveToNext()) {
            Log.d(TAG,"delete query info:" + cursor1.getInt(0) + " " + cursor1.getString(1));
        }
        Log.d(TAG,"删除查询结束");
        //更新
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("name","LXC");
        contentResolver.update(uri,contentValues1,"id = ?",new String[]{"1"});
        Cursor cursor2 = contentResolver.query(uri, new String[]{"id", "name"}, null, null, null);
        while (cursor2.moveToNext()) {
            Log.d(TAG,"update query info:" + cursor2.getInt(0) + " " + cursor2.getString(1));
        }
        Log.d(TAG,"更新查询结束");

    }

    private void sharedPreferencesSend(){
        sharedPreferencesImpl.saveData(this,"1","lxc","12345");
        sharedPreferencesImpl.saveData(this,"1","lxc","123456789");//这里直接覆盖上面的数据
        sharedPreferencesImpl.saveData(this,"2","lxc","12345");
        sharedPreferencesImpl.saveData(this,"3","lxc","12345");
        sharedPreferencesImpl.saveData(this,"4","lxc","12345");
        sharedPreferencesImpl.getData(this,"1");
        sharedPreferencesImpl.getData(this,"2");
        sharedPreferencesImpl.getData(this,"3");
        Log.d(TAG,"sharedPreferencesImpl :" + sharedPreferencesImpl.getMap());
    }

    private void aidlSend(){
        Intent intent = new Intent();
        intent.setAction("com.example.myapplication.MY_REMOTE_SERVICE");//需要与AndroidManifest文件中的对齐
        // 同一个应用内，需指定包名（Android 5.0+ 强制要求）
        intent.setPackage(getPackageName());//服务端包名
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "客户端连接aidl服务的进程id ：" + Process.myPid());
                Log.d(TAG,"aidl连接成功");
                mAidlInterface = IMyAidlInterface.Stub.asInterface(service);
                mIsServiceBound = true;
                try {
                    //连接之后，客户端与服务端做通讯
                    String result = mAidlInterface.getServiceMsg("今天天气很好");
                    Log.d(TAG,"result = " + result);
                    int ab = mAidlInterface.add(12,10);
                    Log.d(TAG,"ab = " + ab);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG,"aidl连接断开");
                mAidlInterface = null;
                mIsServiceBound = false;
            }
        }, BIND_AUTO_CREATE);
    }

    private void MessageSend(){
//        Message	存储需要传递的数据（支持 Bundle、基本类型等），是通信的数据载体；
//        Messenger	封装了 Binder 和 Handler，分为「服务端 Messenger」和「客户端 Messenger」，负责跨进程传递 Message；
//        Handler	处理接收到的跨进程 Message（服务端 / 客户端各自通过 Handler 处理消息）
        // 初始化客户端 Messenger（用于接收回复）
        mClientMessenger = new Messenger(new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.d(TAG, "收到回复信息：" + msg);
                switch (msg.what) {
                    case 2001: // 服务端回复标识
                        String serverReply = msg.getData().getString("server_reply");
                        Log.d(TAG, "回复信息 ：" + serverReply);
                        break;
                }
            }
        });

        // 绑定服务端 Service（跨应用）
        Intent intent = new Intent();
        intent.setAction("com.example.messengerserver.MESSENGER_SERVICE");//需要与AndroidManifest文件中的对齐
        // Android 5.0+ 必须指定服务端包名
        intent.setPackage(getPackageName());//服务端包名
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG,"Message服务连接成功");
                mServerMessenger = new Messenger(service);
                //TODO 发送信息给服务端
                Message msg = Message.obtain();
                msg.what = 1001;//信息标识，需要和服务器端对齐

                Bundle bundle = new Bundle();
                bundle.putString("client_msg", "今天天气怎么样");
                msg.setData(bundle);
                // 设置回复用的客户端 Messenger
                msg.replyTo = mClientMessenger;

                try {
                    // 跨应用发送 Message
                    mServerMessenger.send(msg);
                    Log.d(TAG, "已向服务端发送消息");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG,"Message断开服务连接");
                mServerMessenger = null;
            }
        }, BIND_AUTO_CREATE);
    }

    private String replaceAction(int action){
        String actionName;
        switch (action) {
            case MotionEvent.ACTION_DOWN :
                actionName = "ACTION_DOWN（按下）";
                break;
            case MotionEvent.ACTION_MOVE :
                actionName ="ACTION_MOVE（移动）";
                break;
            case MotionEvent.ACTION_UP :
                actionName = "ACTION_UP（抬起）";
                break;
            case MotionEvent.ACTION_CANCEL :
                actionName = "ACTION_CANCEL（事件取消）";
                break;
            case MotionEvent.ACTION_OUTSIDE :
                actionName = "ACTION_OUTSIDE（触摸到View外部）";
                break;
            default :
                actionName = "UNKNOWN";
                break;
        }
        return actionName;
    }
}