package com.snnu.Utils;

import java.util.Arrays;
import java.util.List;

/**
 * 数据监听的Util类
 */
public class DataUtil {
    //private int uid;
    private List<SportData> sportDatas;//存放acc,gyr合量数据
    private int status;//用户当前运动状态
    private int heart;//心跳数据
    private int stepCount;//步数

    public DataUtil() {
    }

    public DataUtil(List<SportData> sportDatas, int status, int heart, int stepCount) {
        this.sportDatas = sportDatas;
        this.status = status;
        this.heart = heart;
        this.stepCount = stepCount;
    }

    /*public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }*/

    public List<SportData> getSportDatas() {
        return sportDatas;
    }

    public void setSportDatas(List<SportData> sportDatas) {
        this.sportDatas = sportDatas;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    @Override
    public String toString() {
        return "DataUtil{" +
                "sportDatas=" + sportDatas +
                ", status=" + status +
                ", heart=" + heart +
                ", stepCount=" + stepCount +
                '}';
    }
}
