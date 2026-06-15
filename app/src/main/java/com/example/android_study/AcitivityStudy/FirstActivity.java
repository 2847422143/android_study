package com.example.android_study.AcitivityStudy;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.android_study.MainActivity;
import com.example.android_study.R;
import com.example.android_study.SQLite.OneActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FirstActivity extends AppCompatActivity {

    private static final String TAG = "FirstActivity";

    ArrayAdapter<String> adapter;

    List<String> contactsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.first_layout); //为当前活动Activity 加载一个布局

        //隐藏系统自带的标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        ActivityCollector.addActivity(this);
        if(savedInstanceState != null ){
            //回收保存的数据，重新获取
            savedInstanceState.getString("aaa");
            Log.d(TAG, savedInstanceState.getString("aaa"));
        }

        Button button1 = findViewById(R.id.button1); //这里的findViewById 返回的是一个View ，这里做了向下转型成Button对象
        button1.setOnClickListener(v -> {
            Toast.makeText(FirstActivity.this, "You clicked me!", Toast.LENGTH_SHORT).show();
//            finish();//销毁当前活动  效果和按下back按键是一样的

            //显性Intent
            Intent intent = new Intent(FirstActivity.this, NetWorkActivity.class);
            intent.putExtra("date","hello");
//            startActivity(intent); // 专门用于启动活动
            startActivityForResult(intent, 1); //期望启动的活动销毁后返回数据给这个活动
//            优点
//            最快、最直接、最安全
//            明确知道要跳转到哪个页面
//            系统不用搜索，性能高
//            不会跳错页面
//            项目内部跳转 90% 都用它
//            缺点
//            耦合度高（必须知道目标 Activity 类名）
//            不能跨 APP 灵活跳转

            //隐式Intent
//            Intent intent1 = new Intent("com.example.myapplication.MY_ACTION"); //需要和androidMainxml 文件中参数对应
//            intent1.addCategory("com.example.myapplication.MY_CATEGORY"); //需要和androidMainxml 文件中参数对应 除了设置DEFAULT会自己添加 ，其他都需要手动添加
//            startActivity(intent1);
//            优点
//            解耦（不写死类名）
//            可以跨 APP 跳转（打电话、发短信、打开浏览器、拍照）
//            灵活、可扩展
//            可以让别的 APP 响应你的 Intent
//            缺点
//            慢（系统要匹配匹配过滤器）
//            不安全（可能被别的应用劫持）
//            容易写错 action/category 导致崩溃
//            代码复杂、麻烦

//            Intent intent3 = new Intent(Intent.ACTION_VIEW);
//            intent3.setData(Uri.parse("https://www.baidu.com"));
//            startActivity(intent3);
        });


        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(v -> {
            //提示对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(FirstActivity.this);
            builder.setTitle("This is a Dialog");//标题
            builder.setMessage("Something Important");//内容
            builder.setPositiveButton("OK", (dialog, which) -> {
                Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                startActivity(intent);
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            builder.show();
        });
        ProgressBar progressBar = findViewById(R.id.progress_bar);

        ProgressBar progressBar2 = findViewById(R.id.progress_bar2);

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(v -> {
            if(progressBar.getVisibility() == View.GONE)
            progressBar.setVisibility(View.VISIBLE);
            else
            progressBar.setVisibility(View.GONE);

            int progress = progressBar2.getProgress();
            progress += 10;
            progressBar2.setProgress(progress);
        });


        Button button4 = findViewById(R.id.make_call);
        button4.setOnClickListener(v -> {
            if(ContextCompat.checkSelfPermission(FirstActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                //没有授权
                ActivityCompat.requestPermissions(FirstActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            } else {
                call();
            }

        });

        Button button5 = findViewById(R.id.send_notice);
        button5.setOnClickListener(v -> {
            Log.d(TAG, "send_notice");
            // Android 13 (API 33) 及以上需要动态申请 POST_NOTIFICATIONS 运行时权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(FirstActivity.this, Manifest.permission.POST_NOTIFICATIONS)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(FirstActivity.this,
                            new String[]{Manifest.permission.POST_NOTIFICATIONS}, 3);
                    return; // 等待用户授权后再发送
                }
            }
            sendNotification();
        });

        Button button6 = findViewById(R.id.photo);
        button6.setOnClickListener(v -> {
            Intent intent = new Intent(FirstActivity.this, nineActivity.class);
            startActivity(intent);
        });
        ListView contarc = findViewById(R.id.contacts_view);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactsList);
        contarc.setAdapter(adapter);
        if(ContextCompat.checkSelfPermission(FirstActivity.this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(FirstActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 2);
        } else {
            readContacts();
        }

    }

    private void readContacts(){
        Cursor cursor = null;
         try {
             cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
             if(cursor != null ){
                 while (cursor.moveToNext()) {
                     @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                     @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                     contactsList.add(name + " : " + number);
                 }
                 adapter.notifyDataSetChanged();
             }
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             if (cursor != null) {
                 cursor.close();
             }
         }
        adapter.addAll(contactsList);
    }

    private void call (){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:123456789"));
            startActivity(intent);
        } catch (SecurityException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
                case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            case 3:
                // POST_NOTIFICATIONS 权限回调（Android 13+）
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendNotification();
                } else {
                    Toast.makeText(this, "通知权限被拒绝，无法发送通知", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void sendNotification() {
        Intent intent = new Intent(FirstActivity.this, MainActivity.class);
        // Android 12 (API 31) 及以上必须显式指定 FLAG_IMMUTABLE 或 FLAG_MUTABLE
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Android 8.0 (API 26) 及以上必须创建 NotificationChannel，否则通知无法显示
        String channelId = "default_channel";
        NotificationChannel channel = new NotificationChannel(
                channelId,
                "默认通知渠道",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        channel.setDescription("用于显示普通通知");
        notificationManager.createNotificationChannel(channel);

        Notification notification = new Notification.Builder(this, channelId)
                .setContentTitle("This is content title")  // 通知标题
                //setContentText方法一般只适用于短文本
                .setContentText("This is content text,aaaaasdaddsadsadsadsadsadsadwdqdqw wjbuduihdjndjasbdhsa dsjkand sajkdnsabdwuiqhd dklsandjsabduiqwbdoind ja")    // 通知正文
                //如果想显示长文本就可以用setStyle()
//                .setStyle(new Notification.BigTextStyle().bigText("This is content text,aaaaasdaddsadsadsadsadsadsadwdqdqw wjbuduihdjndjasbdhsa dsjkand sajkdnsabdwuiqhd dklsandjsabduiqwbdoind ja"))
                .setWhen(System.currentTimeMillis())        // 通知创建时间
                .setSmallIcon(R.drawable.img)               // 小图标（需PNG，不能用XML自适应图标）
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.img)) // 大图标
                .setContentIntent(pendingIntent)            // 点击通知后跳转的Activity
                .setAutoCancel(true)                        // 点击后自动消除通知

                // setPriority() 用于设置通知的优先级，影响通知在通知栏中的显示方式和排序位置
                // 可选参数（仅适用于 Android 7.1 及以下，Android 8.0+ 由 NotificationChannel 的 IMPORTANCE 控制）：
                // Notification.PRIORITY_MIN  (-2)  最低优先级，通知栏中不显眼，用户可能看不到
                // Notification.PRIORITY_LOW  (-1)  低优先级，显示较小的图标
                // Notification.PRIORITY_DEFAULT (0)  默认优先级
                // Notification.PRIORITY_HIGH  (1)  高优先级，更醒目，可能弹出提醒
                // Notification.PRIORITY_MAX   (2)  最高优先级，以 heads-up 悬浮通知形式弹出
                .setPriority(Notification.PRIORITY_MAX)


//        也可以在打开的Activity中关闭
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.cancel(1)，这里的1和下面的id一直
                .build();
        notificationManager.notify(1, notification);
    }

    //重写onCreateOptionsMenu 去关联自己的菜单控件
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        //通过getMenuInflater 方法获取一个MenuInflater对象，然后调用它的inflate方法给menu对象添加条目
        return true; //这里必须要返回true 才能显示出来，返回false不会显示出来
    }

    //重写onOptionsItemSelected 处理菜单点击事件
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_item) {
            Toast.makeText(this, "You clicked Add", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.remove_item) {
            Toast.makeText(this, "You clicked Remove", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    //重写onActivityResult 处理结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        requestCode :启动活动时候的请求码
//        resultCode :目标活动销毁后返回的状态码
//        data :目标活动销毁后返回的Intent
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String returnedData = data.getStringExtra("data_return");
                    Toast.makeText(this, returnedData, Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        ActivityCollector.removeActivity(this);
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart");
        super.onRestart();
    }

    //被回收前调用，用来保存数据
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("aaa","bbb");
        //保存的数据在onCreate的时候取出来
    }
}