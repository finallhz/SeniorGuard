package com.snnu.Service;

import com.snnu.Dao.FoodsDao;
import com.snnu.POJO.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodsService {
    @Autowired
    private FoodsDao foodsDao;

    public List<Food> recoFoods(int[] foodsNum){
        List<Food> foodsList = foodsDao.getFoodsByFid(foodsNum);
        return foodsList;
    }

    //获取全部菜品
    public List<Food> getAllFoods(){
        List<Food> allFoods = foodsDao.getAllFoods();
        return allFoods;
    }

    //根据名称模糊查询
    public List<Food> getFoodsByTitle(String title){
        return foodsDao.getFoodsByTitle(title);
    }
}
