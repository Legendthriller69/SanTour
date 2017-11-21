package com.example.group3.santour.Firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by kevin on 21/11/17.
 */

public class TrackDB {

    private final static DatabaseReference TRACK_REFERENCE = FirebaseDatabase.getInstance().getReference("tracks");

    private TrackDB(){

    }

    public static void createTrack(String name, String description, Double distance, Integer duration, String idType) {
        DatabaseReference id = TRACK_REFERENCE.push();
        id.child("id").setValue(id.getKey());
        id.child("name").setValue(name);
        id.child("description").setValue(description);
        id.child("distance").setValue(distance);
        id.child("duration").setValue(duration);
        id.child("idType").setValue(idType);
    }





}
