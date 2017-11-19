package com.example.group3.santour.Firebase;

import android.util.Log;

import com.example.group3.santour.DTO.Role;
import com.example.group3.santour.DTO.Track;
import com.example.group3.santour.DTO.Type;
import com.example.group3.santour.DTO.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by aleks on 17.11.2017.
 */

public class FirebaseDB {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference myReference = firebaseDatabase.getReference("SanTourFirebase");

    //write data into the firebase DB
    public void writeNewRole(String name) {
        DatabaseReference id = myReference.child("roles").push();
        id.child("id").setValue(id.getKey());
        id.child("name").setValue(name);
    }

    public void writeNewUser(String username, String password, String mail, String idRole) {
        DatabaseReference id = myReference.child("users").push();
        id.child("id").setValue(id.getKey());
        id.child("username").setValue(username);
        id.child("password").setValue(password);
        id.child("mail").setValue(mail);
        id.child("idRole").setValue(idRole);
    }

    public void writeNewType(String name) {
        DatabaseReference id = myReference.child("types").push();
        id.child("id").setValue(id.getKey());
        id.child("name").setValue(name);
    }

    public void writeNewTrack(String name, String description, double distance, int duration, String idType) {
        DatabaseReference id = myReference.child("tracks");
        id.child("id").setValue(id.getKey());
        id.child("name").setValue(name);
        id.child("description").setValue(description);
        id.child("distance").setValue(distance);
        id.child("duration").setValue(duration);
        id.child("idType").setValue(idType);
    }

    //retrieve data from the firebase DB
    public void getAllUsers() {
        myReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.child("users").getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    Log.e("Users", user.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getRoleById(final String id) {
        myReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot roleSnapshot : dataSnapshot.child("roles").getChildren()) {
                    if (id.equals(roleSnapshot.child("id").getValue(String.class))) {
                        Role role = roleSnapshot.getValue(Role.class);
                        Log.e("Role : ", role.toString());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
