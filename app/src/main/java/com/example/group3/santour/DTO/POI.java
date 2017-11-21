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

    @Override
    public String toString() {
        return "POI : ";
    }
}
