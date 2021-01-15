package com.snnu.WebSocket;

import com.alibaba.fastjson.JSONArray;
import com.snnu.Dao.UserDao;
import com.snnu.RunPython.FallDetect;
import com.snnu.Utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.*;

import com.alibaba.fastjson.JSONObject;

@Component
@ServerEndpoint("/ws/listen")
public class ListenWebSocket implements Runnable {

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    //private static CopyOnWriteArraySet<ListenWebSocket> listenWebSocketSet = new CopyOnWriteArraySet<ListenWebSocket>();

    //WebSocket前后一一映射
    private static Map<String, Session> socket = new HashMap<String, Session>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;


    private static ExecutePython executePython;

    @Autowired
    public void setExecutePython(ExecutePython executePython) {
        ListenWebSocket.executePython = executePython;
    }

    private HeartAndStepUtil heartAndStepUtil = new HeartAndStepUtil();

    private Data sportdata;

    public void setSportdata(Data sportdata) {
        this.sportdata = sportdata;
    }

    @Autowired
    private UserDao user1;
/*
    String filePath = System.getProperty("webapp.root") + "pythonFile" + File.separator + "webDataJson.py";
    String dataPath = System.getProperty("webapp.root") + "dataset"+ File.separator +"webData1.csv";*/

    private String uuid;

    public int flag = 0;

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 客户端连接成功
     *
     * @param session
     * @throws IOException
     */
    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
        String param = session.getQueryString();//获取websocket传递的url中的参数信息
        this.setUuid("暂定");
        socket.put(this.uuid, session);
        System.out.println("WebSocket连接成功");
//            logger.info("WebSocket - 连接成功");
    }


    /**
     * 收到消息时执行
     * 根据点击的不同页面，传递不同的参数
     * 默认：传递加速度、角速度
     * 1：传递心跳、步数等实时数据，并传递最近七条数据
     * 2. 关闭心跳、步数页面时，保存当时的心跳和步数
     *
     * @param message
     * @param session
     * @throws IOException
     */
    @OnMessage
    public void onMessage(int message, Session session) throws IOException, Exception {
        System.out.println("来自客户端的消息:" + message);
        this.sendMessage("success");
    }


    /**
     * 关闭时执行
     */
    @OnClose
    public void onClose() {
//            logger.info("webSocket - 连接关闭");
        socket.remove(this.uuid);
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
        error.printStackTrace();
    }


    /**
     * 发送消息给客户端
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        Session sess = socket.get("暂定");
        if (sess != null) {
            //sess.getAsyncRemote().sendText(message);
            sess.getBasicRemote().sendText(message);
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true){
            try {
                if (sportdata != null) {
                    String s = sportdata.removeData();
                    //System.out.println("ListenWebSocket:" + s);
                    Thread.sleep(1000);
                    if (s != null) {
                        this.sendMessage(s);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /*//准备空包
        byte[] data = new byte[10240];
        DatagramPacket packet = new DatagramPacket(data, data.length);

        //准备socket
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(8081);
            String string = "";//用于存放数据内容
            *//*StringBuffer sb = new StringBuffer();//每100条数据进行一次摔倒模型检测
            int columnCount = 0;//记录sb中有多少条数据*//*
            List<String> list = new ArrayList<String>();//每100条数据进行一次摔倒模型检测

            while (true) {
                //接收数据包
                socket.receive(packet);//数据格式 uid,数据
                string = new String(packet.getData(), 0, packet.getLength());
                String[] strArray = string.trim().split("\n");
                //记录当前状态  默认未摔倒
                int status = 0;
                if (list.size() + strArray.length >= 100) {
                    int needCount = 100 - list.size();
                    String[] subStrArray = Arrays.copyOfRange(strArray, 0, needCount);
                    //System.out.println(subStrArray.length);
                    Collections.addAll(list, subStrArray);
                    //此时list已经获取100条数据，需要执行python
                    //System.out.println(list.toString());
                    //判断是否摔倒
                    String judge = new FallDetect().fallDetect(list.toString());
                    status = Integer.parseInt(judge);
                    list.clear();
                    String[] remainArray = Arrays.copyOfRange(strArray, needCount, 30);
                    Collections.addAll(list, remainArray);

                } else {
                    Collections.addAll(list, strArray);
                    //System.out.println(list.size());
                }

                //将每秒获取到的数据转化为echart数值
                List<SportData> list1 = SportDataProc.dataProc(strArray);

                //获取当前心跳
                int heart = SportDataProc.currHeart(strArray);
                //获取当前步数
                int stepCount = SportDataProc.currStep(strArray);

                DataUtil dataUtil = new DataUtil(list1, status, heart, stepCount);
                //System.out.println(dataUtil);

                String jsonString = JSONObject.toJSONString(dataUtil);
                //System.out.println(jsonString);

                //this.sendMessage(jsonString);


                *//*dataUtil.setUserID(strArray[0]);//获取用户ID
                float[] numArray = new float[strArray.length-1];//178个采集数据
                for(int i =0; i < numArray.length; i++){
                    numArray[i] = Float.parseFloat(strArray[i+1]);
                }
                dataUtil.setEcgs(numArray);//获取ECG数据
                int result = predict.tensorPredict(numArray);//利用tensorflow模型预测当前发病状态
                dataUtil.setFlush(false);
                if(result == 1){
                    //数据库添加新纪录
                    Exrecord exrecord = new Exrecord();
                    exrecord.setPatient(patientService.findByPid(strArray[0]));
                    exrecord.setIsConfirmed(false);
                    exrecord.setTime(new Timestamp(new Date().getTime()));
                    exrecordService.saveExrecord(exrecord);
                    dataUtil.setFlush(true);
                }
                dataUtil.setStatus(result);//获取发病状态

                JSONObject json = JSONObject.fromObject(dataUtil);
                String jsonString = json.toString();

                //输出
//				System.out.println("获取数据：" + strArray.length + "-" + string);
                System.out.println("传输的JSON数据为：" + jsonString);
                System.out.println("用户ID为：" + strArray[0]);

                User user = userService.findByUid(strArray[0]);
                if (user.getRole() == 0) {
                    String did = dandpService.findDidByPid(strArray[0]);//查找当前患者对应医生编号
                    if(did != null || !"".equals(did)){
                        DoctorRealTimeUtil.setPatientCurrentStatus(did, strArray[0], String.valueOf(result));//激活用户实时在线
//						DoctorRealTimeUtil.setCurrentStatus(strArray[0], String.valueOf(result));//存储实时状态
                        DoctorRealTimeUtil.reduce();//执行激活期衰弱
                        this.sendMessage(jsonString, did);
                    }
                    this.sendMessage(jsonString, strArray[0]);
                }else {
                    this.sendMessage(jsonString, strArray[0]);
                }*//*

//				for(ListenWebSocket item : listenWebSocketSet){
//					item.sendMessage(jsonString);
//				}
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }*/
//-------------------------------------------------------------------------------------------
        /*try {
            String[] s = new String[]{"python", filePath, dataPath};
            List<String> list = executePython.exeCmd(s);
            System.out.println("run方法：" + list);
            System.out.println(list.get(1));
            while (true){
                if (socket.get("暂定") != null) {
                    for (int i = 0; i < list.size(); i++) {
                        //Thread.sleep((list.get(i).length()/20)*1000);
                        this.sendMessage(list.get(i));
                        JSONArray objects = JSONObject.parseArray(list.get(i));
                        int time = (int)(objects.size() / 20.0) * 1000;
                        Thread.sleep(time);
                    }
                    break;
                }
                //System.out.println("----------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

}
