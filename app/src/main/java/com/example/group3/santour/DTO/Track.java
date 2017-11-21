package com.example.group3.santour.DTO;

/**
 * Created by DarkFace on 17 nov. 2017.
 */

public class Track {

    private String id;
    private String name;
    private String description;
    private double distance;
    private int duration;
    private String idType;

    public Track(){

    }

    public Track(String name, String description, double distance, int duration, String idType) {
        this.name = name;
        this.description = description;
        this.distance = distance;
        this.duration = duration;
        this.idType = idType;
    }

    //getter
    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public double getDistance() {
        return this.distance;
    }

    public int getDuration() {
        return this.duration;
    }

    public String getType() {
        return this.idType;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }
}
