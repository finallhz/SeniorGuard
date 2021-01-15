package com.snnu.Controller;

import com.snnu.POJO.Contact;
import com.snnu.POJO.User;
import com.snnu.Service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ContactController {
    @Autowired
    private ContactService contactService;
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/addContact")
    @ResponseBody
    public int addContact(Contact contact){
        //Contact contact = new Contact(1,"李明","3038375568@qq.com","12312312312");
        //System.out.println("进入addContact方法");
        //contact.setUid(1);
        int result = 0;
        /*User user = (User)session.getAttribute("loginUser");
        if (user == null) return result;*/
        //System.out.println(contact.toString());
        if (contact.getUid() == 0){
            contact.setUid(getUserInfo().getUid());
        }
        result = contactService.addContact(contact);
        System.out.println("addContact:" + result);
        return result;
    }

    @RequestMapping("/getContacts")
    @ResponseBody
    public List<Contact> getContactsByUID(int uid){
        List<Contact> contacts = contactService.getContactsByUID(uid);
        //System.out.println("getContactsByUID:" + contacts.toString());
        return contacts;
    }

    @RequestMapping("/updateContact")
    @ResponseBody
    public List<Contact> updateContact(Contact contact){
        List<Contact> contacts = contactService.updateContact(contact);
        return contacts;
    }

    @RequestMapping("/getContByName")
    @ResponseBody
    public List<Contact> getContactByCname(Contact contact){
        List<Contact> contacts = contactService.getContactByCname(contact);
        return contacts;
    }

    @RequestMapping("/deleteContact")
    @ResponseBody
    public List<Contact> deleteContact(Contact contact){
        return contactService.deleteContact(contact);
    }

    //获取session中的用户信息（通过cookie）
    public User getUserInfo(){
        User userInfo = null;

        //系统默认cookie获取
        Cookie[] cookies = request.getCookies();
        String sessionId = session.getId();
        for (Cookie c:cookies) {
            if ("JSESSIONID".equals(c.getName())){
                if (c.getValue().equals(sessionId)){
                    userInfo = (User) session.getAttribute(sessionId);
                }
            }
        }
        return userInfo;
    }
}
