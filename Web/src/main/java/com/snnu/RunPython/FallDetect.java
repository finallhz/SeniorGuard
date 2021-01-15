package com.snnu.RunPython;

import com.snnu.Utils.ExecutePython;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class FallDetect {
    private static ExecutePython executePython;
    @Autowired
    public void setExecutePython(ExecutePython executePython){
        this.executePython = executePython;
    }

    String filePath = System.getProperty("webapp.root") + "pythonFile" + File.separator + "judgeFall.py";

    public String fallDetect(String data){
        String[] s = new String[]{"python", filePath, data};
        List<String> result = executePython.exeCmd(s);
        String judge = result.get(0);
        return judge;
    }
}
