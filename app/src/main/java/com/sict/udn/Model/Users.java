package com.sict.udn.Model;

import java.io.Serializable;

public class Users implements Serializable {
    String uid;
    String name;
    String address;
    String phone;
    String email;

    public Users(String name, String address, String phone, String email) {
        this.uid = uid;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public Users() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
