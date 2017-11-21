package com.example.group3.santour.DTO;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

/**
 * Created by DarkFace on 17 nov. 2017.
 */

@IgnoreExtraProperties
public class Track {

    private String id;
    private String name;
    private String description;
    private double distance;
    private int duration;
    private List<POI> pois;
    private List<POD> pods;
    private List<Position> positions;
    private Type type;
    private User user;

    public Track(){

    }

    public Track(String id, String name, String description, double distance, int duration, List<POI> pois, List<POD> pods, List<Position> positions, Type type, User user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.distance = distance;
        this.duration = duration;
        this.pois = pois;
        this.pods = pods;
        this.positions = positions;
        this.type = type;
        this.user = user;
    }

    public Track(String name, String description, double distance, int duration, List<POI> pois, List<POD> pods, List<Position> positions, Type type, User user) {
        this.name = name;
        this.description = description;
        this.distance = distance;
        this.duration = duration;
        this.pois = pois;
        this.pods = pods;
        this.positions = positions;
        this.type = type;
        this.user = user;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<POI> getPois() {
        return pois;
    }

    public void setPois(List<POI> pois) {
        this.pois = pois;
    }

    public List<POD> getPods() {
        return pods;
    }

    public void setPods(List<POD> pods) {
        this.pods = pods;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
