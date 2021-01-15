package com.snnu.Controller;

import com.snnu.Utils.HeartAndStepUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RecentDataController {
    @Autowired
    HeartAndStepUtil heartAndStepUtil;

    @RequestMapping("/getHeartAndStep")
    @ResponseBody
    public String getHeartAndStep(int uid){
        String heartAndStep = heartAndStepUtil.getHeartAndStep(uid);
        return heartAndStep;
    }

    @RequestMapping("/saveProp")
    @ResponseBody
    public void saveProp(int uid, int heart, int step){
        heartAndStepUtil.saveProp(uid, heart, step);
    }
}
