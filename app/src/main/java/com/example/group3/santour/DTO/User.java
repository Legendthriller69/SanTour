package com.example.group3.santour.DTO;

import android.webkit.JavascriptInterface;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by aleks on 17.11.2017.
 */

@IgnoreExtraProperties
public class User {

    private String id;
    private String username;
    private String password;
    private String mail;
    private Role role;

    public User(String id, String username, String password, String mail, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.role = role;
    }

    public User(String username, String password, String mail, Role role) {
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.role = role;
    }

    public User(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Exclude
    public Role getRole() {
        return role;
    }
    @Exclude
    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Id : " + id + ", pseudo : " + username + ", mail : " + mail;
    }
}
