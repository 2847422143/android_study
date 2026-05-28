package com.example.android_study.AcitivityStudy.bean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.android_study.R;

import java.util.List;

public class FrultAdapter extends ArrayAdapter<Frult> {
    private int resourceId;

    public FrultAdapter(@NonNull Context context, int textViewResourceId, List<Frult> objects) {
        super(context, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //todo ListView性能优化

        //每次滚动都需要把布局重新加载一遍，导致性能大大降低
        //convertView 用于将之前加载好的布局进行缓存 ，以便之后可以重用
        //如果缓存中没有该布局，则重新加载

        //解决了重复加载布局的问题，但是还有一个问题，就是每次都要重新获取控件实例，比较麻烦
        //为了解决这个问题，Android 引入了 ViewHolder 模式
        //通过自定义的 ViewHolder 类来缓存控件实例，避免每次滚动时都重新获取控件实例


        Frult frult = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null ){
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.fruitImage = view.findViewById(R.id.fruit_image);
            viewHolder.fruitName = view.findViewById(R.id.fruit_name);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        ImageView fruitImage = viewHolder.fruitImage;
        fruitImage.setImageResource(frult.getImageId());
        TextView fruitName = viewHolder.fruitName   ;
        fruitName.setText(frult.getName());
        return view;
    }

    class ViewHolder{
        ImageView fruitImage;
        TextView fruitName;
    }
}
