package com.example.android_study.AcitivityStudy;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_study.AcitivityStudy.bean.Frult;
import com.example.android_study.AcitivityStudy.bean.FrultAdapter;
import com.example.android_study.R;

import java.util.ArrayList;
import java.util.List;

public class ThridActivity extends AppCompatActivity {
    private String[] data = {"Android", "iOS", "Windows", "macOS", "Linux", "Ubuntu", "CentOS", "Debian", "Fedora", "Arch Linux"};

    private List<Frult> frultList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thrid);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        initFruits();
        ListView listView = findViewById(R.id.list_view);
        //ArrayAdapter 适配器
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        FrultAdapter adapter = new FrultAdapter(this, R.layout.frult_item, frultList);
        listView.setAdapter(adapter);

        //listView的点击事件
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Frult frult = frultList.get(position);
            Toast.makeText(ThridActivity.this, frult.getName(), Toast.LENGTH_SHORT).show();
        });
    }

    private void initFruits() {
        for (int i = 0; i < 2; i++) {
            Frult apple = new Frult("Apple", R.drawable.img);
            frultList.add(apple);
            Frult banana = new Frult("Banana", R.drawable.img_1);
            frultList.add(banana);
            Frult orange = new Frult("Orange", R.drawable.img_2);
            frultList.add(orange);
            Frult watermelon = new Frult("Watermelon", R.drawable.img_3);
            frultList.add(watermelon);
        }
    }
}