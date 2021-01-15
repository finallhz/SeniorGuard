package com.snnu.RunPython;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FallJudgment {
    public static void main(String[] args) {
        String filePath = "E:\\赛尔\\数据集\\python file\\modelPrediction.py";
        String sourceFile = "E:\\赛尔\\数据集\\chooseData\\feaAllDateShuf.csv";
        String targetFile = "E:\\赛尔\\数据集\\chooseData\\dataRandomExtra.csv";
        String[] s = new String[]{"python", filePath, sourceFile, targetFile};
        try {
            Process process = Runtime.getRuntime().exec(s);
            //正常情况下，输出结果
            InputStream inputStream = process.getInputStream();
            InputStream errorStream = process.getErrorStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "gbk"));
            BufferedReader error = new BufferedReader(new InputStreamReader(errorStream, "gbk"));

            String line = null;
            while ((line = reader.readLine()) != null){
                System.out.println(line);
            }
            reader.close();

            while ((line = error.readLine()) != null){
                System.out.println(line);
            }
            error.close();

            System.out.println(process.waitFor());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
