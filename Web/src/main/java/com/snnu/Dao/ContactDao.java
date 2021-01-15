package com.snnu.Dao;

import com.snnu.POJO.Contact;

import java.util.List;

public interface ContactDao {
    //添加紧急联系人
    int addContact(Contact contact);
    //查询当前用户下所有联系人
    List<Contact> getContactsByUID(int uid);
    //添加用户和联系人关联信息
    int addUserRefCont(Contact contact);
    //根据联系人信息查询id
    Integer getContactID(Contact contact);
    //修改联系人信息
    int updateContact(Contact contact);
    //根据姓名模糊查找
    List<Contact> getContactByCname(Contact contact);
    //删除联系人信息
    int deleteContact(int cid);
    //删除用户和联系人关联信息
    int deleteUserRefCont(Contact contact);
    //查询UserRefCont中是否还有cid
    int getCidOnURC(int cid);
}
