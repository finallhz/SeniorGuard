package com.snnu.WebSocket;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class Data {
    List<String> datas = new ArrayList<>();

    public synchronized boolean addData(String sportdata) {
        boolean result = datas.add(sportdata);
        return result;
    }

    public synchronized String removeData() {
        String sportdata = null;
        if (datas.size() > 0){
            sportdata = datas.remove(0);
        }
        return sportdata;
    }
}
