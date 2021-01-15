package com.snnu.POJO;

public class Food {
    private int fid;
    private String title;
    private String dept;
    private String mainM;
    private String subM;
    private String mixM;
    private String attention;
    private String cooking;

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getMainM() {
        return mainM;
    }

    public void setMainM(String mainM) {
        this.mainM = mainM;
    }

    public String getSubM() {
        return subM;
    }

    public void setSubM(String subM) {
        this.subM = subM;
    }

    public String getMixM() {
        return mixM;
    }

    public void setMixM(String mixM) {
        this.mixM = mixM;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public String getCooking() {
        return cooking;
    }

    public void setCooking(String cooking) {
        this.cooking = cooking;
    }

    @Override
    public String toString() {
        return "Food{" +
                "fid=" + fid +
                ", title='" + title + '\'' +
                ", dept='" + dept + '\'' +
                ", mainM='" + mainM + '\'' +
                ", subM='" + subM + '\'' +
                ", mixM='" + mixM + '\'' +
                ", attention='" + attention + '\'' +
                ", cooking='" + cooking + '\'' +
                '}';
    }
}
