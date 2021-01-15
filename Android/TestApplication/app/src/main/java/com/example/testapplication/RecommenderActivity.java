package com.example.testapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

//记录-推荐界面
public class RecommenderActivity extends AppCompatActivity {
    RelativeLayout rl1;
    RelativeLayout rl2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommender);

        rl1 = findViewById(R.id.ll0);
        rl2 = findViewById(R.id.ll1);
        //运动推荐
        rl1.setOnClickListener(new  View.OnClickListener() {
            public void onClick(View v) {
              Intent ii = new Intent(RecommenderActivity.this,SportRecommendActivity.class);
              startActivity(ii);
            }
        });
        //饮食推荐
        rl2.setOnClickListener(new  View.OnClickListener() {
            public void onClick(View v) {
                Intent ii = new Intent(RecommenderActivity.this, FoodRecommendActivity.class);
                startActivity(ii);
            }
        });
    }
}
