package com.example.testapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adapter.ContactAdapter;
import com.example.adapter.FoodAdapter;
import com.example.pojo.ContactBean;
import com.example.pojo.FoodBean;
import com.example.utils.ContactUtils;
import com.example.utils.FoodUtils;
import com.example.utils.UserDataManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;


public class FoodRecommendActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SHOW_TEXT = "text";
    public static HashSet<Integer> checkList;
    ListView lv_food;
    private String mContentText;
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;
    private Button b6;
    private Button b7;
    private Button b8;
    private Button b9;
    private Button b10;
    private TextView start;
    private Context mContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        checkList = new HashSet<Integer>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_foodrec);
        mContext = FoodRecommendActivity.this;
        //addNewsToList();
        addNewsToList1();
        ArrayList<FoodBean> allContact = FoodUtils.getAllFood(mContext);
        //根据指定的引用布局加载
        lv_food = (ListView) findViewById(R.id.lv_food);
        FoodAdapter foodAdapter = new FoodAdapter(mContext, allContact);
        lv_food.setAdapter(foodAdapter);
        //4.设置listview条目的点击事件
        lv_food.setOnItemClickListener(this);

        b1 = (Button) findViewById(R.id.bt1);
        b2 = (Button) findViewById(R.id.bt2);
        b3 = (Button) findViewById(R.id.bt3);
        b4 = (Button) findViewById(R.id.bt4);
        b5 = (Button) findViewById(R.id.bt5);
        b6 = (Button) findViewById(R.id.bt6);
        b7 = (Button) findViewById(R.id.bt7);
        b8 = (Button) findViewById(R.id.bt8);
        b9 = (Button) findViewById(R.id.bt9);
        b10 = (Button) findViewById(R.id.bt10);
        start = (TextView) findViewById(R.id.tv1);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得选中的Checkbox情况。然后跳转到对应的推荐展示页面
                Log.i("list",checkList.toString());
                String listInfor = checkList.toString();
                StringBuffer sb = new StringBuffer(listInfor);
                sb =  sb.deleteCharAt(0);
                sb  = sb.deleteCharAt(sb.length()-1);
                Log.i("list","处理完的结果："+sb);
                String res = UserDataManager.toServerList(sb.toString());
                ArrayList<String> food = new ArrayList<String>();
                //然后解析res信息
                try {
                    JSONArray i = new JSONArray(res);
                    for(int ii=0;ii<i.length();ii++) {
                        System.out.println(i.getJSONObject(ii).get("fid"));//获得菜品的id
                        food.add(i.getJSONObject(ii).get("fid").toString());//加入后面的序列中
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent fr = new Intent(FoodRecommendActivity.this,FoodRecommendResult.class);
                fr.putExtra("infor",food.toString());
                startActivity(fr);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewsToList1();
                foodAdapter.notifyDataSetChanged();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewsToList2();
                foodAdapter.notifyDataSetChanged();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewsToList3();
                foodAdapter.notifyDataSetChanged();
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewsToList4();
                foodAdapter.notifyDataSetChanged();
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewsToList5();
                foodAdapter.notifyDataSetChanged();
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewsToList6();
                foodAdapter.notifyDataSetChanged();
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewsToList7();
                foodAdapter.notifyDataSetChanged();
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewsToList8();
                foodAdapter.notifyDataSetChanged();
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewsToList9();
                foodAdapter.notifyDataSetChanged();
            }
        });
        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewsToList10();
                foodAdapter.notifyDataSetChanged();
            }
        });

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {



    }



    //全部菜品标签
    public void addNewsToList1()
    {
        FoodUtils.removeAllFood();
        int i= 1;
        //所有菜品
        for(; i<161; i++)
        {
            int name = getApplicationContext().getResources().getIdentifier("food"+(i),"string",getApplicationContext().getPackageName());
            int attr1 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_1","string",getApplicationContext().getPackageName());
            int attr2 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_2","string",getApplicationContext().getPackageName());
            int attr3 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_3","string",getApplicationContext().getPackageName());
            int attr4 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_4","string",getApplicationContext().getPackageName());
            int attr5 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_5","string",getApplicationContext().getPackageName());
            int pic = getApplicationContext().getResources().getIdentifier("food"+(i),"drawable",getApplicationContext().getPackageName());
            FoodUtils.addFood(mContext,i,name,"徽菜",pic,
                    attr1,attr2,attr3,attr4,attr5);
        }


    }

    //粤菜标签
    public void addNewsToList2()
    {
        FoodUtils.removeAllFood();
        int i = 1;
        for(; i<=10; i++)
        {
            int name = getApplicationContext().getResources().getIdentifier("food"+(i),"string",getApplicationContext().getPackageName());
            int attr1 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_1","string",getApplicationContext().getPackageName());
            int attr2 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_2","string",getApplicationContext().getPackageName());
            int attr3 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_3","string",getApplicationContext().getPackageName());
            int attr4 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_4","string",getApplicationContext().getPackageName());
            int attr5 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_5","string",getApplicationContext().getPackageName());
            int pic = getApplicationContext().getResources().getIdentifier("food"+(i),"drawable",getApplicationContext().getPackageName());
            FoodUtils.addFood(mContext,i,name,"粤菜",pic,
                    attr1,attr2,attr3,attr4,attr5);
        }
    }

    //鲁菜标签
    public void addNewsToList3()
    {
        FoodUtils.removeAllFood();
        int i = 11;
        for(; i<=20; i++)
        {
            int name = getApplicationContext().getResources().getIdentifier("food"+(i),"string",getApplicationContext().getPackageName());
            int attr1 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_1","string",getApplicationContext().getPackageName());
            int attr2 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_2","string",getApplicationContext().getPackageName());
            int attr3 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_3","string",getApplicationContext().getPackageName());
            int attr4 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_4","string",getApplicationContext().getPackageName());
            int attr5 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_5","string",getApplicationContext().getPackageName());
            int pic = getApplicationContext().getResources().getIdentifier("food"+(i),"drawable",getApplicationContext().getPackageName());
            FoodUtils.addFood(mContext,i,name,"鲁菜",pic,
                    attr1,attr2,attr3,attr4,attr5);
        }
    }

    //川菜标签
    public void addNewsToList4()
    {
        FoodUtils.removeAllFood();
        int i = 21;
        for(; i<=30; i++)
        {
            int name = getApplicationContext().getResources().getIdentifier("food"+(i),"string",getApplicationContext().getPackageName());
            int attr1 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_1","string",getApplicationContext().getPackageName());
            int attr2 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_2","string",getApplicationContext().getPackageName());
            int attr3 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_3","string",getApplicationContext().getPackageName());
            int attr4 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_4","string",getApplicationContext().getPackageName());
            int attr5 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_5","string",getApplicationContext().getPackageName());
            int pic = getApplicationContext().getResources().getIdentifier("food"+(i),"drawable",getApplicationContext().getPackageName());
            FoodUtils.addFood(mContext,i,name,"川菜",pic,
                    attr1,attr2,attr3,attr4,attr5);
        }

    }

    //湘菜标签
    public void addNewsToList5()
    {
        FoodUtils.removeAllFood();
        int i =31;
        for(; i<=40; i++)
        {
            int name = getApplicationContext().getResources().getIdentifier("food"+(i),"string",getApplicationContext().getPackageName());
            int attr1 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_1","string",getApplicationContext().getPackageName());
            int attr2 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_2","string",getApplicationContext().getPackageName());
            int attr3 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_3","string",getApplicationContext().getPackageName());
            int attr4 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_4","string",getApplicationContext().getPackageName());
            int attr5 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_5","string",getApplicationContext().getPackageName());
            int pic = getApplicationContext().getResources().getIdentifier("food"+(i),"drawable",getApplicationContext().getPackageName());
            FoodUtils.addFood(mContext,i,name,"湘菜",pic,
                    attr1,attr2,attr3,attr4,attr5);
        }
    }

    //闽菜标签
    public void addNewsToList6()
    {
        FoodUtils.removeAllFood();
        int i = 41;
        for(; i<=50; i++)
        {
            int name = getApplicationContext().getResources().getIdentifier("food"+(i),"string",getApplicationContext().getPackageName());
            int attr1 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_1","string",getApplicationContext().getPackageName());
            int attr2 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_2","string",getApplicationContext().getPackageName());
            int attr3 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_3","string",getApplicationContext().getPackageName());
            int attr4 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_4","string",getApplicationContext().getPackageName());
            int attr5 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_5","string",getApplicationContext().getPackageName());
            int pic = getApplicationContext().getResources().getIdentifier("food"+(i),"drawable",getApplicationContext().getPackageName());
            FoodUtils.addFood(mContext,i,name,"闽菜",pic,
                    attr1,attr2,attr3,attr4,attr5);
        }
    }

    //浙菜标签
    public void addNewsToList7()
    {
        FoodUtils.removeAllFood();
        int i = 84;
        for(; i<=89; i++)
        {
            int name = getApplicationContext().getResources().getIdentifier("food"+(i),"string",getApplicationContext().getPackageName());
            int attr1 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_1","string",getApplicationContext().getPackageName());
            int attr2 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_2","string",getApplicationContext().getPackageName());
            int attr3 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_3","string",getApplicationContext().getPackageName());
            int attr4 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_4","string",getApplicationContext().getPackageName());
            int attr5 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_5","string",getApplicationContext().getPackageName());
            int pic = getApplicationContext().getResources().getIdentifier("food"+(i),"drawable",getApplicationContext().getPackageName());
            FoodUtils.addFood(mContext,i,name,"闽菜",pic,
                    attr1,attr2,attr3,attr4,attr5);
        }
        i =100;
        for(; i<=160; i++)
        {
            int name = getApplicationContext().getResources().getIdentifier("food"+(i),"string",getApplicationContext().getPackageName());
            int attr1 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_1","string",getApplicationContext().getPackageName());
            int attr2 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_2","string",getApplicationContext().getPackageName());
            int attr3 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_3","string",getApplicationContext().getPackageName());
            int attr4 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_4","string",getApplicationContext().getPackageName());
            int attr5 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_5","string",getApplicationContext().getPackageName());
            int pic = getApplicationContext().getResources().getIdentifier("food"+(i),"drawable",getApplicationContext().getPackageName());
            FoodUtils.addFood(mContext,i,name,"闽菜",pic,
                    attr1,attr2,attr3,attr4,attr5);
        }

    }

    //苏菜标签
    public void addNewsToList8()
    {
        FoodUtils.removeAllFood();
        //苏菜
        FoodUtils.addFood(mContext,51,R.string.food51,"苏菜",R.drawable.food51,
                R.string.food51_1,R.string.food51_2,R.string.food51_3,R.string.food51_4,R.string.food51_5);
        FoodUtils.addFood(mContext,59,R.string.food59,"苏菜",R.drawable.food59,
                R.string.food59_1,R.string.food59_2,R.string.food59_3,R.string.food59_4,R.string.food59_5);
        FoodUtils.addFood(mContext,62,R.string.food62,"苏菜",R.drawable.food62,
                R.string.food62_1,R.string.food62_2,R.string.food62_3,R.string.food62_4,R.string.food62_5);
        FoodUtils.addFood(mContext,63,R.string.food63,"苏菜",R.drawable.food63,
                R.string.food63_1,R.string.food63_2,R.string.food63_3,R.string.food63_4,R.string.food63_5);
        FoodUtils.addFood(mContext,64,R.string.food64,"苏菜",R.drawable.food64,
                R.string.food64_1,R.string.food64_2,R.string.food64_3,R.string.food64_4,R.string.food64_5);
        FoodUtils.addFood(mContext,65,R.string.food65,"苏菜",R.drawable.food65,
                R.string.food65_1,R.string.food65_2,R.string.food65_3,R.string.food65_4,R.string.food65_5);
        FoodUtils.addFood(mContext,66,R.string.food66,"苏菜",R.drawable.food66,
                R.string.food66_1,R.string.food66_2,R.string.food66_3,R.string.food66_4,R.string.food66_5);
        FoodUtils.addFood(mContext,67,R.string.food67,"苏菜",R.drawable.food67,
                R.string.food67_1,R.string.food67_2,R.string.food67_3,R.string.food67_4,R.string.food67_5);
        FoodUtils.addFood(mContext,68,R.string.food68,"苏菜",R.drawable.food68,
                R.string.food68_1,R.string.food68_2,R.string.food68_3,R.string.food68_4,R.string.food68_5);
        FoodUtils.addFood(mContext,69,R.string.food69,"苏菜",R.drawable.food69,
                R.string.food69_1,R.string.food69_2,R.string.food69_3,R.string.food69_4,R.string.food69_5);
        FoodUtils.addFood(mContext,70,R.string.food70,"苏菜",R.drawable.food70,
                R.string.food70_1,R.string.food70_2,R.string.food70_3,R.string.food70_4,R.string.food70_5);

    }

    //徽菜标签
    public void addNewsToList9()
    {
        FoodUtils.removeAllFood();
        int i = 71;
        for(; i<=80; i++)
        {
            int name = getApplicationContext().getResources().getIdentifier("food"+(i),"string",getApplicationContext().getPackageName());
            int attr1 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_1","string",getApplicationContext().getPackageName());
            int attr2 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_2","string",getApplicationContext().getPackageName());
            int attr3 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_3","string",getApplicationContext().getPackageName());
            int attr4 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_4","string",getApplicationContext().getPackageName());
            int attr5 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_5","string",getApplicationContext().getPackageName());
            int pic = getApplicationContext().getResources().getIdentifier("food"+(i),"drawable",getApplicationContext().getPackageName());
            FoodUtils.addFood(mContext,i,name,"徽菜",pic,
                    attr1,attr2,attr3,attr4,attr5);
        }

    }

    //其他标签
    public void addNewsToList10()
    {
        FoodUtils.removeAllFood();
        FoodUtils.addFood(mContext,57,R.string.food57,"沪菜",R.drawable.food57,
                R.string.food57_1,R.string.food57_2,R.string.food57_3,R.string.food57_4,R.string.food57_5);
        FoodUtils.addFood(mContext,61,R.string.food61,"赣菜",R.drawable.food61,
                R.string.food61_1,R.string.food61_2,R.string.food61_3,R.string.food61_4,R.string.food61_5);
        FoodUtils.addFood(mContext,81,R.string.food81,"京菜",R.drawable.food81,
                R.string.food81_1,R.string.food81_2,R.string.food81_3,R.string.food81_4,R.string.food81_5);
        FoodUtils.addFood(mContext,82,R.string.food82,"京菜",R.drawable.food82,
                R.string.food82_1,R.string.food82_2,R.string.food82_3,R.string.food82_4,R.string.food82_5);
        FoodUtils.addFood(mContext,90,R.string.food90,"京菜",R.drawable.food90,
                R.string.food90_1,R.string.food90_2,R.string.food90_3,R.string.food90_4,R.string.food90_5);
        int i = 91;
        for(; i<=97; i++)
        {
            int name = getApplicationContext().getResources().getIdentifier("food"+(i),"string",getApplicationContext().getPackageName());
            int attr1 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_1","string",getApplicationContext().getPackageName());
            int attr2 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_2","string",getApplicationContext().getPackageName());
            int attr3 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_3","string",getApplicationContext().getPackageName());
            int attr4 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_4","string",getApplicationContext().getPackageName());
            int attr5 = getApplicationContext().getResources().getIdentifier("food"+(i)+"_5","string",getApplicationContext().getPackageName());
            int pic = getApplicationContext().getResources().getIdentifier("food"+(i),"drawable",getApplicationContext().getPackageName());
            FoodUtils.addFood(mContext,i,name,"豫菜",pic,
                    attr1,attr2,attr3,attr4,attr5);
        }
        FoodUtils.addFood(mContext,99,R.string.food99,"豫菜",R.drawable.food99,
                R.string.food99_1,R.string.food99_2,R.string.food99_3,R.string.food99_4,R.string.food99_5);

    }
}
