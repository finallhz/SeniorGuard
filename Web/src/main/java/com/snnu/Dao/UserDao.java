package com.snnu.Dao;

import com.snnu.POJO.User;

import java.util.List;

public interface UserDao {
    //登录
    User login(User user);
    //普通用户注册
    int addUser(User user);
    //查询用户名是否重复
    int getCountByUsername(String username);
    //修改密码
    int updatePwd(User user);
    //查询所有用户
    List<User> getAllUser();
    //根据用户名查找用户
    User getUserByUsername(String username);
    //用户信息修改
    int updateUser(User user);
    //用户删除
    int deleteUserByUID(int uid);
    //删除桥表中用户  获取桥表中与当前用户关联的cid，如果count=0，删除联系人
    int deleteUserRefCont(int uid);

    /**
     * 查询模块
     */
    //根据UID获取用户信息
    User getUserByUID(int uid);
    //根据用户名模糊查找
    List<User> getUsersByUsername(String username);
    //根据电话模糊查找
    List<User> getUsersByPhone(String phone);
    //根据邮箱模糊查找
    List<User> getUsersByEmail(String email);
    //根据地址模糊查找
    List<User> getUsersByAddr(String address);
    //根据uuid查询用户
    User getUserByUuid(String uuid);
    /**
     * 联系人查询
     */

}
