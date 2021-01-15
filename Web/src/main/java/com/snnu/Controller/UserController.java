package com.snnu.Controller;

import com.snnu.POJO.User;
import com.snnu.Service.UserService;
import com.snnu.Utils.MailUtil;
import com.snnu.Utils.RandomUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    User loginUser;
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MailUtil mailUtil;

    private String sessionId = null;

    /**
     * 用户登录
     *
     * @param username
     * @param pwd
     * @return
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public User login(@RequestParam(value = "username") String username,
                     @RequestParam(value = "pwd") String pwd,
                     HttpServletResponse response) {
        int result = 0;
        //判断是邮箱还是手机号
        String em = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        String ph = "^1[3|4|5|7|8][0-9]\\d{4,8}$";

        //系统默认cookie获取
        Cookie[] cookies = request.getCookies();
        sessionId = session.getId();


//        System.out.println("em" + username.matches(em));
//        System.out.println("ph" + username.matches(ph));
        loginUser = new User();
        loginUser.setPwd(pwd);
        if (username.matches(em)) {
            loginUser.setEmail(username);
        } else if (username.matches(ph)) {
            loginUser.setPhone(username);
        } else {
            loginUser.setUsername(username);
        }
//        System.out.println("userController输出"+userService);
        //System.out.println(loginUser);
        User login = userService.login(loginUser);
        /*ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpSession session = attributes.getRequest().getSession();*/

        if (login == null) {
            return null;
        }

        session.setAttribute(sessionId, login);

        /*for (Cookie c:cookies) {
            System.out.println("cookie:" + c.getName());
            System.out.println("sessionId:" + sessionId);
            if ("JSESSIONID".equals(c.getName())){
                if (c.getValue().equals(sessionId)){
                    c.setValue(c.getValue()+"&username=" + username + "&role=" +login.getRole());
                    System.out.println("从cookie中获取到了session");
                    response.addCookie(c);
                }
            }
        }*/

        Cookie usernameCookie = new Cookie("usernameCookie", username);
        Cookie uidCookie = new Cookie("uidCookie", login.getUid()+"");
        Cookie roleCookie = new Cookie("roleCookie", login.getRole() + "");
        //告诉浏览器在浏览哪些路径的时候要是cookie持久化保存.(“/”)表示的是访问当前工程下的所有webApp都会产生cookie
        /*usernameCookie.setPath("/");
        roleCookie.setPath("/");*/
        response.addCookie(usernameCookie);
        response.addCookie(uidCookie);
        response.addCookie(roleCookie);


