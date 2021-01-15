package com.snnu.POJO;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class Contact {
    private int cid;
    private int uid;
    private String cname;
    private String email;
    private String phone;
    private List<User> users;

    public Contact() {
    }

    public Contact(int cid, String cname, String email, String phone) {
        this.cid = cid;
        this.cname = cname;
        this.email = email;
        this.phone = phone;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "cid=" + cid +
                ", uid=" + uid +
                ", cname='" + cname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", users=" + users +
                '}';
    }
}
