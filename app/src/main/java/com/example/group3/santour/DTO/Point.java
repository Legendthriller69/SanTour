package com.example.group3.santour.DTO;

/**
 * Created by aleks on 21.11.2017.
 */

public abstract class Point {

    private String id;
    private String name;
    private String picture;
    private String description;
    private Position position;

    public Point(){

    }

    public Point(String id, String name, String picture, String description, Position position) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.description = description;
        this.position = position;
    }

    public Point(String name, String picture, String description, Position position) {
        this.name = name;
        this.picture = picture;
        this.description = description;
        this.position = position;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
