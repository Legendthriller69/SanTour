package com.example.group3.santour.Firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

/**
 * Created by aleks on 21.11.2017.
 */

public class PositionDB {

    private final static DatabaseReference POSTION_REFERENCE = FirebaseDatabase.getInstance().getReference("tracks");

    private PositionDB(){

    }

    public static void createPosition(double longitude, double latitude, double altitude, Date dateTime, String idTrack){
        DatabaseReference id = POSTION_REFERENCE.child("positions").push();
        id.child("id").setValue(id.getKey());
        id.child("longitude").setValue(longitude);
        id.child("latitude").setValue(latitude);
        id.child("altitude").setValue(altitude);
        id.child("dateTime").setValue(dateTime);
        id.child("idTrack").setValue(idTrack);
    }


}
