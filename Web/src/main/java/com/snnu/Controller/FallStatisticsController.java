package com.snnu.Controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.snnu.POJO.Contact;
import com.snnu.POJO.FallData;
import com.snnu.POJO.User;
import com.snnu.Service.FallStatisticsService;
import com.snnu.Utils.MailUtil;
import com.snnu.Utils.ReverseGeocoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.net.www.http.HttpClient;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class FallStatisticsController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpSession session;
    private String sessionId = null;

    @Autowired
    private FallStatisticsService fallStatService;
    @Autowired
    private ContactController contactController;
    @Autowired
    MailUtil mailUtil;
    //百度逆地理编码api
    private String url = "http://api.map.baidu.com/reverse_geocoding/v3/?ak=T8t2DwdWt8gQmFk9RQmO8DHGamU4NVfK&output=json&coordtype=wgs84ll&location=%s,%s";
    //静态图api
    private String staticUrl = "http://api.map.baidu.com/staticimage/v2?ak=T8t2DwdWt8gQmFk9RQmO8DHGamU4NVfK&center=%s,%s&width=800&height=550&zoom=17&markers=%s,%s";
    //调起地图
    private String linkUrl = "http://api.map.baidu.com/marker?location=%s,%s&title=摔倒位置&content=看护人：%s&output=html";


    @RequestMapping("/addFallData")
    @ResponseBody
    public int addFallData() {
        int uid = getUserInfo().getUid();
        int result = 1;
        if (uid != 0){
            FallData fallPoint = getFallPoint(null,null);
            fallPoint.setUid(uid);
            String address = ReverseGeocoding.getLocation(url, fallPoint.getLatitude(), fallPoint.getLongitude()).get("address");
            FallData fallData = new FallData(fallPoint.getUid(), new Date(), address, 1);
            result = fallStatService.addFallData(fallData);
        }

        return result;
    }

    @RequestMapping("/getAllFall")
    @ResponseBody
    public PageInfo<FallData> getAllFallData(@RequestParam(value = "pn", defaultValue = "1") Integer pn, int uid){
        //紧跟这条语句的就是分页查询
        PageHelper.startPage(pn, 10);
        List<FallData> list = fallStatService.getAllFallData(uid);

        /**
         * pageNum 当前页
         * pageSize 每页多少条数据
         * size 总页数
         * prePage 上一页
         * nextPage 下一页
         * isFirstPage 是否为首页
         * navigatepageNums 导航页码
         */

        PageInfo<FallData> info = new PageInfo<>(list, 6);
        System.out.println(list.toString());

        return info;
    }

    /**
     * 地图标记摔倒点模块
     */
    //获取摔倒位置
    @RequestMapping("/getPoint")
    @ResponseBody
    public FallData getFallPoint(@RequestParam(required = false) Double lon, @RequestParam(required = false) Double lat){
        FallData fallData = getUserInfo();
        if (fallData == null){
            return null;
        }
        /**
         * x范围：108.890899 108.904446
         * y范围：34.154333 34.163885
         */
        if (lon == null || lat == null){
            int min_x=890899;
            int max_x=904446;
            int min_y=154333;
            int max_y=163885;
            Random r = new Random();
            int x = r.nextInt(max_x - min_x) + min_x;
            int y = r.nextInt(max_y - min_y) + min_y;
            lon = 108.0 + x/1000000.0;
            lat = 34.0 + y/1000000.0;
        }

        System.out.println(lon + "   " + lat);
        /*fallData.setName("张三");
        fallData.setAge(56);*/
        fallData.setLongitude(lon);
        fallData.setLatitude(lat);

        //临时代码
        //session.setAttribute("fallData", fallData);

        return fallData;
    }

    //发送邮件
    @RequestMapping(value = "/sendMail")
    @ResponseBody
    public boolean sendFallMail(@RequestParam(required = false) Double lon, @RequestParam(required = false) Double lat){
        boolean result = false;
        //FallData fallData = (FallData) session.getAttribute("fallData");
        FallData fallData = getFallPoint(lon, lat);
        List<Contact> contacts = null;
        if (fallData == null){
            return result;
        }else {
            contacts = contactController.getContactsByUID(fallData.getUid());
        }
        Map<String, String> map = ReverseGeocoding.getLocation(url, fallData.getLatitude(), fallData.getLongitude());
        //System.out.println(map.toString());
        String content = "尊敬的用户，您所看护的 %s 于 %s 在 %s 附近发生摔倒事件，请您及时联系看护人并采取相关措施，愿一切安好！";
        content = String.format(content, fallData.getName(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), map.get("address"));
        linkUrl = String.format(linkUrl, fallData.getLatitude(),fallData.getLongitude(),fallData.getName());

        String picUrl = String.format(staticUrl, fallData.getLongitude(), fallData.getLatitude(),
                fallData.getLongitude(), fallData.getLatitude());
        //System.out.println(picUrl);
        try {
            //创建url
            URL url = new URL(picUrl);
            //打开链接
            URLConnection connection = url.openConnection();
            //创建输入流
            InputStream is = connection.getInputStream();
            // 1K的数据缓冲
            byte[] b = new byte[1024];
            // 读取到的数据长度
            int n;
            //输出流OutputStream os = new FileOutputStream("D:/static.png");
            //创建临时文件
            String filePath = System.getProperty("webapp.root") + "MapPNG" + File.separator;
            File dir = new File(filePath);
            File file = File.createTempFile("static", ".png", dir);
            OutputStream os = new FileOutputStream(file);
            //读数据
            while ((n = is.read()) != -1) {
                os.write(n);
            }
            os.close();
            is.close();
            //System.out.println(file.getPath());

            System.out.println("编辑邮件成功");
            for (Contact contact : contacts) {
                //"750211923@qq.com"
                result = mailUtil.sendFallMail(contact.getEmail(), content, file, linkUrl);

            }
            file.delete();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //修改误报记录，标记位flag置为0  id uid
    @RequestMapping("/deleteFall")
    @ResponseBody
    public PageInfo<FallData> updateFallData(Integer pn, FallData fallData){
        fallData.setFlag(0);
        fallStatService.updateFallData(fallData);
        PageInfo<FallData> allFallData = getAllFallData(pn, fallData.getUid());
        return allFallData;
    }

    //获取session中的用户信息（通过cookie）
    public FallData getUserInfo(){
        FallData fallData = new FallData();
        User userInfo = null;
        //系统默认cookie获取
        Cookie[] cookies = request.getCookies();
        sessionId = session.getId();
        for (Cookie c:cookies) {
            if ("JSESSIONID".equals(c.getName())){
                if (c.getValue().equals(sessionId)){
                    userInfo = (User) session.getAttribute(sessionId);
                }
            }
        }
        if (userInfo != null){
            fallData.setUid(userInfo.getUid());
            fallData.setName(userInfo.getUsername());
            fallData.setAge(userInfo.getAge());
        }
        return fallData;
    }

    //统计数据
    @RequestMapping(value = "/getFallStatistic")
    @ResponseBody
    public List<HashMap> getFallStatistic(int uid){
        List<HashMap> fallStatistic = fallStatService.getFallStatistic(uid);
        System.out.println(fallStatistic.toString());
        return fallStatistic;
    }

    //误报统计
    @RequestMapping(value = "/getSucErr")
    @ResponseBody
    public List<HashMap> getSucErr(int uid){
        return fallStatService.getSucErr(uid);
    }


    //测试ipv6
    @RequestMapping(value = "/getIPV6")
    @ResponseBody
    public String getIPV6(){
        String ipv6Address = null;
        try {
            ipv6Address = mailUtil.getIPV6Address();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ipv6Address;
    }
}
