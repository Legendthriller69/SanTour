package com.example.group3.santour.Firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by aleks on 21.11.2017.
 */

public class POIDB {

    private final static DatabaseReference POI_REFERENCE = FirebaseDatabase.getInstance().getReference("tracks");

    private POIDB(){

    }

    public static void createPOI(String name, String picture, String description, double longitude, double latitude, String idTrack){
        DatabaseReference id = POI_REFERENCE.child("POI").push();
        id.child("id").setValue(id.getKey());
        id.child("name").setValue(name);
        id.child("picture").setValue(picture);
        id.child("description").setValue(description);
        id.child("longitude").setValue(longitude);
        id.child("latitude").setValue(latitude);
        id.child("idTrack").setValue(idTrack);
    }

}
