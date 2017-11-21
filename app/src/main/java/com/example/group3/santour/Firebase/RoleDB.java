package com.example.group3.santour.Firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by aleks on 20.11.2017.
 */

public class RoleDB {

    private final static DatabaseReference roleReference = FirebaseDatabase.getInstance().getReference("roles");

    private RoleDB(){

    };

    public static void createRole(String name){
        DatabaseReference id = roleReference.push();
        id.child("id").setValue(id.getKey());
        id.child("name").setValue(name);
    }

}
