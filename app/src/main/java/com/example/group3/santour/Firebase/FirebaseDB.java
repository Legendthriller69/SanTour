package com.example.group3.santour.Firebase;

import com.example.group3.santour.DTO.Role;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by aleks on 17.11.2017.
 */

public class FirebaseDatabase {

    FirebaseDatabase firebaseDatabase = com.google.firebase.database.FirebaseDatabase.getInstance();
    public DatabaseReference myReference = firebaseDatabase.getReference("message");

    public void writeToDatabase(DatabaseReference myReference, String text){
        myReference.setValue(text);
    }

    public void writeNewRole(Role role){
        myReference.child("roles").child(String.valueOf(role.getId())).setValue(role);
    }
}
