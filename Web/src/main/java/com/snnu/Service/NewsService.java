package com.snnu.Service;

import com.snnu.Dao.NewsDao;
import com.snnu.POJO.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    @Autowired
    private NewsDao newsDao;
    //添加文章
    public int addNews(News news){
        int result = newsDao.addNews(news);
        return result;
    }

    //撤销文章
    public int updateNewsStatus(int nid){
        int result = newsDao.updateNewsStatus(nid);
        return result;
    }
    //修改文章
    public int updateNews(News news){
        int result = newsDao.updateNews(news);
        return result;
    }
    //查询所有有效文章
    public List<News> getAllNews(){
        List<News> allNews = newsDao.getAllNews();
        return allNews;
    }
}
