package com.example.testapplication;

import com.example.testapplication.R;
import com.example.utils.UserDataManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

//基本的加速度和角速度的数据图表展示
public class BasicChartActivity extends Activity {

    private Handler handler;
    private LineChart mChart1;//加速度图表
    private LineChart mChart2;//角速度图表
    private Thread t1;//图表异步线程1
    List<Entry> list1=new ArrayList<>();//加速度数据集合
    List<Entry> list2=new ArrayList<>();//角速度数据集合
    public int list1count =1 ;
    public int list2count =1 ;
    public TextView _status;
    public Timer timer;
    //图表相关部分
    LineDataSet lineDataSet;
    LineDataSet lineDataSet2;
    LineData lineData;
    LineData lineData2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_basic);
        mChart1= findViewById(R.id.chart1);
        mChart2= findViewById(R.id.chart2);
        _status = findViewById(R.id.fall3);
        //创建之后初始化WebSocket，与服务器建立连接
        UserDataManager.toServerSocket();
        //访问数据和添加图表数据的方法
        serverData();
        //图表1美化
        mChart1.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChart1.getAxisRight().setEnabled(false);
        //图表2美化
        mChart2.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChart2.getAxisRight().setEnabled(false);
    }
    //当这个Activity被销毁时，销毁线程
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void serverData()
    {

        //应该改为没过0.5秒更新一次数据，从服务器提取一次数据
        handler =new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                //首先清除掉所有数据
                //System.out.println("开始子线程运行！");
                //al.removeAll(al);
                String infor = UserDataManager.message;
                //System.out.println("json数据："+infor);
                try {
                    JSONObject jsonObject = new JSONObject(infor);
                    System.out.println("jsonObject:"+jsonObject.toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("sportDatas");
                    for(int i=0;i<jsonArray.length();i++) {
                        //System.out.println("当前的加速度："+jsonArray.getJSONObject(i).get("acc"));
                        //System.out.println("当前的角速度："+jsonArray.getJSONObject(i).get("gyr"));
                        System.out.println("当前的摔倒状态："+jsonObject.get("status"));
                        addAcce(Float.parseFloat(jsonArray.getJSONObject(i).get("acc").toString()));
                        addAngular(Float.parseFloat(jsonArray.getJSONObject(i).get("gyr").toString()));
                        setStatus(Integer.parseInt(jsonObject.get("status").toString()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //将图表刷新

                handler.postDelayed(this, 500);
            }
        };
        t1 = new Thread(runnable);
        t1.start();//启动第一个加速度数据的绘制
    }

    //设置跌倒状态
    public void setStatus(int status)
    {
        if(timer == null)
        {
            timer = new Timer();
        }
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //需要执行的任务在这里处理，如果涉及更新ui，则需要Handler辅助
                    // eg： handler.sendEmptyMessage(0);
                    System.out.println("线程触发！");
                    if(status ==1)
                    {
                        //如果摔倒了，设置该状态五秒钟
                        System.out.println("线程触发！摔倒了！");
                        _status.setTextColor(Color.rgb(255, 48, 48));
                        _status.setText("跌倒");

                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }else if(status ==0)
                    {
                        System.out.println("线程触发！未摔倒！");
                        _status.setTextColor(Color.rgb(0, 0, 0));
                        _status.setText("未跌倒");

                    }

                }
            }, 1000);




    }

    //加速度图表显示方法
    public void addAcce(Float addAcce)
    {
        //图表的初始化和设置数据集部分
        lineDataSet=new LineDataSet(list1,"加速度数据");
        lineDataSet.setDrawValues(false);//在点上显示数值 默认true
        lineDataSet.setDrawCircles(false);//在点上画圆 默认true
        lineDataSet.setLineWidth(3f);
        lineData=new LineData(lineDataSet);
        list1.add(new Entry(list1count,addAcce));
        //System.out.println("当前加速度数据集中的数据："+list1.toString());
        mChart1.setData(lineData);
        list1count++;
        mChart1.notifyDataSetChanged();//通知更新速度
        mChart1.invalidate();
        mChart1.requestLayout();
        //System.out.println("移动到图表1");
        mChart1.moveViewToX(list1count);
        mChart1.getXAxis().setAxisMinimum(list1count-650);
        mChart1.getXAxis().setAxisMaximum(list1count+50);
    }
    //加速度图表显示方法
    public void addAngular(Float ang)
    {

        //图表的初始化和设置数据集部分
        lineDataSet2 = new LineDataSet(list2, "角速度数据");
        lineDataSet2.setDrawValues(false);//在点上显示数值 默认true
        lineDataSet2.setDrawCircles(false);//在点上画圆 默认true
        lineDataSet2.setLineWidth(3f);
        lineData2 = new LineData(lineDataSet2);
        list2.add(new Entry(list2count,ang));
        //System.out.println("当前角速度数据集中的数据："+list2.toString());
        mChart2.setData(lineData2);
        list2count++;
        mChart2.notifyDataSetChanged();//通知更新速度
        mChart2.invalidate();
        mChart2.requestLayout();
        //System.out.println("移动到图表2");
        mChart2.moveViewToX(list2count);
        mChart2.getXAxis().setAxisMinimum(list2count-650);
        mChart2.getXAxis().setAxisMaximum(list2count+50);

    }
}