package com.example.group3.santour.Firebase;

import android.util.Log;

import com.example.group3.santour.DTO.Role;
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
}
