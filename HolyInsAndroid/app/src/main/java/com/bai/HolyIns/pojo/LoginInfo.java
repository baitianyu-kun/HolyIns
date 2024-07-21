package com.bai.HolyIns.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



public class LoginInfo {
    private String session_id;
    private String account;
    private String e_mail;
    private String password;
    private String name;

    @Override
    public String toString() {
        return "LoginInfo{" +
                "session_id='" + session_id + '\'' +
                ", account='" + account + '\'' +
                ", e_mail='" + e_mail + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public LoginInfo(String E_mail, String Password)
    {
        this.e_mail=E_mail;
        this.password=Password;
    }

    public LoginInfo(String session_id, String account, String e_mail, String password, String name) {
        this.session_id = session_id;
        this.account = account;
        this.e_mail = e_mail;
        this.password = password;
        this.name = name;
    }

    public LoginInfo() {
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}