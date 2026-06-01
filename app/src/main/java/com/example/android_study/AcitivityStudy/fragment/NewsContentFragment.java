package com.example.android_study.AcitivityStudy.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.android_study.R;

public class NewsContentFragment extends Fragment {

    private View view;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news_content_frag, container, false);
        return view;
    }

    public void refresh(String newsTitle, String newsContent) {
        View visibilityLayout = view.findViewById(R.id.visibility_layout);
        visibilityLayout.setVisibility(View.VISIBLE);

        TextView newsTitleView = (TextView) view.findViewById(R.id.news_title);
        TextView newsContentView = (TextView) view.findViewById(R.id.news_content);
        newsTitleView.setText(newsTitle);//刷新新闻标题
        newsContentView.setText(newsContent);//刷新新闻内容
    }
}
