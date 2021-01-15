package com.snnu.Controller;

import com.snnu.RunPython.ModelTrain;
import com.snnu.Utils.HeartAndStepUtil;
import com.snnu.WebSocket.DataProcess;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

@Controller
public class ModelTrainController {

    @Autowired
    ApplicationContext ac;
    Properties heartAndStep ;
    @Autowired
    HeartAndStepUtil heartAndStepUtil;
    //获取现有模型准确度
    @RequestMapping("/getCurrScore")
    @ResponseBody
    public String getCurrScore(){
        heartAndStep = (Properties) ac.getBean("heartAndStep");
        String modelScore = heartAndStep.getProperty("modelScore");
        return modelScore;
    }

    //获取新训练模型准确度  通过外部上传文件
    @RequestMapping("/getModelScore")
    @ResponseBody
    public String[] getModelScore(MultipartFile filePath, String testPre, String crossTimes){
        String[] score = null;
        String savePath = System.getProperty("webapp.root") + "pythonFile" + File.separator;
        //文件保存
        if (StringUtils.isNotBlank(filePath.getOriginalFilename())){
            try {
                String filename = filePath.getOriginalFilename();
                String path = System.getProperty("webapp.root") + "dataset" + File.separator;

                File file = new File(path + filename);
                filePath.transferTo(file);
                score = new ModelTrain().getModelScore(file.getPath(), testPre, crossTimes, savePath);
                file.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            score = new ModelTrain().getModelScore("", testPre, crossTimes, savePath);
        }
        return score;
    }

    //替换模型
    @RequestMapping("/replaceModel")
    @ResponseBody
    public boolean replaceModel(String modelScore){
        String path = System.getProperty("webapp.root") + "pythonFile" + File.separator;
        File newModel = new File(System.getProperty("webapp.root") + "pythonFile" + File.separator + "model" +File.separator + "feaDataModel.pkl");
        if (!newModel.exists()){
            return false;
        }
        Path path1 = null;
        try {
            path1 = Files.move(Paths.get(path + "model" + File.separator + "feaDataModel.pkl"), Paths.get(path + "feaDataModel.pkl"),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(path1);
        if (!"".equals(path1)){
            heartAndStep = (Properties) ac.getBean("heartAndStep");
            heartAndStep.setProperty("modelScore", modelScore);
            OutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(Thread.currentThread().getContextClassLoader().getResource("/").getPath()+"data/heartAndStepData.properties");
                heartAndStep.store(outputStream,"");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }else {
            return false;
        }
    }
}
