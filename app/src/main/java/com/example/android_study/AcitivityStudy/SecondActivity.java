package com.example.android_study.AcitivityStudy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_study.R;

public class SecondActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ActivityCollector.addActivity(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button button = findViewById(R.id.button2_1);
        button.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        Log.d("SecondActivity", "onDestroy");
        ActivityCollector.removeActivity(this);
        super.onDestroy();
    }

    //另一种点击事件处理方式
    @Override
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.button2_1:
               Intent intent = new Intent();
               intent.setClass(SecondActivity.this, FirstActivity.class);
               startActivity(intent);
               break;
       }
    }
}