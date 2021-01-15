package com.snnu.Service;

import com.snnu.Dao.ContactDao;
import com.snnu.Dao.UserDao;
import com.snnu.POJO.Contact;
import com.snnu.POJO.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//用户业务逻辑层
@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private ContactDao contactDao;

    //用户登录业务
    public User login(User user) {
        return userDao.login(user);
    }

    //用户注册业务
    public int addUser(User user) {
        return userDao.addUser(user);
    }

    //管理员注册业务
    public int addAdmin(User user) {
        return userDao.addUser(user);
    }


    //判断用户名重复业务
    public boolean getCountByUsername(String username) {
        return userDao.getCountByUsername(username) > 0 ? true : false;
    }

    //重置密码
    public int updatePwd(User user) {
        return userDao.updatePwd(user);
    }

    //查询所有用户
    public List<User> getAllUser() {
        return userDao.getAllUser();
    }


    //根据用户名查找用户
    public User getUserByUsername(String username) {
        User user = userDao.getUserByUsername(username);
        return user;
    }

    //用户信息修改
    public int updateUser(User user) {
        return userDao.updateUser(user);
    }


    //用户删除
    public List<User> deleteUserByUID(int uid) {
        List<Contact> contacts = contactDao.getContactsByUID(uid);
        //删除桥表用户
        userDao.deleteUserRefCont(uid);
        //删除用户表用户
        userDao.deleteUserByUID(uid);
        for ( Contact contact : contacts ) {
            //查询桥表中 cid联系人是否还监护其他用户
            int count = contactDao.getCidOnURC(contact.getCid());
            if (count == 0){
                contactDao.deleteContact(contact.getCid());
            }
        }
        return userDao.getAllUser();
    }

    /**
     * 查询模块
     */
    //根据UID获取用户信息
    public User getUserByUID(int uid) {
        return userDao.getUserByUID(uid);
    }

    //根据用户名模糊查找
    public List<User> getUsersByUsername(String username) {
        return userDao.getUsersByUsername(username);
    }

    //根据电话模糊查找
    public List<User> getUsersByPhone(String phone) {
        return userDao.getUsersByPhone(phone);
    }

    //根据邮箱模糊查找
    public List<User> getUsersByEmail(String email) {
        return userDao.getUsersByEmail(email);
    }

    //根据地址模糊查找
    public List<User> getUsersByAddr(String address) {
        return userDao.getUsersByAddr(address);
    }

    //根据uuid查询用户
    public User getUserByUuid(String uuid) {
        return userDao.getUserByUuid(uuid);
    }
}
