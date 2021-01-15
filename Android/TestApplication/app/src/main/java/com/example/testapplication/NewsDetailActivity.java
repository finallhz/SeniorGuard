package com.example.testapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsDetailActivity extends Activity {
    TextView head;
    TextView details;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetail);
        Intent intent=getIntent();
        int position=intent.getIntExtra("position", -1);
        Log.i("position",position+"接收到");
        head = findViewById(R.id.textView3);
        details = findViewById(R.id.textView4);
        iv = findViewById(R.id.imageView);
        int id = getResources().getIdentifier("news"+(position+1),"string",getPackageName());
        head.setText(id);
        int id1 = getResources().getIdentifier("newsdes"+(position+1),"string",getPackageName());
        details.setText(id1);
        int id2 = getResources().getIdentifier("news"+(position+1),"drawable",getPackageName());
        iv.setImageDrawable(getResources().getDrawable(id2));
    }
}
