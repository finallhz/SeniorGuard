package com.snnu.RunPython;

import java.io.IOException;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;



/**
 * 保持socket连接
 *
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint(value = "/webSocketChart")
public class webSocketChart {


    //socket连接会话，用于发送消息给客户端
    private Session session;


    /**
     * 客户端连接成功
     *
     * @param session
     * @throws IOException
     */
    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
        System.out.println("WebSocket连接成功");
//            logger.info("WebSocket - 连接成功");
    }


    /**
     * 收到消息时执行
     *
     * @param message
     * @param session
     * @throws IOException
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException,Exception {
        System.out.println("来自客户端的消息:" + message);
        this.sendMessage("success");
    }


    /**
     * 关闭时执行
     */
    @OnClose
    public void onClose() {
//            logger.info("webSocket - 连接关闭");
        System.out.println("webSocket连接关闭");
    }


    /**
     * 连接错误时执行
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
//            logger.error("webSocket - 出错：" + error.getMessage());
        System.out.println("webSocket出错" + error);
    }


    /**
     * 发送消息给客户端
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException,Exception {
        int i=0;
        while (true){
            List<String> strings = new ChartModel().sportState(i);
            String jsondata = strings.get(0);
            int lineCount = Integer.parseInt(strings.get(1));
            if (jsondata.equals("[]")){
                break;
            }
            this.session.getBasicRemote().sendText(jsondata);
            i = i + lineCount;
            Thread.sleep(2000);

        }


    }

}
