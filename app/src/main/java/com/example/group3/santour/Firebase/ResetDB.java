package com.example.group3.santour.Firebase;

import android.util.Log;

import com.example.group3.santour.DTO.Role;
import com.example.group3.santour.DTO.User;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by aleks on 21.11.2017.
 */

public class ResetDB {

    private final static DatabaseReference DB_REFERENCE = FirebaseDatabase.getInstance().getReference();

    public static void resetDB(){
        //remove everything from DB
        DB_REFERENCE.removeValue();

        //add a new role
        RoleDB.createRole(new Role("admin"), new DataListener() {
            @Override
            public void onSuccess(Object object) {
                Role role = (Role) object;
                UserDB.createUser(new User("root", "root", "root@root.ch", role));
            }

            @Override
            public void onFailed(DatabaseError dbError) {

            }
        });
    }
}
