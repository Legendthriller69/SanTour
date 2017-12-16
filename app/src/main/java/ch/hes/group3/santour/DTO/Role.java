package ch.hes.group3.santour.DTO;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by aleks on 17.11.2017.
 */

@IgnoreExtraProperties
public class Role {

    private String id;
    private String name;

    public Role(){

    }

    public Role(String name) {
        this.name = name;
    }

    public Role(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Id : " + id + ", name : " + name;
    }
}
