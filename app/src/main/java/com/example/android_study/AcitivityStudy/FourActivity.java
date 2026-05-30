package com.example.android_study.AcitivityStudy;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.android_study.AcitivityStudy.bean.Frult;
import com.example.android_study.AcitivityStudy.bean.FrultAdapter1;
import com.example.android_study.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FourActivity extends AppCompatActivity {

    private List<Frult> mFruitList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_four);
        //隐藏系统自带的标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initFruits();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        //指定recyclerView的布局方式
        // LinearLayoutManager表示线性布局
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        recyclerView.setLayoutManager(linearLayoutManager);

        //GirdLayoutManager表示网格布局
        //StaggeredGridLayoutManager表示瀑布流布局,第一个参数表示列数,第二个参数表示布局方向
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        recyclerView.setAdapter(new FrultAdapter1(mFruitList));
    }
    private void initFruits() {
        for (int i = 0; i < 2; i++) {
            Frult apple = new Frult(getRandomLengthName("Apple"), R.drawable.img);
            mFruitList.add(apple);
            Frult banana = new Frult(getRandomLengthName("Banana"), R.drawable.img_1);
            mFruitList.add(banana);
            Frult orange = new Frult(getRandomLengthName("Orange"), R.drawable.img_2);
            mFruitList.add(orange);
            Frult watermelon = new Frult(getRandomLengthName("Watermelon"), R.drawable.img_3);
            mFruitList.add(watermelon);
            Frult grape = new Frult(getRandomLengthName("Grape"), R.drawable.img_4);
            mFruitList.add(grape);
            Frult pear = new Frult(getRandomLengthName("Pear"), R.drawable.img_1);
            mFruitList.add(pear);
        }
    }

    private String getRandomLengthName(String name) {
        Random random = new Random();
        int length = random.nextInt(20) + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(name);

        }
        return builder.toString();
    }
}