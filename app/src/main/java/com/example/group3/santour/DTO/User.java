package com.example.group3.santour.DTO;

/**
 * Created by aleks on 17.11.2017.
 */

public class User {

    private String id;
    private String pseudo;
    private String password;
    private String mail;
    private Role role;

    public User(){

    }

    public User(String pseudo, String password, String mail, Role role) {
        this.pseudo = pseudo;
        this.password = password;
        this.mail = mail;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getPassword() {
        return password;
    }

    public String getMail() {
        return mail;
    }

    public Role getRole() {
        return role;
    }

    public void setId(String id){
        this.id = id;
    }
}
