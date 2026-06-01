package com.example.android_study.AcitivityStudy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_study.AcitivityStudy.fragment.NewsContentFragment;
import com.example.android_study.R;

//单页模式下展示内容
public class SixActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_six);

        String newsTitle = getIntent().getStringExtra("news_title");
        String newsContent = getIntent().getStringExtra("news_content");
        NewsContentFragment newsContentFragment = (NewsContentFragment) getSupportFragmentManager().findFragmentById(R.id.news_content_fragment);
        newsContentFragment.refresh(newsTitle, newsContent);
    }

    public static  void actionStart(Context context,String newsTitle,String newsContent) {
        Intent intent = new Intent(context, SixActivity.class);
        intent.putExtra("news_title", newsTitle);
        intent.putExtra("news_content", newsContent);
        context.startActivity(intent);
    }
}