//        System.out.println(login);
        /*System.out.println("开始取session");
        System.out.println(session.getAttribute("loginUser"));*/
        return login;
    }


    /**
     * 用户名重复，有重复返回true
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/isExist")
    @ResponseBody
    public boolean isExist(@RequestParam(value = "username") String username) {
        return userService.getCountByUsername(username);
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/register")
    @ResponseBody
    public String register(User user) {
        if (isExist(user.getUsername())) {
            return "失败";
        }

        String uuid = RandomUUID.getRandomUUID();
        user.setRole(0);
        user.setUuid(uuid);

        /*long age = ((new Date()).getTime() - user.getBirthdate().getTime())/(24 * 60 * 60 * 1000);
        age = age/365 ;
        user.setAge(age);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            user.setBirthdate(sdf.parse("2010-01-01"));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int currYear = calendar.get(Calendar.YEAR);
        calendar.setTime(user.getBirthdate());
        int birthYear = calendar.get(Calendar.YEAR);
        user.setAge(currYear - birthYear);
        System.out.println("注册USER：" + user.toString());
        int i = userService.addUser(user);
        return i == 0 ? "失败" : "成功";
    }

    @RequestMapping(value = "/registerAndr", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String registerAndr(User user) {
        if (isExist(user.getUsername())) {
            return "用户名重复";
        }
        String uuid = RandomUUID.getRandomUUID();
        user.setRole(0);
        user.setUuid(uuid);

        int i = userService.addUser(user);
        return i == 0 ? "数据库添加失败" : "成功";
    }

    /**
     * 绑定邮箱，这部分之后要添加到注册中
     * 绑定邮箱后，会生成uuid，注册时uuid为空
     */
    @RequestMapping("/bindingEmail")
    @ResponseBody
    public String bindingEmail(){
        //注册时生成的uuid String uuid = RandomUUID.getRandomUUID();这里为了测试，先从数据库获取一个已注册的
        User user = null;
        for (int i = 1; i <= 5; i++) {
            user = userService.getUserByUID(i);
            System.out.println("bindingEmail方法：" + user.toString());
            if (user.getUuid() != null) break;
        }
        String uuid = user.getUuid();
        boolean result = mailUtil.sendMail("750211923@qq.com", uuid);
        return String.valueOf(result);
    }

    @RequestMapping("/registerActive/{uuid}")
    @ResponseBody
    public int registerActive(@PathVariable("uuid") String uuid){
        int result = 0;
        if (uuid != null){
            //根据uuid查询用户
            User user = userService.getUserByUuid(uuid);
            user.setUuid("");
            result = userService.updateUser(user);
        }
        return result;
    }

    /**
     * 管理员注册
     *
     * @param user
     * @return
     */
    @RequestMapping("/registerAdmin")
    @ResponseBody
    public String registerAdmin(User user) {
        if (isExist(user.getUsername())) {
            return "失败";
        }
        String uuid = RandomUUID.getRandomUUID();
        user.setRole(1);
        user.setUuid(uuid);

        int i = userService.addUser(user);
        return i == 0 ? "失败" : "成功";
    }

    /**
     * 重置密码
     * 1.输入用户名  判断用户是否存在，存在则返回手机号  /getUserTEL
     * 2.向手机发送验证码，通过后跳转重置密码界面   /sendCode 发送验证码   /verify 验证
     * 3.更新密码信息  /updatePwd
     */
    @RequestMapping("/getUserTEL")
    @ResponseBody
    public String getUserTEL(String username){
        User user = userService.getUserByUsername(username);
        if (user != null){
            return user.getPhone();
        }else {
            return null;
        }
    }

    @RequestMapping("/updatePwd")
    @ResponseBody
    public int updatePwd(User user) {
        return userService.updatePwd(user);
    }

    /**
     * 用户注销
     */
    @RequestMapping(value = "/logout")
    public int logout(HttpServletResponse response) {
        int flag = 0;
        try{
            Cookie[] cookies = request.getCookies();
            for (Cookie c : cookies) {
                if ("JSESSIONID".equals(c.getName())){
                    String sessionId = c.getValue();
                    session.removeAttribute(sessionId);
                    session.invalidate();
                }
                c.setMaxAge(0);
                response.addCookie(c);
            }
            flag = 1;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return flag;
        }
    }

    /**
     * 用户名模糊查询
     */
    @RequestMapping(value = "/findUsers")
    @ResponseBody
    public List<User> getUsersByUsername(String content, String searchType) {
        List<User> list = null;
        switch (searchType){
            case "username":
                list = userService.getUsersByUsername(content);
                break;
            case "phone":
                list = userService.getUsersByPhone(content);
                break;
            case "email":
                list = userService.getUsersByEmail(content);
                break;
            case "address":
                list = userService.getUsersByAddr(content);
                break;
        }

        return list;
    }

    /**
     * 管理员发送邮件
     */
    @RequestMapping(value = "/sendCustomMail")
    @ResponseBody
    public boolean sendCustomMail(String to, String subject ,String content){
        boolean result = mailUtil.sendCustomMail(to, subject, content);
        return result;
    }

    /**
     * 管理员查询所有用户
     */
    @RequestMapping(value = "/getAllUser")
    @ResponseBody
    public List<User> getAllUser(){
        List<User> allUser = userService.getAllUser();
        return allUser;

    }

    /**
     * 删除用户
     */
    @RequestMapping(value = "/deleteUserByUID")
    @ResponseBody
    public List<User> deleteUserByUID(int uid){
        List<User> users = userService.deleteUserByUID(uid);
        return users;
    }

    /**
     * 用户信息修改
     */
    @RequestMapping(value = "/updateUser")
    @ResponseBody
    public int updateUser(User user, HttpServletRequest request, HttpServletResponse response) {
        int flag = 0;
        int updateResult = userService.updateUser(user);
        if(updateResult == 1){
            Cookie[] cookies = request.getCookies();
            if (cookies != null){
                for (int i = 0; i < cookies.length; i++) {
                    if ("usernameCookie".equals(cookies[i].getName())){
                        cookies[i].setValue(user.getUsername());
                        response.addCookie(cookies[i]);
                        flag = 1;
                    }
                }
            }
        }

        return flag;
    }


    //根据UID获取用户信息
    @RequestMapping(value = "/getUserByUID")
    @ResponseBody
    public User getUserByUID(int uid) {
        return userService.getUserByUID(uid);
    }

    /**
     * 临时方法
     * 更新所有用户uuid
     *
     */
    @RequestMapping("/tempUpd")
    public void tempUpdateUuid(){
        List<User> allUser = userService.getAllUser();
        for (User user : allUser) {
            String uuid = RandomUUID.getRandomUUID();
            user.setUuid(uuid);
            userService.updateUser(user);
        }
    }

}
