package com.snnu.RunPython;

import com.snnu.Utils.ExecutePython;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class ModelTrain {

    private static ExecutePython executePython;
    @Autowired
    public void setExecutePython(ExecutePython executePython){
        this.executePython = executePython;
    }

    String filePath = System.getProperty("webapp.root") + "pythonFile" + File.separator + "KNNTrainFea.py";
    String dataPath = System.getProperty("webapp.root") + "pythonFile" + File.separator + "NewDateTestShuf.csv";

    public String[] getModelScore(String path, String testPre, String crossTimes, String savePath){
        if (!"".equals(path)){
            dataPath = path;
        }
        String[] s = new String[]{"python", filePath, dataPath, testPre, crossTimes, savePath};
        List<String> result = executePython.exeCmd(s);
        String[] Kresult = new String[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Kresult[i] = result.get(i);
        }
        return Kresult;
    }
}
