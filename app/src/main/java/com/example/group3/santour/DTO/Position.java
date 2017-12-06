package com.example.group3.santour.DTO;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by aleks on 21.11.2017.
 */

@IgnoreExtraProperties
public class Position {

    private String id;
    private double longitude;
    private double latitude;
    private double altitude;
    private String dateTime;

    public Position(){

    }

    public Position(String id, double latitude, double longitude, double altitude, String dateTime) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.dateTime = dateTime;
    }

    public Position(double longitude, double latitude, double altitude, String dateTime) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
        this.dateTime = dateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id='" + id + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", altitude=" + altitude +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }
}
