import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JavaRunPython {
    public static void main(String[] args) {
        String filePath = "E:\\test.py";
        int a = 5;
        int b = 10;
        String[] s = new String[]{"python", filePath, String.valueOf(a), String.valueOf(b)};
        try {
            Process process = Runtime.getRuntime().exec(s);
            //正常情况下，输出结果
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "gbk"));

            String line = null;
            while ((line = reader.readLine()) != null){
                System.out.println(line);
            }
            reader.close();

            //java代码中的process.waitFor()返回值为0表示我们调用python脚本成功，
            //返回值为1表示调用python脚本失败，这和我们通常意义上见到的0与1定义正好相反
            System.out.println(process.waitFor());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
