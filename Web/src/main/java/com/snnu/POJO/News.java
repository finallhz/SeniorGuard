package com.snnu.POJO;

import java.util.Date;

public class News {
    private int nid;
    private String title;
    private String pic;
    private String content;
    private String keywords;
    private String author;
    private String publishTime;
    private int clicks;
    private int publishStatus;

    public News() {
    }


    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public int getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(int publishStatus) {
        this.publishStatus = publishStatus;
    }

    @Override
    public String toString() {
        return "News{" +
                "nid=" + nid +
                ", title='" + title + '\'' +
                ", pic='" + pic + '\'' +
                ", content='" + content + '\'' +
                ", keywords='" + keywords + '\'' +
                ", author='" + author + '\'' +
                ", publishTime=" + publishTime +
                ", clicks=" + clicks +
                ", publishStatus=" + publishStatus +
                '}';
    }
}
