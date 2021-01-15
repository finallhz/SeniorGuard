package com.example.utils;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.example.pojo.NewsBean;
import com.example.testapplication.R;

//社区导航栏相关，新闻工具类
public class NewsUtils {

    public static ArrayList<NewsBean> arrayList = new ArrayList<NewsBean>();
    //封装新闻的假数据到list中返回,以后数据会从数据库中获取
    public static ArrayList<NewsBean> getAllNews(Context context) {

        return arrayList;
    }

    public static void addNews(Context context,int title, int des, int drawableid)
    {
        NewsBean newsBean = new NewsBean();
        newsBean.title =context.getResources().getString(title);
        newsBean.des= context.getResources().getString(des);
        newsBean.news_url= context.getResources().getString(R.string.url);
        newsBean.icon = ContextCompat.getDrawable(context, drawableid); //通过context对象将一个资源id转换成一个Drawable对象。
        arrayList.add(newsBean);
    }
    public static void multiNews()
    {

    }
}