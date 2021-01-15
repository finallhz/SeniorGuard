package com.snnu.Controller;

import com.aliyuncs.exceptions.ClientException;
import com.snnu.Utils.AliyunMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@Controller
public class SendMessageController {
    @Autowired
    private HttpSession session;

    @RequestMapping("/sendCode")
    @ResponseBody
    public Map<String, Object> sendIdentifyingCode(String phoneNum){
        Map<String, Object> resultMap = new HashMap<>();
        int state = -1;

        //验证码生成,赋予局部变量
        int identifyingCode = AliyunMessageUtil.getIdentifyingCode();
        try {
            String s = AliyunMessageUtil.sendIdentifyingCode(phoneNum);
            if ("OK".equals(s)){
                state = 1;

                //TimerTask实现5分钟后从session中删除code
                session.setAttribute(phoneNum, identifyingCode);
                final Timer timer = new Timer();
                final String phoneN = phoneNum;
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        session.removeAttribute(phoneN);
                        System.out.println("验证码从session中删除");
                    }
                },5*60*1000);
                resultMap.put("state", state);
                resultMap.put("msg", "发送成功");
            }else {
                resultMap.put("state", state);
                resultMap.put("msg", "发送失败,请重新发送");
            }
        } catch (ClientException e) {
            e.printStackTrace();
            resultMap.put("state", state);
            resultMap.put("msg", "发送失败,请重新发送");
        }
        return resultMap;
    }

    @RequestMapping("/verify")
    @ResponseBody
    public int verifyIdentifyingCode(String phoneNum, String code){
        int state = 0;
        Object identifyingCode = session.getAttribute(phoneNum);
        if (code != null && identifyingCode.toString().equals(code)){
            state = 1;
        }
        return state;
    }
}
