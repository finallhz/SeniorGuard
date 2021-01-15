package com.snnu.Utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class ReverseGeocoding {
    public static Map<String, String> getLocation(String urlProp, double lat, double lon){
        String bdURL = String.format(urlProp, lat, lon);
        Map<String, String> map = new HashMap<String, String>();
        //System.out.println(bdURL);
        try {
            URL url = new URL(bdURL);
            URLConnection connection = url.openConnection();
            /**
             * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。
             * 通过把URLConnection设为输出，你可以把数据向你个Web页传送。下面是如何做：
             */
            connection.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
            out.flush();
            out.close();
            // 一旦发送成功，用以下方法就可以得到服务器的回应：
            String res;
            InputStream l_urlStream;
            l_urlStream = connection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(l_urlStream, "UTF-8"));
            StringBuilder sb = new StringBuilder("");
            while ((res = in.readLine()) != null) {
                sb.append(res.trim());
            }
            String str = sb.toString();
            //System.out.println(str);


            if (str != null && str != "") {
                int addss = str.indexOf("country\":");
                int added = str.indexOf("\",\"country_code");
                if (addss > 0 && added > 0) {
                    String country = str.substring(addss + 10, added);
                    //System.out.println("国家：" + country);
                    map.put("country", country);
                }
                int addss0 = str.indexOf("formatted_address\":");
                int added0 = str.indexOf("\",\"business");
                if (addss > 0 && added > 0) {
                    String address = str.substring(addss0 + 20, added0);
                    //System.out.println("具体位置：" + address);
                    map.put("address", address);
                }
                int addss00 = str.indexOf("business\":");
                int added00 = str.indexOf("\",\"addressComponent");
                if (addss > 0 && added > 0) {
                    String business = str.substring(addss00 + 11, added00);
                    //System.out.println("街区：" + business);
                    map.put("business", business);
                }
                int addss1 = str.indexOf("province\":");
                int added1 = str.indexOf("\",\"city");
                if (addss1 > 0 && added1 > 0) {
                    String province = str.substring(addss1 + 11, added1);
                    //System.out.println("州市：" + province);
                    map.put("province", province);
                }
                int addss2 = str.indexOf("city\":");
                int added2 = str.indexOf("\",\"city_level");
                if (addss2 > 0 && added2 > 0) {
                    String city = str.substring(addss2 + 7, added2);
                    //System.out.println("城市：" + city);
                    map.put("city", city);
                }
                //System.out.println(map.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
