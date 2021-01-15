package com.snnu.WebSocket;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DataReceiveListener implements ServletContextListener {

    private Thread thread;

    @Override
    //线程销毁
    public void contextDestroyed(ServletContextEvent sce) {
        if (thread != null && thread.isInterrupted()) {
            thread.interrupt();
        }
    }

    @Override
    //线程启动
    public void contextInitialized(ServletContextEvent sce) {
        if (thread == null) {
            System.out.println("手表数据监听器启动");
//			ReceiveData rd = new ReceiveData();
            ListenWatchSocket lws = new ListenWatchSocket();
            thread = new Thread(lws);
            thread.start();
        }
    }

}