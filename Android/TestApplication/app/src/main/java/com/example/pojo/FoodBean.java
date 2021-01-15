package com.example.pojo;

import android.graphics.drawable.Drawable;

//食物的属性
public class FoodBean {
    public int type1;//checkbox的状态
    public int id;//食物的id
    public String name;//食物的名称
    public String type;//食物的标签属性，默认是0即属于任何类别
    public Drawable icon;//食物的图片
    public String first;//主料属性
    public String second;//副料属性
    public String third;//调料属性
    public String fourth;//做法属性
    public String fifth;//注意事项属性

}
