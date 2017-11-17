package com.example.group3.santour.DTO;

/**
 * Created by DarkFace on 17 nov. 2017.
 */

public class Type {

    private String id;
    private String name;

    public Type(){
    }

    public Type (String name){
        this.name = name;
    }

    //getter
    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public void setId(String id) {
        this.id = id;
    }
}
