package com.snnu.POJO;

import java.util.Date;

public class FallData {
    private int id;
    private int uid;
    //摔倒人姓名
    private String name;
    //年龄
    private int age;
    //经纬度
    private double longitude;
    private double latitude;
    //具体位置
    private String location;
    private Date time;
    private int flag;

    public FallData() {
    }

    public FallData(int uid, Date time, String location, int flag) {
        this.uid = uid;
        this.time = time;
        this.location = location;
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Date getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "FallData{" +
                "id=" + id +
                ", uid=" + uid +
                ", time=" + time +
                ", location=" + location +
                ", flag=" + flag +
                '}';
    }
}
