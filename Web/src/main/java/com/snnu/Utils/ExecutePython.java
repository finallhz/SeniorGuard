package com.snnu.Utils;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ExecutePython {

    public List<String> exeCmd(String[] exeStr){
        List<String> list = new ArrayList<String>();

        try {
            //System.out.println(Arrays.toString(exeStr));
            Process process = Runtime.getRuntime().exec(exeStr);
            //正常情况下，输出结果
            InputStream inputStream = process.getInputStream();
            InputStream errorStream = process.getErrorStream();
            //BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "gbk"));
            LineNumberReader reader = new LineNumberReader(new InputStreamReader(inputStream, "gbk"));
            BufferedReader error = new BufferedReader(new InputStreamReader(errorStream, "gbk"));

            String line = null;
            while ((line = reader.readLine()) != null){
                list.add(line);
                System.out.println("ExecutePython类：" + line);
            }
            reader.close();

            while ((line = error.readLine()) != null){
                System.out.println("这是python错误信息:" + line);
            }
            error.close();
            //java代码中的process.waitFor()返回值为0表示我们调用python脚本成功，
            //返回值为1表示调用python脚本失败，这和我们通常意义上见到的0与1定义正好相反
            System.out.println("waitFor:" + process.waitFor());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }
}
