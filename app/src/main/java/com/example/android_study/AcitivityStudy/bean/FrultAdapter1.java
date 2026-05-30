package com.example.android_study.AcitivityStudy.bean;


import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_study.R;

import java.util.List;

public class FrultAdapter1 extends RecyclerView.Adapter<FrultAdapter1.ViewHolder> {

    private List<Frult> mFruitList;
    public FrultAdapter1(List<Frult> fruitList) {
        mFruitList = fruitList;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        View fruitView;
        ImageView fruitImage;
        TextView fruitName;
        public ViewHolder(View view) {
            super(view);
            fruitView = view;
            fruitImage = view.findViewById(R.id.fruit_image);
            fruitName = view.findViewById(R.id.fruit_name);
        }
    }

    @NonNull
    @Override
    //用于创建ViewHolder实例
    public FrultAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(), R.layout.frult_item1, null);
        final ViewHolder holder = new ViewHolder(view);
        holder.fruitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Frult frult = mFruitList.get(position);
                Toast.makeText(v.getContext(), frult.getName(), Toast.LENGTH_SHORT).show();
                Log.d("FrultAdapter1", "onClick: " + frult.getName());
            }
        });
        return holder;
    }

    //用于对RecyclerView中的子项数据进行赋值，会在每个子项被滚动到屏幕内执行
    @Override
    public void onBindViewHolder(@NonNull FrultAdapter1.ViewHolder viewHolder, int i) {
        Frult frult = mFruitList.get(i);
        Log.d("FrultAdapter1", "onBindViewHolder: " + frult.getName());
        viewHolder.fruitName.setText(frult.getName());
        viewHolder.fruitImage.setImageResource(frult.getImageId());
    }

    //返回数据项的数量
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
}
