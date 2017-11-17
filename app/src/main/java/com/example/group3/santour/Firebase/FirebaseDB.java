package com.example.group3.santour.Firebase;

import com.example.group3.santour.DTO.Role;
import com.example.group3.santour.DTO.Track;
import com.example.group3.santour.DTO.Type;
import com.example.group3.santour.DTO.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by aleks on 17.11.2017.
 */

public class FirebaseDB {

    FirebaseDatabase firebaseDatabase = com.google.firebase.database.FirebaseDatabase.getInstance();
    public DatabaseReference myReference = firebaseDatabase.getReference("SanTourFirebase");


    public void writeNewRole(Role role){
        DatabaseReference id = myReference.child("roles").push();
        id.setValue(role);
        role.setId(id.getKey());
    }

    public void writeNewUser(User user) {
        DatabaseReference id = myReference.child("users").push();
        id.child("username").setValue(user.getPseudo());
        id.child("password").setValue(user.getPassword());
        id.child("mail").setValue(user.getMail());
        id.child(user.getRole().getId()).setValue(true);
        user.setId(id.getKey());
    }

    public void writeNewType(Type type){
        DatabaseReference id = myReference.child("types").push();
        id.setValue(type);
        type.setId(id.getKey());
    }

    public void writeNewTrack( Track track){
        DatabaseReference id = myReference.child("tracks");
        id.child("name").setValue(track.getName());
        id.child("description").setValue(track.getDescription());
        id.child("distance").setValue(track.getDistance());
        id.child("duration").setValue(track.getDuration());
        id.child(track.getType().getId()).setValue(true);
        track.setId(id.getKey());
    }
}
