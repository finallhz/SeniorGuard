package com.example.testapplication;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.utils.UserDataManager;

//运动推荐的Activity
public class SportRecommendActivity extends AppCompatActivity {
    //热量值
    private int num;
    private EditText energyet;//当前摄入，从服务器提取数据
    private EditText lostenergyet;//当前消耗，手动输入
    private Button button;//开始推荐按钮
    //下面为推荐的几个图画
    private ImageView im1;
    private TextView tv1;
    private ImageView im2;
    private TextView tv2;
    private ImageView im3;
    private TextView tv3;
    private ImageView im4;
    private TextView tv4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sportrec);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }//隐藏标题栏
        energyet = findViewById(R.id.et1);
        lostenergyet = findViewById(R.id.et2);
        button = findViewById(R.id.bt1);
        im1 = findViewById(R.id.iv11);
        tv1= findViewById(R.id.tv11);
        im2 = findViewById(R.id.iv21);
        tv2 = findViewById(R.id.tv21);
        im3 = findViewById(R.id.iv31);
        tv3 = findViewById(R.id.tv31);
        im4 = findViewById(R.id.iv41);
        tv4 = findViewById(R.id.tv41);
        //主线程请求热量记录
        String energy = UserDataManager.getEnergy();
        //请求完毕后设置该数值到EditText中
        energyet.setText(energy);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击的时候计算差值，根据差值进行推荐
                int energyNum = Integer.parseInt(energyet.getText().toString());
                int energylostNum = Integer.parseInt(lostenergyet.getText().toString());
                if(energyNum-energylostNum<100)
                {
                    //推荐做家务，上下楼梯，插花，散步
                    im1.setImageResource(R.drawable.spo01);
                    im2.setImageResource(R.drawable.spo02);
                    im3.setImageResource(R.drawable.spo03);
                    im4.setImageResource(R.drawable.spo04);
                    tv1.setText("做家务");
                    tv2.setText("上下楼梯");
                    tv3.setText("插花");
                    tv4.setText("散步");
                }else if(energyNum-energylostNum>=100&&energyNum-energylostNum<=300)
                {
                    //推荐打太极、漫步、健身操、打拳
                    im1.setImageResource(R.drawable.spo05);
                    im2.setImageResource(R.drawable.spo06);
                    im3.setImageResource(R.drawable.spo07);
                    im4.setImageResource(R.drawable.spo08);
                    tv1.setText("打太极");
                    tv2.setText("慢步");
                    tv3.setText("健身操");
                    tv4.setText("打拳");
                }else if(energyNum-energylostNum>300)
                {
                    //推荐乒乓球，慢跑，骑自行车，广场舞
                    im1.setImageResource(R.drawable.spo09);
                    im2.setImageResource(R.drawable.spo10);
                    im3.setImageResource(R.drawable.spo11);
                    im4.setImageResource(R.drawable.spo12);
                    tv1.setText("乒乓球");
                    tv2.setText("慢跑");
                    tv3.setText("骑自行车");
                    tv4.setText("广场舞");
                }
            }
        });
    }



}
