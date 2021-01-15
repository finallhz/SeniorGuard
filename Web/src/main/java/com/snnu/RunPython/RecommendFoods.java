package com.snnu.RunPython;

import com.snnu.Utils.ExecutePython;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.List;


@Component
public class RecommendFoods {

    private static ExecutePython executePython;
    @Autowired
    public void setExecutePython(ExecutePython executePython){
        this.executePython = executePython;
    }


    String filePath = System.getProperty("webapp.root") + "pythonFile" + File.separator + "tuijian02.py";
    String dataPath = System.getProperty("webapp.root") + "dataset"+ File.separator +"foods160.csv";


    public int[] foodsNum(String L){
        String[] s = new String[]{"python", filePath, L, dataPath};
        List<String> result = executePython.exeCmd(s);
        String num = result.get(0);
        if (num.equals("[]")){
            return null;
        }
        String[] numSArr = num.replace("[", "").replace("]", "").replace(" ", "").replace("'", "").split(",");
        int[] numIArr = new int[numSArr.length];
        for (int i = 0; i < numSArr.length; i++) {
            numIArr[i] = Integer.parseInt(numSArr[i]);
        }
        //System.out.println(Arrays.toString(numIArr));
        return numIArr;
    }

}
