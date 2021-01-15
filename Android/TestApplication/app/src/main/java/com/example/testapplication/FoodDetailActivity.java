package com.example.testapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashSet;

public class FoodDetailActivity extends Activity {
    TextView tvname;
    ImageView iv1;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tv5;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fooddetail);
        tvname = findViewById(R.id.tv1);
        tv1 = findViewById(R.id.tv2);
        tv2 = findViewById(R.id.tv3);
        tv3 = findViewById(R.id.tv4);
        tv4 = findViewById(R.id.tv5);
        tv5 = findViewById(R.id.tv6);
        iv1 = findViewById(R.id.iv1);
        Intent i = this.getIntent();
        int mName= i.getIntExtra("id",1);
        int name = getApplicationContext().getResources().getIdentifier("food"+(mName),"string",getApplicationContext().getPackageName());
        int attr1 = getApplicationContext().getResources().getIdentifier("food"+(mName)+"_1","string",getApplicationContext().getPackageName());
        int attr2 = getApplicationContext().getResources().getIdentifier("food"+(mName)+"_2","string",getApplicationContext().getPackageName());
        int attr3 = getApplicationContext().getResources().getIdentifier("food"+(mName)+"_3","string",getApplicationContext().getPackageName());
        int attr4 = getApplicationContext().getResources().getIdentifier("food"+(mName)+"_4","string",getApplicationContext().getPackageName());
        int attr5 = getApplicationContext().getResources().getIdentifier("food"+(mName)+"_5","string",getApplicationContext().getPackageName());
        int pic = getApplicationContext().getResources().getIdentifier("food"+(mName),"drawable",getApplicationContext().getPackageName());
        tvname.setText(name);
        tv1.setText(attr1);
        tv2.setText(attr2);
        tv3.setText(attr3);
        //这两个倒换下位置，要不空闲太多太难看了
        tv5.setText(attr4);
        tv4.setText(attr5);
        iv1.setImageResource(pic);

    }
}
