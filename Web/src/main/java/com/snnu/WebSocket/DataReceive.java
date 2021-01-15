package com.snnu.WebSocket;

import java.io.IOException;
import java.net.*;

public class DataReceive {
    public static void main(String[] args) {
        System.out.println("开启新线程");
        NewThread newThread = new NewThread();
        Thread t = new Thread(newThread);
        t.start();
    }



}

class NewThread implements Runnable{

    @Override
    public void run() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            System.out.println("ipv4地址："+inetAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        //准备空包
        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);

        //准备socket
        DatagramSocket socket = null;
        String msg = "";//用于存放数据内容
        try {
            socket = new DatagramSocket(8081);
            while (true){
                socket.receive(packet);

                //显示接收到的数据
                msg = new String(packet.getData(), 0, packet.getLength());

                System.out.println(packet.getAddress().getHostAddress()+ ":" + msg);

            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}