package com.example.testapplication;

import com.example.utils.UserDataManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//心跳数据图表展示
//Chart2的数据还没填充，应该是记录心跳的异常速度次数
public class HeartbeatChartActivity extends Activity {
    private Handler handler;
    private LineChart mChart1;//心跳图表
    private LineChart mChart2;//步数图表
    private Thread t1;//图表异步线程1
    private Handler mHandler = new Handler(Looper.getMainLooper()); // 全局变量,轮询相关
    //private ArrayList al;
    private TextView tv1;//心跳
    private TextView tv2;//步数
    private String heartandstep;//心跳步数数据
    List<Entry> list1=new ArrayList<>();//心跳数据集合
    List<Entry> list2=new ArrayList<>();//步数数据集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //al = new ArrayList();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_heartbeat);
        tv1  = findViewById(R.id.fall3);
        tv2= findViewById(R.id.fall5);
        //请求历史步数和心跳结果
        Intent it = this.getIntent();
        heartandstep = UserDataManager.toHeartBeatAndStep(it.getStringExtra("_uid"));
        //创建之后初始化WebSocket，与服务器建立连接
        UserDataManager.toServerSocket();
        serverData();
        mChart1 = (LineChart) findViewById(R.id.chart1);
        mChart2 = (LineChart) findViewById(R.id.chart2);
        addDataEntry();
        addDataEntryToStep();

    }

    //当这个Activity被销毁时，销毁线程
    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);

        super.onDestroy();
    }


    private void addDataEntryToStep()
    {
        String[] split=heartandstep.split(";");
        StringBuffer sb1 = new StringBuffer(split[1]);
        sb1 = sb1.deleteCharAt(0);
        sb1 = sb1.deleteCharAt(sb1.length()-1);
        String str =sb1.toString().replaceAll(" ","");
        System.out.println("str:"+str);
        String[] splitMiddle = str.split(",");//步数数据中间处理
        String[] splitEnd = new String[7];
        int i =0;
        for(;i<splitMiddle.length;i++)
        {
            String[] splitStep = splitMiddle[i].split("%");//最后的步数数据
            splitEnd[i] = splitStep[1];
            System.out.println("插入的记录："+splitEnd[i]);

        }
        for(;i<splitEnd.length;i++)
        {
            if(splitEnd[i]==null)
                splitEnd[i] = "0";
            //若后面数据不为0，则补齐0
        }
        int y =0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MMdd");
        System.out.println(formatter.format(date));
        Log.i("232323",formatter.format(date));
        int dateData = Integer.parseInt(formatter.format(date));
        for(;y<splitEnd.length;y++)
        {
            int u = y+1;
            /**将ArrayList中的数据解析**/
            Entry entry = new Entry(u, Integer.parseInt(splitEnd[y]));
            list2.add(entry);
            mChart2.notifyDataSetChanged();
            dateData = dateData-1;
        }

        LineDataSet lineDataSet=new LineDataSet(list2,"步数数据");
        LineData lineData=new LineData(lineDataSet);
        mChart2.setData(lineData);
        mChart2.notifyDataSetChanged();
        //   X轴所在位置   默认为上面
        mChart2.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        //隐藏右边的Y轴
        mChart2.getAxisRight().setEnabled(false);
    }

    private void addDataEntry()
    {
        String[] split=heartandstep.split(";");
        StringBuffer sb1 = new StringBuffer(split[0]);
        sb1 = sb1.deleteCharAt(0);
        sb1 = sb1.deleteCharAt(sb1.length()-1);
        String str =sb1.toString().replaceAll(" ","");
        String[] splitEnd = str.split(",");//最后的心跳数据
        int y =0;
        for(;y<splitEnd.length;y++)
        {
            /**将ArrayList中的数据解析**/
            int i = y+1;
            Entry entry = new Entry( i, Integer.parseInt(splitEnd[y]));
            list1.add(entry);
            mChart1.notifyDataSetChanged();
        }
        Log.i("chart1","触发该方法！");
        Log.i("chart1",list1.toString());
        LineDataSet lineDataSet=new LineDataSet(list1,"心跳数据");
        LineData lineData=new LineData(lineDataSet);
        mChart1.setData(lineData);
        mChart1.notifyDataSetChanged();
        //   X轴所在位置   默认为上面
        mChart1.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        //隐藏右边的Y轴
        mChart1.getAxisRight().setEnabled(false);
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
                //Log.i("message1","当前信息："+UserDataManager.message);
                //System.out.println("json数据："+infor);
                try {
                    /*JSONArray jsonArray = new JSONArray(infor);
                    for(int ii=0;ii<jsonArray.length();ii++) {
                        System.out.println("心率序列："+jsonArray.getJSONObject(ii).get("heart"));//获得心脏数据序列
                        al.add(jsonArray.getJSONObject(ii).get("heart").toString());//加入后面的序列中
                    }*/
                    JSONObject jsonObject = new JSONObject(infor);
                    //al.add(jsonObject.get("heart").toString());//加入后面的序列中
                    //Log.i("heart",jsonObject.get("heart").toString());
                    //Log.i("stepCount",jsonObject.get("stepCount").toString());
                    tv1.setText(jsonObject.get("heart").toString());
                    tv2.setText(jsonObject.get("stepCount").toString());
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

}