package com.snnu.WebSocket;

import java.io.FileWriter;
import java.io.IOException;
import java.net.*;

public class ListenWatchSocket implements Runnable{

    @Override
    public void run() {
        /*try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            System.out.println("ipv4地址："+inetAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }*/

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
                if (!"".equals(msg)){
                    writeData(msg);
                }

                System.out.println(packet.getAddress().getHostAddress()+ ":" + msg);

            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeData(String str){
        String fileName="F:\\watchData.csv";
        try {
            String[] dataLine = str.split("\n");
            FileWriter writer = new FileWriter(fileName, true);
            for (String s: dataLine) {
                writer.write(s+"\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
