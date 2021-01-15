package com.example.utils;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pojo.UserData;
import com.example.testapplication.ContactActivity;
import com.example.testapplication.LoginActivity;
import com.example.utils.HttpLogger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.ByteString;

//用户数据管理类
//本类不应该成为Activity，但为了应用SharedPreferences简单，直接设置为安卓环境
public class UserDataManager {
        private static JSONObject json;
        //检查用户的登录名和密码，如果正确，返回用户的id值，如果不正确，返回-1;
        //private static String ipaddress = "http://10.150.198.72";
        //private static String ipaddress = "http://10.150.49.25";
        private static String ipaddress = "http://[2001:da8:270:2021::10e]";
        private static String port = ":8080/";
        private static String address =  "FallDetection/";

        //当前 WebSocket的信息
        public static String message = "";
        //下面是使用的所有参数属性
        static HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());

        //根据用户名和密码进行登录，这里的页面逻辑还没写完
        public  String findUserByNameAndPwd(String name, String pw)  {
                logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                String path = ipaddress+port+address+"login?username="+name+"&pwd="+pw;
                OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
                Request request = new Request.Builder().url(path)
                        .get().build();
                Call call = client.newCall(request);
                try {
                        Response response = call.execute();
                        String res = response.body().string();
                        if(res.equalsIgnoreCase("")||res==null)
                                return "";
                        else
                        {
                               return res;

                        }

                } catch (IOException e) {
                        e.printStackTrace();
                }
               return "";
        }

        //注销用户的登录状态，成功返回1，不成功返回-1;
        //这里不用和服务器进行交互了
        //应该清理所有跟用户相关的信息
        public static int deleteUserDatabyname(String name)  {
                ContactActivity.startnum=0;
                return 1;
        }


        //向后端服务器发送注册的信息，用户名和密码，成功返回1，失败返回-1
        public static int insertUserData(UserData ud)  {
                String path = ipaddress+port+address+"registerAndr?username="+ud.getUserName()+"&pwd="+ud.getUserPwd();
                OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
                Request request = new Request.Builder().url(path)
                        .get().build();
                Call call = client.newCall(request);
                try {
                        Response response = call.execute();
                        String res = response.body().string();
                        if(res.equalsIgnoreCase("用户名重复"))
                                return 1;
                        else  if(res.equalsIgnoreCase("数据库添加失败"))
                                return 2;
                        else  if(res.equalsIgnoreCase("成功"))
                                return 3;
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return -1;
        }

        //向后端服务器发送更新密码的信息，成功返回true，失败返回false
        public static boolean updateUserData(UserData ud)  {
                json = new JSONObject();
                try {
                        json.put("userName", ud.getUserName());
                        json.put("userPwd", ud.getUserPwd());
                } catch (JSONException e) {
                        e.printStackTrace();
                }
                String returnJson  = postJson("",json);
                return false;
        }

        //post方式请求数据，请求json
        private static String postJson(String url, JSONObject jsonOb)  {
                MediaType JSON = MediaType.parse("application/json;charset=utf-8");
                //申明给服务端传递一个json串
                //创建一个OkHttpClient对象
                OkHttpClient okHttpClient = new OkHttpClient();
                //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
                RequestBody requestBody = RequestBody.create(JSON, String.valueOf(jsonOb));
                //创建一个请求对象
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
                Response response = null;
                //发送请求获取响应
                try {
                        response =okHttpClient.newCall(request).execute();
                        //判断请求是否成功
                        if(response.isSuccessful()){
                                //打印服务端返回结果
                                Log.i("TAG",response.body().string());

                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }
                try {
                        return response.body().string();
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return "";
        }
        //获得用户的当前的消耗的能量
        public static String getEnergy()
        {
                String path = ipaddress+port+address+"getEnergy";
                OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
                Request request = new Request.Builder().url(path)
                        .get().build();
                Call call = client.newCall(request);
                try {
                        Response response = call.execute();
                        String res = response.body().string();
                        return res;
                } catch (IOException e) {
                        e.printStackTrace();
                }

                return "-1";
        }

        //获得用户的当前的联系人,返回的是请求的结果，解析放到Activity之中做
        public static String getContacts(String uid)
        {
                String path = ipaddress+port+address+"getContacts?uid="+uid;
                System.out.println("联系人访问地址:"+path);
                OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
                Request request = new Request.Builder().url(path)
                        .get().build();
                Call call = client.newCall(request);
                try {
                        Response response = call.execute();
                        String res = response.body().string();
                        System.out.println("返回联系人json:"+res);
                        if(res.equalsIgnoreCase("")||res==null)
                                return "";
                        else
                                return res;

                } catch (IOException e) {
                        e.printStackTrace();
                }

                return "";
        }

        public static String addContacts(String uid,String cname,String email,String phone)
        {
                String path = ipaddress+port+address+"addContact?uid="+uid+"&cname="+cname+"&email="+email+"&phone"+phone;
                System.out.println("添加联系人地址："+path);
                OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
                Request request = new Request.Builder().url(path)
                        .get().build();
                Call call = client.newCall(request);
                try {
                        Response response = call.execute();
                        String res = response.body().string();
                        System.out.println("添加联系人json:"+res);
                        if(res.equalsIgnoreCase("")||res==null)
                                return "";
                        else
                                return res;

                } catch (IOException e) {
                        e.printStackTrace();
                }

                return "";
        }
        public static String delContacts(String uid,String cid)
        {
                String path = ipaddress+port+address+"deleteContact?uid="+uid+"&cid="+cid;
                OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
                Request request = new Request.Builder().url(path)
                        .get().build();
                Call call = client.newCall(request);
                try {
                        Response response = call.execute();
                        String res = response.body().string();
                        if(res.equalsIgnoreCase("")||res==null)
                                return "";
                        else
                                return res;

                } catch (IOException e) {
                        e.printStackTrace();
                }

                return "";
        }

        public static String toServerList(String param)
        {
                //上传信息到服务器端，然后等待服务器端返回数据
                String path = ipaddress+port+address+"getFoodsNum?L="+param;
                OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
                Request request = new Request.Builder().url(path)
                        .get().build();
                Call call = client.newCall(request);
                try {
                        Response response = call.execute();
                        String res = response.body().string();
                        if(res.equalsIgnoreCase("")||res==null)
                                return "";
                        else
                                return res;

                } catch (IOException e) {
                        e.printStackTrace();
                }

                return "";

        }
        public static String toHeartBeatAndStep(String param)
        {
                if(param ==null)
                {
                        System.out.println("当前未登陆，默认id为1");
                        param = "1";
                }
                //上传信息到服务器端，然后等待服务器端返回数据
                String path = ipaddress+port+address+"getHeartAndStep?uid="+param;
                System.out.println("当前的访问路径"+path);
                OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
                Request request = new Request.Builder().url(path)
                        .get().build();
                Call call = client.newCall(request);
                try {
                        Response response = call.execute();
                        String res = response.body().string();
                        if(res.equalsIgnoreCase("")||res==null)
                                return "";
                        else
                                return res;

                } catch (IOException e) {
                        e.printStackTrace();
                }

                return "";
        }

        public static void toServerSocket()
        {
                OkHttpClient  client = new OkHttpClient.Builder()
                        .readTimeout(5, TimeUnit.SECONDS)
                        .build();
                Request request = new Request.Builder()
                        .url("ws://10.150.198.72:8080/FallDetection/ws/listen")
                        .build();
                WebSocket webSocket = client.newWebSocket(request, new WebSocketListener() {

                        @Override
                        public void onOpen(WebSocket webSocket, Response response) {
                                super.onOpen(webSocket, response);
                                System.out.println("与服务器建立连接onOpen！");
                                Log.i("socket","与服务器建立连接onOpen！");
                        }

                        @Override
                        public void onMessage(WebSocket webSocket, String text) {
                                super.onMessage(webSocket, text);
                                //System.out.println("接受到服务器的信息onMessage");
                                message = text;
                        }

                        @Override
                        public void onMessage(WebSocket webSocket, ByteString bytes) {
                                super.onMessage(webSocket, bytes);
                        }

                        @Override
                        public void onClosing(WebSocket webSocket, int code, String reason) {
                                super.onClosing(webSocket, code, reason);
                                System.out.println("正在关闭服务器的链接onClosing！");
                                Log.i("socket","正在关闭服务器的链接onClosing！");
                        }

                        @Override
                        public void onClosed(WebSocket webSocket, int code, String reason) {
                                super.onClosed(webSocket, code, reason);
                                System.out.println("已经关闭了服务器的链接onClosed！");
                                Log.i("socket","已经关闭了服务器的链接onClosed！");
                        }

                        @Override
                        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                                super.onFailure(webSocket, t, response);
                                System.out.println("链接服务器失败onFailure！");
                                Log.i("socket","链接服务器失败onFailure！");
                        }
                });

        }

}
