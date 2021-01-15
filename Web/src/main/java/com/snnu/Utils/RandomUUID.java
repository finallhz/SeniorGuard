package com.snnu.Utils;

import java.util.UUID;

public class RandomUUID {
    /*
     * 生成唯一token
     */
    public static String getRandomUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
