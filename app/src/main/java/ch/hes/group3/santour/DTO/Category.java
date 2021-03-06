package ch.hes.group3.santour.DTO;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by aleks on 21.11.2017.
 */

@IgnoreExtraProperties
public class Category {

    private String id;
    private String name;


    public Category() {

    }

    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(String name){
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
