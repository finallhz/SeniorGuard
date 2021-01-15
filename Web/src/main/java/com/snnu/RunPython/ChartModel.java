package com.snnu.RunPython;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ChartModel {

    public List<String> sportState(int lineNum){
        String filePath = "E:\\赛尔\\数据集\\python file\\webDataJson.py";
        String dataPath = "E:\\赛尔\\数据集\\chooseData\\webData1.csv";
//        int lineNum = 1100;
        String[] s = new String[]{"python", filePath, dataPath, String.valueOf(lineNum)};

        List<String> arr = new ArrayList<String>();

        try {
            Process process = Runtime.getRuntime().exec(s);
            //正常情况下，输出结果
            InputStream inputStream = process.getInputStream();
            InputStream errorStream = process.getErrorStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "gbk"));
            BufferedReader error = new BufferedReader(new InputStreamReader(errorStream, "gbk"));

            String line = null;
            while ((line = reader.readLine()) != null){
                arr.add(line);
                System.out.println(line);
            }

            while ((line = error.readLine()) != null){
                System.out.println(line);
            }
            error.close();

            reader.close();

            //java代码中的process.waitFor()返回值为0表示我们调用python脚本成功，
            //            //返回值为1表示调用python脚本失败，这和我们通常意义上见到的0与1定义正好相反
            System.out.println(process.waitFor());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static void main(String[] args) {
        new ChartModel().sportState(1100);
    }
}
