package com.example.group3.santour.DTO;

/**
 * Created by aleks on 17.11.2017.
 */

public class User {

    private String id;
    private String username;
    private String password;
    private String mail;
    private String idRole;

    public User() {

    }

    public User(String username, String password, String mail, String idRole) {
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.idRole = idRole;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMail() {
        return mail;
    }

    public String getIdRole() {
        return idRole;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setIdRole(String idRole) {
        this.idRole = idRole;
    }

    @Override
    public String toString() {
        return "Id : " + id + ", pseudo : " + username + ", mail : " + mail;
    }
}
