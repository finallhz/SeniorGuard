package com.snnu.Controller;

import com.alibaba.fastjson.JSON;
import com.snnu.POJO.Food;
import com.snnu.RunPython.RecommendFoods;
import com.snnu.Service.FoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
public class RecoFoodsController {
    @Autowired
    private FoodsService foodsService;

    @RequestMapping(value = "/getFoodsNum")
    @ResponseBody
    public List<Food> recoFoods(@RequestParam(required = false, defaultValue = "1,15,26,27") String L){
        System.out.println(L);
        int[] foodsNum = new RecommendFoods().foodsNum(L);
        if (foodsNum == null){
            return null;
        }
        List<Food> foods = foodsService.recoFoods(foodsNum);
        //String foodsJson = JSON.toJSONString(foods);
        //System.out.println("推荐菜品：" + foodsJson);
        return foods;
    }

    //获取全部菜品
    @RequestMapping(value = "/getAllFoods")
    @ResponseBody
    public List<Food> getAllFoods(){
        List<Food> allFoods = foodsService.getAllFoods();
        return allFoods;
    }

    //根据名称模糊查询
    @RequestMapping(value = "/getFoodsByTitle")
    @ResponseBody
    public List<Food> getFoodsByTitle(String title){
        return foodsService.getFoodsByTitle(title);
    }
}
