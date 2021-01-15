package com.snnu.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

/**
 * 不能使用websocket调用，socket为实时数据，sendmessage方法一直被占用，无法发送
 * 通过Controller，获取心跳和步数时发送uid
 * 保存时返回最后一条心跳和步数
 */
@Component
public class HeartAndStepUtil {
    @Autowired
    ApplicationContext ac;
    Properties heartAndStep ;

    public void saveProp(int uid, int heart, int step){
        heartAndStep = (Properties) ac.getBean("heartAndStep");
        try {
            String heartKey = uid+".heart";
            String stepKey = uid+".stepCount";
            String heartStr = (String) heartAndStep.get(heartKey);
            int lastindex = heartStr.lastIndexOf(",");
            String lastHeart = heartStr.substring(lastindex + 1);
            System.out.println(Integer.parseInt(lastHeart) + "      " + heart);
            if (Integer.parseInt(lastHeart) != heart){
                int oldindex = heartStr.indexOf(",");
                String updateHeartStr = heartStr.substring(oldindex+1);
                updateHeartStr += ","+heart;
                //System.out.println(updateHeartStr);
                heartAndStep.setProperty(heartKey, updateHeartStr);
            }


            String stepStr = (String) heartAndStep.get(stepKey);
            String[] stepArr = stepStr.split("<");
            String[] laststep = stepArr[stepArr.length - 1].split("%");
            String updatestep = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(new Date());

            if (laststep[0].equals(date)){
                laststep[1] = step+"";
                updatestep = laststep[0]+"%"+laststep[1];
            }else {
                updatestep = date+"%"+step;
                String[] tempArr = new String[stepArr.length+1];
                System.arraycopy(stepArr, 0, tempArr, 0, stepArr.length);
                stepArr = tempArr;
            }
            stepArr[stepArr.length - 1] = updatestep;

            //最后判断是否超过7个
            if (stepArr.length > 7){
                stepArr = Arrays.copyOfRange(stepArr, stepArr.length - 7, stepArr.length);
            }
            //System.out.println(Arrays.toString(stepArr));
            String stepResult = stepArr[0];
            for (int i = 1; i < stepArr.length; i++) {
                stepResult += "<"+stepArr[i];
            }
            //System.out.println(stepResult);
            heartAndStep.setProperty(stepKey, stepResult);

            OutputStream outputStream = new FileOutputStream(Thread.currentThread().getContextClassLoader().getResource("/").getPath()+"data/heartAndStepData.properties");
            heartAndStep.store(outputStream,"");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String getHeartAndStep(int uid){
        heartAndStep = (Properties) ac.getBean("heartAndStep");
        String heartKey = uid+".heart";
        String stepKey = uid+".stepCount";
        String heartStr = (String) heartAndStep.get(heartKey);
        String[] heartArr = heartStr.split(",");

        String stepStr = (String) heartAndStep.get(stepKey);
        String[] stepArr = stepStr.split("<");
        return Arrays.toString(heartArr)+";"+Arrays.toString(stepArr);
    }

    public void saveModelScore(String modelScore){

    }

}
