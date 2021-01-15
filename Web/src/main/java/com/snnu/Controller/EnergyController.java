package com.snnu.Controller;

import com.snnu.RunPython.EnergyConsume;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class EnergyController {
    //String dataPath = "G:\\FallDetectionData\\";;
    String dataPath = "/dataForEnergy";
    String endTime;
    String weight;

    @RequestMapping(value = "/getEnergy")
    @ResponseBody
    public String getEnergy(){
        /*long currentTime = System.currentTimeMillis()/1000;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String filename = sdf.format(new Date(currentTime));*/
        String filename = "2020-12-08";
        long currentTime = 1607418200;
        dataPath = filename + ".csv";
        endTime = String.valueOf(currentTime);
        weight = 70+"";
        String eValue = new EnergyConsume().energyValue(dataPath, endTime, weight);
        System.out.println(eValue);
        return eValue;
    }
}
