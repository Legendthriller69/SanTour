package com.example.group3.santour.DTO;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by aleks on 21.11.2017.
 */

@IgnoreExtraProperties
public class POI extends Point{

    public POI(){
        super();
    }

    public POI(String id, String name, String picture, String description, Position position) {
        super(id, name, picture, description, position);
    }

    public POI(String name, String picture, String description, Position position) {
        super(name, picture, description, position);
    }

    @Override
    public String toString() {
        return "POI : ";
    }
}
