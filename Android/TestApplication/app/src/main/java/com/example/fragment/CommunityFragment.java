package com.example.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.adapter.NewsAdapter;
import com.example.pojo.NewsBean;
import com.example.testapplication.NewsDetailActivity;
import com.example.testapplication.R;
import com.example.utils.NewsUtils;

import java.util.ArrayList;

//底部导航栏相关碎片
//社区导航栏相关碎片
public class CommunityFragment extends Fragment implements AdapterView.OnItemClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SHOW_TEXT = "text";
    ListView lv_news;
    private String mContentText;

    private  Context mContext;
    public CommunityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment CommunityFragment.
     */
    public static CommunityFragment newInstance(String param1) {
        CommunityFragment fragment = new CommunityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SHOW_TEXT, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContentText = getArguments().getString(ARG_SHOW_TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //这句代码是加载指定的View布局
        View rootView = inflater.inflate(R.layout.fragment_community, container, false);
        mContext = this.getActivity();
        int iii =0 ;
        for (;iii<10;iii++)
        addNewsToList();

        ArrayList<NewsBean> allNews = NewsUtils.getAllNews(mContext);
        //根据指定的引用布局加载
        lv_news = (ListView) rootView.findViewById(R.id.lv_news);
        NewsAdapter newsAdapter = new NewsAdapter(mContext, allNews);
        lv_news.setAdapter(newsAdapter);
        //4.设置listview条目的点击事件
        lv_news.setOnItemClickListener(this);

        return rootView;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        //需要获取条目上bean对象中url做跳转
        NewsBean bean = (NewsBean) parent.getItemAtPosition(position);

        int realposition = position%25;
        //跳转浏览器
        Intent intent = new Intent(getActivity(),NewsDetailActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        intent.putExtra("position",realposition);
        Log.i("position",realposition+"");
        startActivity(intent);

    }

    //添加新闻内容方法
    public void addNewsToList()
    {
        NewsUtils.addNews(mContext,R.string.news1,R.string.newsdes1,R.drawable.news1);
        NewsUtils.addNews(mContext,R.string.news2,R.string.newsdes2,R.drawable.news2);
        NewsUtils.addNews(mContext,R.string.news3,R.string.newsdes3,R.drawable.news3);
        NewsUtils.addNews(mContext,R.string.news4,R.string.newsdes4,R.drawable.news4);
        NewsUtils.addNews(mContext,R.string.news5,R.string.newsdes5,R.drawable.news5);
        NewsUtils.addNews(mContext,R.string.news6,R.string.newsdes6,R.drawable.news6);
        NewsUtils.addNews(mContext,R.string.news7,R.string.newsdes7,R.drawable.news7);
        NewsUtils.addNews(mContext,R.string.news8,R.string.newsdes8,R.drawable.news8);
        NewsUtils.addNews(mContext,R.string.news9,R.string.newsdes9,R.drawable.news9);
        NewsUtils.addNews(mContext,R.string.news10,R.string.newsdes10,R.drawable.news10);
        NewsUtils.addNews(mContext,R.string.news11,R.string.newsdes11,R.drawable.news11);
        NewsUtils.addNews(mContext,R.string.news12,R.string.newsdes12,R.drawable.news12);
        NewsUtils.addNews(mContext,R.string.news13,R.string.newsdes13,R.drawable.news13);
        NewsUtils.addNews(mContext,R.string.news14,R.string.newsdes14,R.drawable.news14);
        NewsUtils.addNews(mContext,R.string.news15,R.string.newsdes15,R.drawable.news15);
        NewsUtils.addNews(mContext,R.string.news16,R.string.newsdes16,R.drawable.news16);
        NewsUtils.addNews(mContext,R.string.news17,R.string.newsdes17,R.drawable.news17);
        NewsUtils.addNews(mContext,R.string.news18,R.string.newsdes18,R.drawable.news18);
        NewsUtils.addNews(mContext,R.string.news19,R.string.newsdes19,R.drawable.news19);
        NewsUtils.addNews(mContext,R.string.news20,R.string.newsdes20,R.drawable.news20);
        NewsUtils.addNews(mContext,R.string.news21,R.string.newsdes21,R.drawable.news21);
        NewsUtils.addNews(mContext,R.string.news22,R.string.newsdes22,R.drawable.news22);
        NewsUtils.addNews(mContext,R.string.news23,R.string.newsdes23,R.drawable.news23);
        NewsUtils.addNews(mContext,R.string.news24,R.string.newsdes24,R.drawable.news24);
        NewsUtils.addNews(mContext,R.string.news25,R.string.newsdes25,R.drawable.news25);


    }
}
