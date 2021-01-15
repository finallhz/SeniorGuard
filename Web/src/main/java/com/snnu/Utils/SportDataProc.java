package com.snnu.Utils;

import java.util.ArrayList;
import java.util.List;

public class SportDataProc {


    public static List<SportData> dataProc(String[] strArray){
        //处理后数据
        List<SportData> list = new ArrayList<>();
        SportData sportData;
        double acc;
        double gyr;
        for (String str: strArray) {
            //原始数据 012加速度 345角速度 6心跳 7步数 8时间戳
            String[] s = str.trim().split(" ");
            Float[] floatArray = new Float[6];
            for (int i = 0; i < 6; i++) {
                 floatArray[i] = Float.parseFloat(s[i]);
            }
            acc = compSum(floatArray[0],floatArray[1],floatArray[2]);
            gyr = compSum(floatArray[3],floatArray[4],floatArray[5]);
            sportData = new SportData(acc, gyr);
            list.add(sportData);
        }
        return list;
    }

    public static double compSum(float f1, float f2, float f3){
        return Math.sqrt(Math.pow(f1, 2) + Math.pow(f2, 2) + Math.pow(f3, 2));
    }

    public static int currHeart(String[] strArray){
        String str = strArray[strArray.length - 3];
        String[] s = str.trim().split(" ");
        return Integer.parseInt(s[6]);
    }

    public static int currStep(String[] strArray){
        String str = strArray[strArray.length - 3];
        String[] s = str.trim().split(" ");
        return Integer.parseInt(s[7]);
    }
}
