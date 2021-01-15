package com.snnu.Service;

import com.snnu.Dao.ContactDao;
import com.snnu.POJO.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    @Autowired
    private ContactDao contactDao;

    /**
     * 添加紧急联系人
     * @param contact
     * @return
     */
    public int addContact(Contact contact){
        int result = 0;
        Integer cid = contactDao.getContactID(contact);
        System.out.println("cid:" + cid);
        if (cid != null) {
            contact.setCid(cid);
        }else {
            contactDao.addContact(contact);
        }
        System.out.println(contact.toString());
        result = contactDao.addUserRefCont(contact);
        return result;
    }

    /**
     * 查询当前用户下所有联系人
     */
    public List<Contact> getContactsByUID(int uid){
        return contactDao.getContactsByUID(uid);
    }

    //修改联系人信息
    public List<Contact> updateContact(Contact contact){
        int result = contactDao.updateContact(contact);
        List<Contact> contacts = getContactsByUID(contact.getUid());
        return contacts;
    }

    //根据姓名模糊查找
    public List<Contact> getContactByCname(Contact contact){
        List<Contact> contacts = contactDao.getContactByCname(contact);
        return contacts;
    }

    //删除联系人信息
    public List<Contact> deleteContact(Contact contact){
        //删除用户和联系人关联信息  参数为登录用户uid和cid
        contactDao.deleteUserRefCont(contact);
        //查询桥表中 cid联系人是否还监护其他用户
        int count = contactDao.getCidOnURC(contact.getCid());
        if (count == 0){
            contactDao.deleteContact(contact.getCid());
        }
        List<Contact> contacts = getContactsByUID(contact.getUid());
        return contacts;
    }

}
