package com.snnu.Controller;

import com.snnu.POJO.News;
import com.snnu.Service.NewsService;
import com.snnu.Utils.RandomUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class NewsController {
    @Autowired
    private NewsService newsService;
    private String folderPath = System.getProperty("webapp.root") + "pic"+ File.separator;
    private String picPath;

    //添加文章 状态1为发布，状态2为撤销
    @RequestMapping("/addNews")
    @ResponseBody
    public int addNews(News news, MultipartFile picFile){
        int result = 0;
        //文件保存
        try {
            if (picFile != null){
                String picName = RandomUUID.getRandomUUID();
                String oriName = picFile.getOriginalFilename();
                String extName = oriName.substring(oriName.lastIndexOf("."));
                picPath = folderPath + picName + extName;
                System.out.println(picPath);
                picFile.transferTo(new File(picPath));
                news.setPic("../pic/" + picName + extName);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                news.setPublishTime(sdf.format(new Date()));
                news.setPublishStatus(1);
                System.out.println(news.toString());
                result = newsService.addNews(news);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    //撤销文章
    @RequestMapping(value = "/deleteNews")
    @ResponseBody
    public int updateNewsStatus(int nid){
        int result = newsService.updateNewsStatus(nid);
        return result;
    }
    //修改文章
    @RequestMapping(value = "/updateNews")
    @ResponseBody
    public int updateNews(News news){
        int result = newsService.updateNews(news);
        return result;
    }

    //查看所有文章
    @RequestMapping(value = "/getAllNews")
    @ResponseBody
    public List<News> getAllNews(){
        List<News> allNews = newsService.getAllNews();
        return allNews;
    }
}
