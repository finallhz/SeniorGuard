package com.snnu.Utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;

import java.util.Map;
import java.util.Random;

public class AliyunMessageUtil {
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";
    //阿里云api版本
    static final String version = "2017-05-25";
    //短信操作接口类型（验证码短信）
    static final String msgType = "SendSms";
    //  此处需要替换成开发者自己的AK(遵循阿里云规范，创建子用户ak)
    static final String accessKeyId = "#################";
    static final String accessKeySecret = "#################";
    //  短信签名
    static final String signName = "SG验证密码";
    //  短信模板code(根据需要,注册、密码重置可用不同的模板code,阿里云访问控制台设置多套模板)
    static final String identityTemplateCode = "SMS_204981171";// 验证码模板

    // 随机生成验证码(六位数)
    private static int identifyingCode;

    public static int getIdentifyingCode() {
        return identifyingCode = new Random().nextInt(900000) + 100000;
        //每次调用生成一次六位数的随机数
    }

    //发送验证码
    public static String sendSms(String phoneNumber, String templateParam) throws ClientException {
        // 设置公共请求参数，初始化Client
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        CommonRequest request = new CommonRequest();
        /**
         * 系统变量，固定
         */
        request.setSysDomain(domain);
        request.setSysVersion(version);
        request.setSysAction(msgType);
        //必填:接收短信的手机号码
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        //必填:短信签名名称。请在控制台签名管理页面签名名称一列查看（必须是已添加、并通过审核的短信签名）
        request.putQueryParameter("SignName", signName);
        //必填:短信模板ID
        request.putQueryParameter("TemplateCode", identityTemplateCode);
        //可选:短信模板变量对应的实际值，JSON格式。模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //"{\"name\":\"Tom\", \"code\":\"123\"}";
        request.putQueryParameter("TemplateParam", templateParam);

        CommonResponse commonResponse = client.getCommonResponse(request);
        //下面是打印日志信息，以及获取发送短信的回执ID，方便对发送详情进行查询
        String data = commonResponse.getData();
        String sData = data.replaceAll("'\'", "");
        log_print("sendSms", sData);
        Gson gson = new Gson();
        Map map = gson.fromJson(sData, Map.class);
        Object bizId = map.get("BizId");
        Object message = map.get("Message");
        return message.toString();

    }
    private static void log_print(String functionName, Object result) {
        Gson gson = new Gson();
        System.out.println("-------------------------------" + functionName + "-------------------------------");
        System.out.println(gson.toJson(result));
    }

    /**
     * 修改密码发送验证码短信（templateParam 根据模板需要传入的参数进行json格式拼接）
     * @param phoneNumber
     * @throws ClientException
     * @throws InterruptedException
     */
    public static String sendIdentifyingCode(String phoneNumber) throws ClientException {
        String templateParam = "{\"code\":\"" + identifyingCode + "\"}";
        return sendSms(phoneNumber, templateParam);
    }

    public static void main(String[] args) {
        try {
            getIdentifyingCode();
            String s = AliyunMessageUtil.sendIdentifyingCode("18629526037");
            System.out.println(s);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
