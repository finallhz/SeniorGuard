package com.snnu.RunPython;

import com.snnu.Utils.ExecutePython;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Component
public class EnergyConsume {
    private static ExecutePython executePython;
    @Autowired
    public void setExecutePython(ExecutePython executePython){
        this.executePython = executePython;
    }


    String filePath = System.getProperty("webapp.root") + "pythonFile" + File.separator + "energy02.py";
    //String dataPath = "E:\\赛尔\\data flush\\new\\2020-12-15.csv";

    public String energyValue(String dataPath, String endtime, String weight){
        String[] s = new String[]{"python", filePath, dataPath, endtime ,weight};
        List<String> result = executePython.exeCmd(s);
        String evalue = result.get(0);
        return evalue;
    }
}
