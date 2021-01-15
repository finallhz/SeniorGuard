package com.example.testapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adapter.ContactAdapter;
import com.example.pojo.ContactBean;
import com.example.utils.ContactUtils;
import com.example.utils.UserDataManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//添加联系人的Activity
public class AddContactActivity extends AppCompatActivity {
    private EditText et1;
    private EditText et2;
    private EditText et3;
    private Button add;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcontact);
        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et11);
        et3 = findViewById(R.id.et12);
        add = findViewById(R.id.login_btn_login);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact();
            }
        });
    }
    public void addContact()
    {
        if(et1.getText()==null||et1.getText().equals(""))
            Toast.makeText(AddContactActivity.this,"请您输入联系人姓名完整信息！",Toast.LENGTH_SHORT).show();
        else if(et2.getText()==null||et2.getText().equals(""))
            Toast.makeText(AddContactActivity.this,"请您输入联系人手机完整信息！",Toast.LENGTH_SHORT).show();
        else if(et3.getText()==null||et3.getText().equals(""))
            Toast.makeText(AddContactActivity.this,"请您输入联系人邮箱完整信息！",Toast.LENGTH_SHORT).show();

        //首先获得当前用户的id号
        String uid =sharedPreferences.getString("_uid","");
        if(uid.equalsIgnoreCase("")||uid==null)
        {
            Toast.makeText(AddContactActivity.this,"您当前尚未登录，请您登录！",Toast.LENGTH_SHORT).show();
        }else
        {
            //如果当前登录了，就上传这些信息到服务器
            String name = et1.getText().toString();
            String phone = et2.getText().toString();
            String num = et3.getText().toString();
            String res = UserDataManager.addContacts(uid,name,phone,num);
            if(res!=null&&!res.equals(""))
            {
                Toast.makeText(AddContactActivity.this,"添加联系人信息成功！",Toast.LENGTH_SHORT).show();
                ContactActivity.startnum=0;
                Intent a = new Intent(getApplicationContext(),ContactActivity.class);
                startActivity(a);
                finish();

            }else
            {
                Toast.makeText(AddContactActivity.this,"添加联系人信息失败！请询问管理员",Toast.LENGTH_SHORT).show();
            }

        }
    }
}
