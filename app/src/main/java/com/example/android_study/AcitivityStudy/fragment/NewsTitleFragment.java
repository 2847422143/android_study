package com.example.android_study.AcitivityStudy.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_study.AcitivityStudy.SixActivity;
import com.example.android_study.AcitivityStudy.bean.News;
import com.example.android_study.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewsTitleFragment extends Fragment {
    private boolean isTwoPane;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_title_frag, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.news_title_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new NewsAdapter(getNews()));
        return view;
    }

    private List<News> getNews() {
        List<News> newsList = new ArrayList<News>();
        for (int i = 1; i <= 20; i++) {
            News news = new News();
            news.setTitle("This is news title " + i);
            news.setContent(getRandomLengthName("This is news content " + i));
            newsList.add(news);
        }
        return newsList;
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
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.news_content_layout) != null) {
            isTwoPane = true; //新闻内容布局存在，则是双页模式
        } else {
            isTwoPane = false;//新闻内容布局不存在，则是单页模式
        }
    }

    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
        private List<News> newsTitleList;
        public NewsAdapter(List<News> newsTitleList) {
            this.newsTitleList = newsTitleList;
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("NewsTitleFragment", "click" + holder.getAdapterPosition());
                    Log.d("NewsTitleFragment", "isTwoPane = " + isTwoPane);
                    News news = newsTitleList.get(holder.getAdapterPosition());
                    if (isTwoPane) {
                        //如果是双页模式，则直接在当前Fragment显示新闻内容
                        NewsContentFragment newsContentFragment = (NewsContentFragment) getFragmentManager().findFragmentById(R.id.news_content_fragment);
                        newsContentFragment.refresh(news.getTitle(), news.getContent());
                    } else {
                        //如果是单页模式，则开启一个FragmentActivity来显示新闻内容
                        SixActivity.actionStart(getActivity(), news.getTitle(), news.getContent());
                    }
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            News news = newsTitleList.get(position);
            holder.newsTitleText.setText(news.getTitle());
        }

        @Override
        public int getItemCount() {
            return newsTitleList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView newsTitleText;

            public ViewHolder(View view) {
                super(view);
                newsTitleText = (TextView) view.findViewById(R.id.news_title);
            }
        }
    }
}
