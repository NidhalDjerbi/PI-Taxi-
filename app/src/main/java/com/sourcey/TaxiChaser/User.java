package com.sourcey.TaxiChaser;

import java.io.Serializable;

/**
 * Created by nidhal on 20/12/2017.
 */

public class User implements Serializable{
    final static String ClientType="Client";
    final static String ChauffeurType="Chauffeur";
    final static String ProprietaireType="Proprietaire";

    public  static String Username;
    public  static  String userId;
    public  String username;
    public String address;
    public String function;
    public String email;
    public String mobile;
    public String password;

    public User(String username, String address, String function, String email, String mobile, String password) {
        this.username = username;
        this.address = address;
        this.function = function;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
