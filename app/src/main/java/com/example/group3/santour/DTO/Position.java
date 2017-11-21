package com.example.group3.santour.DTO;

import java.util.Date;

/**
 * Created by aleks on 21.11.2017.
 */

public class Position {

    private String id;
    private double longitude;
    private double latitude;
    private double altitude;
    private Date dateTime;
    private String idTrack;

    public Position(){

    }

    public Position(String id, double latitude, double longitude, double altitude, Date dateTime, String idTrack) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.dateTime = dateTime;
        this.idTrack = idTrack;
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

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getIdTrack() {
        return idTrack;
    }

    public void setIdTrack(String idTrack) {
        this.idTrack = idTrack;
    }
}
