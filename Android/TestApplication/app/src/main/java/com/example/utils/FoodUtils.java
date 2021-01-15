package com.example.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.example.pojo.ContactBean;
import com.example.pojo.FoodBean;

import java.util.ArrayList;

public class FoodUtils {
    public static ArrayList<FoodBean> arrayList = new ArrayList<FoodBean>();
    public static ArrayList<FoodBean> arrayList1 = new ArrayList<FoodBean>();
    //封装新闻的假数据到list中返回,以后数据会从数据库中获取
    public static ArrayList<FoodBean> getAllFood(Context context) {

        return arrayList;
    }
    //封装新闻的假数据到list中返回,以后数据会从数据库中获取
    public static ArrayList<FoodBean> getAllFoodResult(Context context) {

        return arrayList1;
    }
    public static void removeAllFood()
    {
        arrayList.removeAll(arrayList);
    }
    public static void removeAllFoodResult()
    {
        arrayList1.removeAll(arrayList1);
    }
    public static void addFoodResult(Context context,int id,int name, String type, int icon,int first
            ,int second,int third,int fourth,int fifth)
    {
        FoodBean foodContact = new FoodBean();
        foodContact.id =id;
        foodContact.name =context.getResources().getString(name);
        foodContact.type= type;
        foodContact.icon= context.getResources().getDrawable(icon);
        foodContact.first= context.getResources().getString(first);
        foodContact.second= context.getResources().getString(second);
        foodContact.third= context.getResources().getString(third);
        foodContact.fourth= context.getResources().getString(fourth);
        foodContact.fifth= context.getResources().getString(fifth);

        arrayList1.add(foodContact);
    }
    public static void addFood(Context context,int id,int name, String type, int icon,int first
            ,int second,int third,int fourth,int fifth)
    {
        FoodBean foodContact = new FoodBean();
        foodContact.id =id;
        foodContact.name =context.getResources().getString(name);
        foodContact.type= type;
        foodContact.icon= context.getResources().getDrawable(icon);
        foodContact.first= context.getResources().getString(first);
        foodContact.second= context.getResources().getString(second);
        foodContact.third= context.getResources().getString(third);
        foodContact.fourth= context.getResources().getString(fourth);
        foodContact.fifth= context.getResources().getString(fifth);

        arrayList.add(foodContact);
    }
    //重载方法，也可以使用字符串进行加载
    public static void addFood(Context context, String name, String type, int icon,String first
    ,String second,String third,String fourth,String fifth)
    {
        FoodBean foodContact = new FoodBean();
        foodContact.name =name;
        foodContact.type= type;
        foodContact.icon = zoomDrawable(context.getResources().getDrawable(icon), 200, 150);
        foodContact.first =first;
        foodContact.second =second;
        foodContact.third =third;
        foodContact.fourth =fourth;
        foodContact.fifth =fifth;


        arrayList.add(foodContact);
    }

    //缩放图像
    static Drawable zoomDrawable(Drawable drawable, int w, int h)
    {
        int width = drawable.getIntrinsicWidth();
        int height= drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable); // drawable 转换成 bitmap
        Matrix matrix = new Matrix();   // 创建操作图片用的 Matrix 对象
        float scaleWidth = ((float)w / width);   // 计算缩放比例
        float scaleHeight = ((float)h / height);
        matrix.postScale(scaleWidth, scaleHeight);         // 设置缩放比例
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);       // 建立新的 bitmap ，其内容是对原 bitmap 的缩放后的图
        return new BitmapDrawable(newbmp);       // 把 bitmap 转换成 drawable 并返回
    }

    static Bitmap drawableToBitmap(Drawable drawable) // drawable 转换成 bitmap
    {
        int width = drawable.getIntrinsicWidth();   // 取 drawable 的长宽
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888:Bitmap.Config.RGB_565;         // 取 drawable 的颜色格式
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);     // 建立对应 bitmap
        Canvas canvas = new Canvas(bitmap);         // 建立对应 bitmap 的画布
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);      // 把 drawable 内容画到画布中
        return bitmap;
    }

}