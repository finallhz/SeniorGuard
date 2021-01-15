package com.snnu.Dao;

import com.snnu.POJO.News;

import java.util.List;

public interface NewsDao {
    //添加文章
    int addNews(News news);
    //撤销文章
    int updateNewsStatus(int nid);
    //修改文章
    int updateNews(News news);
    //查询所有有效文章
    List<News> getAllNews();
}
