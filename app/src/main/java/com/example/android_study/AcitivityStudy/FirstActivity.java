package com.example.android_study.AcitivityStudy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_study.MainActivity;
import com.example.android_study.R;

public class FirstActivity extends AppCompatActivity {

    private static final String TAG = "FirstActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.first_layout); //为当前活动Activity 加载一个布局

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
            Intent intent = new Intent(FirstActivity.this, MainActivity.class);
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
            Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
            startActivity(intent);
        });
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
        switch (item.getItemId()) {
            case R.id.add_item:
                Toast.makeText(this, "You clicked Add", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this, "You clicked Remove", Toast.LENGTH_SHORT).show();
                break;
            default:
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