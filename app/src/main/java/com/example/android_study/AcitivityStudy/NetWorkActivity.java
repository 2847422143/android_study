package com.example.android_study.AcitivityStudy;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_study.KotlinLearn.Person;
import com.example.android_study.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetWorkActivity extends AppCompatActivity implements View.OnClickListener {
    TextView mResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        //隐藏系统自带的标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        setContentView(R.layout.activity_net_work);
        Button sendButton = findViewById(R.id.send);
        Button okHttpButton = findViewById(R.id.ok_http);
        mResponse = findViewById(R.id.response);
        okHttpButton.setOnClickListener(this);
        sendButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.send) {
            Log.d("NetWorkActivity", "onClick: ");
            sendRequestWithHttpURLConnection();
        } else if (view.getId() == R.id.ok_http) {
            Log.d("NetWorkActivity", "onClick: ");
            sendRequestWithOkHttp();
        }
    }

    private void sendRequestWithHttpURLConnection() {
        //开启线程发送网络请求
        //因为网络请求不能在主线程中进行，否则会报错
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("https://www.baidu.com");
                    connection = (HttpURLConnection) url.openConnection();

                    //写入数据
                    connection.setRequestMethod("POST");
                    DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                    dataOutputStream.writeBytes("username=rain&password=123456");

                    //读出数据
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    //下面对获取到的输入流进行读取
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    sendResponse(response.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void sendResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mResponse.setText(response);
            }
        });
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://www.baidu.com")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    sendResponse(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //todo Gson的用法
//    {"name":"rain","imageId":1}
//    Gson gson  = new Gson();
//    Person person = gson.fromJson(json, Person.class);
//    [{"name":"rain","imageId":1}, {"name":"rain","imageId":1}]
//    List<Person> personList = gson.fromJson(json, new TypeToken<List<Person>>(){}.getType());
}