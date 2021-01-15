package com.example.testapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adapter.ContactAdapter;
import com.example.pojo.ContactBean;
import com.example.utils.ContactUtils;
import com.example.utils.UserDataManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//设置-紧急联系人界面
public class ContactActivity extends AppCompatActivity{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SHOW_TEXT = "text";
    ListView lv_news;
    private String mContentText;
    private SharedPreferences sharedPreferences;
    private Context mContext;
    private TextView add;//添加联系人
    public static int startnum = 0 ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        startnum++;
        if(startnum ==1)
        {
            removeNewsToList();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_contact);
        add = findViewById(R.id.b1);
        mContext = ContactActivity.this;
        sharedPreferences = getSharedPreferences("userInfo", Context .MODE_PRIVATE);
        String uid=sharedPreferences.getString("_uid","-1");

        if (startnum==1)
            addNewsToList();

        ArrayList<ContactBean> allContact = ContactUtils.getAllContact(mContext);
        //根据指定的引用布局加载
        lv_news = (ListView) findViewById(R.id.lv_contact);
        ContactAdapter contactAdapter = new ContactAdapter(mContext, allContact);
        contactAdapter.setUserid(uid);
        lv_news.setAdapter(contactAdapter);
        lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                contactAdapter.getIndex(position);
            }
        });
        //4.设置listview条目的点击事件
        //lv_news.setOnItemClickListener(this);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o = new Intent(getApplicationContext(),AddContactActivity.class);
                startActivity(o);
            }
        });



    }


/*    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        //需要获取条目上bean对象中url做跳转
        ContactBean bean = (ContactBean) parent.getItemAtPosition(position);

        int realposition = position%25;
        //跳转,这里功能还暂未实现
        Intent intent = new Intent(this,NewsDetailActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        intent.putExtra("position",realposition);
        Log.i("position",realposition+"");
        startActivity(intent);

    }*/

    //添加紧急联系人内容方法
    public void addNewsToList()
    {

        String uid=sharedPreferences.getString("_uid","-1");
        String res = UserDataManager.getContacts(uid);
        if(res!=""&&res!=null)
        {
            try {
                JSONArray array = new JSONArray(res);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    String name = object.getString("cname");
                    String email = object.getString("email");
                    String phone = object.getString("phone");
                    String cid = object.getString("cid");
                    ContactUtils.addContact(getApplicationContext(),name,email,phone,cid);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    public void removeNewsToList()
    {
        ContactUtils.removeContacts();
    }

}
