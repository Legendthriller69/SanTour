package com.example.group3.santour.DTO;

/**
 * Created by aleks on 21.11.2017.
 */

public class Category {

    private String id;
    private String name;


    public Category() {

    }

    public Category(String id, String name) {
        this.id = id;
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
