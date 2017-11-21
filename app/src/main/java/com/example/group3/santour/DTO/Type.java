package com.example.group3.santour.DTO;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by DarkFace on 17 nov. 2017.
 */

@IgnoreExtraProperties
public class Type {

    private String id;
    private String name;

    public Type(){

    }

    public Type(String id, String name) {
        this.id = id;
        this.name = name;
    }

    //getter
    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    //setter
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
