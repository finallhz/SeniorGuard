package com.example.testapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//设置界面-个性签名
public class MySignActivity extends AppCompatActivity {
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mysign);
        bt = findViewById(R.id.bt1);
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "保存个性签名成功！", Toast.LENGTH_LONG).show();
                //返回前一个Activity,设置跳转回主Actiivity,应该是返回碎片这里没有写。
                Intent it = new Intent(MySignActivity.this,MainActivity.class);
                startActivity(it);
            }

        });

    }
}
