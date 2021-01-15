package com.snnu.WebSocket;

import com.alibaba.fastjson.JSONObject;
import com.snnu.RunPython.FallDetect;
import com.snnu.Utils.DataUtil;
import com.snnu.Utils.SportData;
import com.snnu.Utils.SportDataProc;
import org.springframework.stereotype.Component;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DataProcess implements Runnable{

    private Data sportdata;

    public DataProcess(Data sportdata) {
        this.sportdata = sportdata;
        ListenWebSocket lws = new ListenWebSocket();
        lws.setSportdata(sportdata);
        new Thread(lws).start();
    }

    public void sleepTime() {
        try {
            Thread.currentThread().sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        //准备空包
        byte[] data = new byte[10240];
        DatagramPacket packet = new DatagramPacket(data, data.length);

        //准备socket
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(8081);
            String string = "";//用于存放数据内容
            /*StringBuffer sb = new StringBuffer();//每100条数据进行一次摔倒模型检测
            int columnCount = 0;//记录sb中有多少条数据*/
            List<String> list = new ArrayList<String>();//每100条数据进行一次摔倒模型检测

            while (true) {
                //接收数据包
                socket.receive(packet);//数据格式 uid,数据
                string = new String(packet.getData(), 0, packet.getLength());
                String[] strArray = string.trim().split("\n");
                //记录当前状态  默认未摔倒
                int status = 0;
                if (list.size() + strArray.length >= 100) {
                    int needCount = 100 - list.size();
                    String[] subStrArray = Arrays.copyOfRange(strArray, 0, needCount);
                    //System.out.println(subStrArray.length);
                    Collections.addAll(list, subStrArray);
                    //此时list已经获取100条数据，需要执行python
                    //System.out.println(list.toString());
                    //判断是否摔倒
                    String judge = new FallDetect().fallDetect(list.toString());
                    status = Integer.parseInt(judge);
                    list.clear();
                    String[] remainArray = Arrays.copyOfRange(strArray, needCount, 30);
                    Collections.addAll(list, remainArray);

                } else {
                    Collections.addAll(list, strArray);
                    //System.out.println(list.size());
                }

                //将每秒获取到的数据转化为echart数值
                List<SportData> list1 = SportDataProc.dataProc(strArray);

                //获取当前心跳
                int heart = SportDataProc.currHeart(strArray);
                //获取当前步数
                int stepCount = SportDataProc.currStep(strArray);

                DataUtil dataUtil = new DataUtil(list1, status, heart, stepCount);
                //System.out.println(dataUtil);

                String jsonString = JSONObject.toJSONString(dataUtil);

                sportdata.addData(jsonString);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }
}
