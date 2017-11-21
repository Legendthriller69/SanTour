package com.example.group3.santour.Firebase;

import com.google.firebase.database.DatabaseError;

/**
 * Created by aleks on 21.11.2017.
 */

public class ResetDB {

    public static void resetDB(){
        //create the role admin
        String idRole = RoleDB.createRole("admin");
        UserDB.createUser("root", "root", "root@root.ch", idRole);
    }
}
