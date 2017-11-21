package com.example.group3.santour.DTO;

/**
 * Created by aleks on 21.11.2017.
 */

public abstract class Point {

    private String id;
    private String name;
    private String picture;
    private String description;
    private double longitude;
    private double latitude;
    private String idTrack;

    public Point(){

    }

    public Point(String id, String name, String picture, String description, double longitude, double latitude, String idTrack) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.idTrack = idTrack;
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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getIdTrack() {
        return idTrack;
    }

    public void setIdTrack(String idTrack) {
        this.idTrack = idTrack;
    }
}
