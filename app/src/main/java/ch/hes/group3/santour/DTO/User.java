package ch.hes.group3.santour.DTO;

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
    private String idRole;

    public User(String id, String username, String password, String mail, String idRole) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.idRole = idRole;
    }

    public User(String username, String password, String mail, String idRole) {
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.idRole = idRole;
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

    public String getIdRole() {
        return idRole;
    }

    public void setIdRole(String idRole) {
        this.idRole = idRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                ", idRole='" + idRole + '\'' +
                '}';
    }
}
