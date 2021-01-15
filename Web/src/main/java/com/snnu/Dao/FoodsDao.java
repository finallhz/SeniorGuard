package com.snnu.Dao;

import com.snnu.POJO.Food;

import java.util.List;

public interface FoodsDao {
    //根据fid int[]获取foods
    List<Food> getFoodsByFid(int[] foodsNum);
    //获取全部菜品
    List<Food> getAllFoods();
    //根据名称模糊查询
    List<Food> getFoodsByTitle(String title);
}
