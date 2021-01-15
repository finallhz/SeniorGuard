package com.snnu.POJO;

import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

@Entity
public class User {
    private int uid;
    private String username;
    private String pwd;
    private int gender;
    private int age;
    private int role;
    private String email;
    private String phone;
    private String address;
    private Date birthdate;
    private String remarks;
    private String uuid;

    private List<Contact> contacts;

    public User() {
    }

    public User(int uid, String username, String pwd, int gender, int age, int role, String email, String phone, String address, Date birthdate, String remarks, String uuid) {
        this.uid = uid;
        this.username = username;
        this.pwd = pwd;
        this.gender = gender;
        this.age = age;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.birthdate = birthdate;
        this.remarks = remarks;
        this.uuid = uuid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", pwd='" + pwd + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", role=" + role +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", birthdate=" + birthdate +
                ", remarks='" + remarks + '\'' +
                ", uuid='" + uuid + '\'' +
                ", contacts=" + contacts +
                '}';
    }
}
