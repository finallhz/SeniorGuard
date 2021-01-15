package com.example.testapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adapter.FoodAdapter;
import com.example.pojo.FoodBean;
import com.example.utils.FoodUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class FoodRecommendResult extends AppCompatActivity {
    private Context mContext;
    ListView lv_food;
    ArrayList list;
    FoodAdapter foodAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodresult);
        //首先获得Intent传输过来的信息
        Intent it = this.getIntent();
        String infor = it.getStringExtra("infor");//获得数据信息
        StringBuffer sb = new StringBuffer(infor);
        sb =  sb.deleteCharAt(0);
        sb  = sb.deleteCharAt(sb.length()-1);
        String rem = sb.toString().replaceAll(" ", "");
        list=new ArrayList(Arrays.asList(rem.split(",")));//存储在ArrayList之中
        System.out.println("推荐结果："+list.toString());
        mContext = FoodRecommendResult.this;
        //根据指定的引用布局加载
        ArrayList<FoodBean> allContact = FoodUtils.getAllFoodResult(mContext);
        lv_food = (ListView) findViewById(R.id.lv_food);
        foodAdapter = new FoodAdapter(mContext, allContact);
        lv_food.setAdapter(foodAdapter);
        addFoodToLv();
    }
    public void addFoodToLv()
    {
        FoodUtils.removeAllFoodResult();
        for(Object i:list)
        {
            String id = (String)i;
            int name = getApplicationContext().getResources().getIdentifier("food"+id,"string",getApplicationContext().getPackageName());
            int attr1 = getApplicationContext().getResources().getIdentifier("food"+id+"_1","string",getApplicationContext().getPackageName());
            int attr2 = getApplicationContext().getResources().getIdentifier("food"+id+"_2","string",getApplicationContext().getPackageName());
            int attr3 = getApplicationContext().getResources().getIdentifier("food"+id+"_3","string",getApplicationContext().getPackageName());
            int attr4 = getApplicationContext().getResources().getIdentifier("food"+id+"_4","string",getApplicationContext().getPackageName());
            int attr5 = getApplicationContext().getResources().getIdentifier("food"+id+"_5","string",getApplicationContext().getPackageName());
            int pic = getApplicationContext().getResources().getIdentifier("food"+id,"drawable",getApplicationContext().getPackageName());
            FoodUtils.addFoodResult(mContext,Integer.parseInt(id),name,"徽菜",pic,
                    attr1,attr2,attr3,attr4,attr5);
            System.out.println("推荐菜单："+getApplicationContext().getResources().getString(name));
        }
        foodAdapter.notifyDataSetChanged();
    }
}
