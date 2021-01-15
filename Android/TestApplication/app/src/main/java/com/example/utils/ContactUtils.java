package com.example.utils;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.pojo.ContactBean;
import com.example.pojo.NewsBean;
import com.example.testapplication.R;

import java.util.ArrayList;

public class ContactUtils {
    public static ArrayList<ContactBean> arrayList = new ArrayList<ContactBean>();
    //封装新闻的假数据到list中返回,以后数据会从数据库中获取
    public static ArrayList<ContactBean> getAllContact(Context context) {

        return arrayList;
    }
    public static void removeContacts(){
        arrayList.removeAll(arrayList);
    }
    public static void removeContactToList(int pos){
        arrayList.remove(pos);
    }
    public static void addContact(Context context,int name, int num)
    {
        ContactBean newsContact = new ContactBean();
        newsContact.name =context.getResources().getString(name);
        newsContact.num= context.getResources().getString(num);

        arrayList.add(newsContact);
    }

    //重载方法，也可以使用字符串进行加载
    public static void addContact(Context context,String name, String num)
    {
        ContactBean newsContact = new ContactBean();
        newsContact.name =name;
        newsContact.num= num;

        arrayList.add(newsContact);
    }

    public static void addContact(Context context,String name, String num,String email,String cid)
    {
        ContactBean newsContact = new ContactBean();
        newsContact.name =name;
        newsContact.num= num;
        newsContact.email =email;
        newsContact.cid =cid;

        arrayList.add(newsContact);
    }

}
