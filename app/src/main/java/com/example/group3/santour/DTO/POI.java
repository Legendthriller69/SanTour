package com.example.group3.santour.DTO;

/**
 * Created by aleks on 21.11.2017.
 */

public class POI extends Point{

    public POI(){
        super();
    }

    public POI(String id, String name, String picture, String description, String idPosition, String idTrack) {
        super(id, name, picture, description, idPosition, idTrack);
    }

    @Override
    public String toString() {
        return "POI : ";
    }
}